package cl.gorilas.user.web.rest;

import cl.gorilas.user.ServicesUsuarioGorilasApp;

import cl.gorilas.user.domain.Usuarios;
import cl.gorilas.user.repository.UsuariosRepository;
import cl.gorilas.user.service.UsuariosService;
import cl.gorilas.user.service.dto.UsuariosDTO;
import cl.gorilas.user.service.mapper.UsuariosMapper;
import cl.gorilas.user.web.rest.errors.ExceptionTranslator;
import cl.gorilas.user.service.dto.UsuariosCriteria;
import cl.gorilas.user.service.UsuariosQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static cl.gorilas.user.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsuariosResource REST controller.
 *
 * @see UsuariosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicesUsuarioGorilasApp.class)
public class UsuariosResourceIntTest {

    private static final String DEFAULT_ID_GORILAS = "AAAAAAAAAA";
    private static final String UPDATED_ID_GORILAS = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMPLETO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final Long DEFAULT_ABONO = 1L;
    private static final Long UPDATED_ABONO = 2L;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TURNOS_DISPONIBLES = 1;
    private static final Integer UPDATED_TURNOS_DISPONIBLES = 2;

    private static final String DEFAULT_TURNOS_DISPONIBLES_LEYENDA = "AAAAAAAAAA";
    private static final String UPDATED_TURNOS_DISPONIBLES_LEYENDA = "BBBBBBBBBB";

    private static final String DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML = "AAAAAAAAAA";
    private static final String UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML = "BBBBBBBBBB";

    private static final String DEFAULT_ABONOS_VENCIMIENTO_HTML = "AAAAAAAAAA";
    private static final String UPDATED_ABONOS_VENCIMIENTO_HTML = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_PROX_APTO_FISICO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PROX_APTO_FISICO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_PROX_APTO_FISICO_STRING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_PROX_APTO_FISICO_STRING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_DIAS_RESTANTES = 1F;
    private static final Float UPDATED_DIAS_RESTANTES = 2F;

    private static final Boolean DEFAULT_TIENE_DEUDA = false;
    private static final Boolean UPDATED_TIENE_DEUDA = true;

    private static final Integer DEFAULT_SALDO = 1;
    private static final Integer UPDATED_SALDO = 2;

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_BARRIO = "AAAAAAAAAA";
    private static final String UPDATED_BARRIO = "BBBBBBBBBB";

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosMapper usuariosMapper;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private UsuariosQueryService usuariosQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUsuariosMockMvc;

    private Usuarios usuarios;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuariosResource usuariosResource = new UsuariosResource(usuariosService, usuariosQueryService);
        this.restUsuariosMockMvc = MockMvcBuilders.standaloneSetup(usuariosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios()
            .idGorilas(DEFAULT_ID_GORILAS)
            .nombreCompleto(DEFAULT_NOMBRE_COMPLETO)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .documento(DEFAULT_DOCUMENTO)
            .abono(DEFAULT_ABONO)
            .estado(DEFAULT_ESTADO)
            .turnosDisponibles(DEFAULT_TURNOS_DISPONIBLES)
            .turnosDisponiblesLeyenda(DEFAULT_TURNOS_DISPONIBLES_LEYENDA)
            .turnosDisponiblesLeyendaHtml(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML)
            .abonosVencimientoHtml(DEFAULT_ABONOS_VENCIMIENTO_HTML)
            .fechaProxAptoFisico(DEFAULT_FECHA_PROX_APTO_FISICO)
            .fechaProxAptoFisicoString(DEFAULT_FECHA_PROX_APTO_FISICO_STRING)
            .diasRestantes(DEFAULT_DIAS_RESTANTES)
            .tieneDeuda(DEFAULT_TIENE_DEUDA)
            .saldo(DEFAULT_SALDO)
            .domicilio(DEFAULT_DOMICILIO)
            .barrio(DEFAULT_BARRIO);
        return usuarios;
    }

    @Before
    public void initTest() {
        usuarios = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarios() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();

        // Create the Usuarios
        UsuariosDTO usuariosDTO = usuariosMapper.toDto(usuarios);
        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuariosDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate + 1);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getIdGorilas()).isEqualTo(DEFAULT_ID_GORILAS);
        assertThat(testUsuarios.getNombreCompleto()).isEqualTo(DEFAULT_NOMBRE_COMPLETO);
        assertThat(testUsuarios.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testUsuarios.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testUsuarios.getAbono()).isEqualTo(DEFAULT_ABONO);
        assertThat(testUsuarios.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsuarios.getTurnosDisponibles()).isEqualTo(DEFAULT_TURNOS_DISPONIBLES);
        assertThat(testUsuarios.getTurnosDisponiblesLeyenda()).isEqualTo(DEFAULT_TURNOS_DISPONIBLES_LEYENDA);
        assertThat(testUsuarios.getTurnosDisponiblesLeyendaHtml()).isEqualTo(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML);
        assertThat(testUsuarios.getAbonosVencimientoHtml()).isEqualTo(DEFAULT_ABONOS_VENCIMIENTO_HTML);
        assertThat(testUsuarios.getFechaProxAptoFisico()).isEqualTo(DEFAULT_FECHA_PROX_APTO_FISICO);
        assertThat(testUsuarios.getFechaProxAptoFisicoString()).isEqualTo(DEFAULT_FECHA_PROX_APTO_FISICO_STRING);
        assertThat(testUsuarios.getDiasRestantes()).isEqualTo(DEFAULT_DIAS_RESTANTES);
        assertThat(testUsuarios.isTieneDeuda()).isEqualTo(DEFAULT_TIENE_DEUDA);
        assertThat(testUsuarios.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testUsuarios.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testUsuarios.getBarrio()).isEqualTo(DEFAULT_BARRIO);
    }

    @Test
    @Transactional
    public void createUsuariosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();

        // Create the Usuarios with an existing ID
        usuarios.setId(1L);
        UsuariosDTO usuariosDTO = usuariosMapper.toDto(usuarios);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuariosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idGorilas").value(hasItem(DEFAULT_ID_GORILAS.toString())))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO.toString())))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].abono").value(hasItem(DEFAULT_ABONO.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].turnosDisponibles").value(hasItem(DEFAULT_TURNOS_DISPONIBLES)))
            .andExpect(jsonPath("$.[*].turnosDisponiblesLeyenda").value(hasItem(DEFAULT_TURNOS_DISPONIBLES_LEYENDA.toString())))
            .andExpect(jsonPath("$.[*].turnosDisponiblesLeyendaHtml").value(hasItem(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML.toString())))
            .andExpect(jsonPath("$.[*].abonosVencimientoHtml").value(hasItem(DEFAULT_ABONOS_VENCIMIENTO_HTML.toString())))
            .andExpect(jsonPath("$.[*].fechaProxAptoFisico").value(hasItem(DEFAULT_FECHA_PROX_APTO_FISICO.toString())))
            .andExpect(jsonPath("$.[*].fechaProxAptoFisicoString").value(hasItem(DEFAULT_FECHA_PROX_APTO_FISICO_STRING.toString())))
            .andExpect(jsonPath("$.[*].diasRestantes").value(hasItem(DEFAULT_DIAS_RESTANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].tieneDeuda").value(hasItem(DEFAULT_TIENE_DEUDA.booleanValue())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].barrio").value(hasItem(DEFAULT_BARRIO.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get the usuarios
        restUsuariosMockMvc.perform(get("/api/usuarios/{id}", usuarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuarios.getId().intValue()))
            .andExpect(jsonPath("$.idGorilas").value(DEFAULT_ID_GORILAS.toString()))
            .andExpect(jsonPath("$.nombreCompleto").value(DEFAULT_NOMBRE_COMPLETO.toString()))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.abono").value(DEFAULT_ABONO.intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.turnosDisponibles").value(DEFAULT_TURNOS_DISPONIBLES))
            .andExpect(jsonPath("$.turnosDisponiblesLeyenda").value(DEFAULT_TURNOS_DISPONIBLES_LEYENDA.toString()))
            .andExpect(jsonPath("$.turnosDisponiblesLeyendaHtml").value(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML.toString()))
            .andExpect(jsonPath("$.abonosVencimientoHtml").value(DEFAULT_ABONOS_VENCIMIENTO_HTML.toString()))
            .andExpect(jsonPath("$.fechaProxAptoFisico").value(DEFAULT_FECHA_PROX_APTO_FISICO.toString()))
            .andExpect(jsonPath("$.fechaProxAptoFisicoString").value(DEFAULT_FECHA_PROX_APTO_FISICO_STRING.toString()))
            .andExpect(jsonPath("$.diasRestantes").value(DEFAULT_DIAS_RESTANTES.doubleValue()))
            .andExpect(jsonPath("$.tieneDeuda").value(DEFAULT_TIENE_DEUDA.booleanValue()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()))
            .andExpect(jsonPath("$.barrio").value(DEFAULT_BARRIO.toString()));
    }

    @Test
    @Transactional
    public void getAllUsuariosByIdGorilasIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idGorilas equals to DEFAULT_ID_GORILAS
        defaultUsuariosShouldBeFound("idGorilas.equals=" + DEFAULT_ID_GORILAS);

        // Get all the usuariosList where idGorilas equals to UPDATED_ID_GORILAS
        defaultUsuariosShouldNotBeFound("idGorilas.equals=" + UPDATED_ID_GORILAS);
    }

    @Test
    @Transactional
    public void getAllUsuariosByIdGorilasIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idGorilas in DEFAULT_ID_GORILAS or UPDATED_ID_GORILAS
        defaultUsuariosShouldBeFound("idGorilas.in=" + DEFAULT_ID_GORILAS + "," + UPDATED_ID_GORILAS);

        // Get all the usuariosList where idGorilas equals to UPDATED_ID_GORILAS
        defaultUsuariosShouldNotBeFound("idGorilas.in=" + UPDATED_ID_GORILAS);
    }

    @Test
    @Transactional
    public void getAllUsuariosByIdGorilasIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idGorilas is not null
        defaultUsuariosShouldBeFound("idGorilas.specified=true");

        // Get all the usuariosList where idGorilas is null
        defaultUsuariosShouldNotBeFound("idGorilas.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByNombreCompletoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where nombreCompleto equals to DEFAULT_NOMBRE_COMPLETO
        defaultUsuariosShouldBeFound("nombreCompleto.equals=" + DEFAULT_NOMBRE_COMPLETO);

        // Get all the usuariosList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultUsuariosShouldNotBeFound("nombreCompleto.equals=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNombreCompletoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where nombreCompleto in DEFAULT_NOMBRE_COMPLETO or UPDATED_NOMBRE_COMPLETO
        defaultUsuariosShouldBeFound("nombreCompleto.in=" + DEFAULT_NOMBRE_COMPLETO + "," + UPDATED_NOMBRE_COMPLETO);

        // Get all the usuariosList where nombreCompleto equals to UPDATED_NOMBRE_COMPLETO
        defaultUsuariosShouldNotBeFound("nombreCompleto.in=" + UPDATED_NOMBRE_COMPLETO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNombreCompletoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where nombreCompleto is not null
        defaultUsuariosShouldBeFound("nombreCompleto.specified=true");

        // Get all the usuariosList where nombreCompleto is null
        defaultUsuariosShouldNotBeFound("nombreCompleto.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByCorreoElectronicoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where correoElectronico equals to DEFAULT_CORREO_ELECTRONICO
        defaultUsuariosShouldBeFound("correoElectronico.equals=" + DEFAULT_CORREO_ELECTRONICO);

        // Get all the usuariosList where correoElectronico equals to UPDATED_CORREO_ELECTRONICO
        defaultUsuariosShouldNotBeFound("correoElectronico.equals=" + UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCorreoElectronicoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where correoElectronico in DEFAULT_CORREO_ELECTRONICO or UPDATED_CORREO_ELECTRONICO
        defaultUsuariosShouldBeFound("correoElectronico.in=" + DEFAULT_CORREO_ELECTRONICO + "," + UPDATED_CORREO_ELECTRONICO);

        // Get all the usuariosList where correoElectronico equals to UPDATED_CORREO_ELECTRONICO
        defaultUsuariosShouldNotBeFound("correoElectronico.in=" + UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByCorreoElectronicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where correoElectronico is not null
        defaultUsuariosShouldBeFound("correoElectronico.specified=true");

        // Get all the usuariosList where correoElectronico is null
        defaultUsuariosShouldNotBeFound("correoElectronico.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento equals to DEFAULT_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.equals=" + DEFAULT_DOCUMENTO);

        // Get all the usuariosList where documento equals to UPDATED_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.equals=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento in DEFAULT_DOCUMENTO or UPDATED_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.in=" + DEFAULT_DOCUMENTO + "," + UPDATED_DOCUMENTO);

        // Get all the usuariosList where documento equals to UPDATED_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.in=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento is not null
        defaultUsuariosShouldBeFound("documento.specified=true");

        // Get all the usuariosList where documento is null
        defaultUsuariosShouldNotBeFound("documento.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abono equals to DEFAULT_ABONO
        defaultUsuariosShouldBeFound("abono.equals=" + DEFAULT_ABONO);

        // Get all the usuariosList where abono equals to UPDATED_ABONO
        defaultUsuariosShouldNotBeFound("abono.equals=" + UPDATED_ABONO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abono in DEFAULT_ABONO or UPDATED_ABONO
        defaultUsuariosShouldBeFound("abono.in=" + DEFAULT_ABONO + "," + UPDATED_ABONO);

        // Get all the usuariosList where abono equals to UPDATED_ABONO
        defaultUsuariosShouldNotBeFound("abono.in=" + UPDATED_ABONO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abono is not null
        defaultUsuariosShouldBeFound("abono.specified=true");

        // Get all the usuariosList where abono is null
        defaultUsuariosShouldNotBeFound("abono.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abono greater than or equals to DEFAULT_ABONO
        defaultUsuariosShouldBeFound("abono.greaterOrEqualThan=" + DEFAULT_ABONO);

        // Get all the usuariosList where abono greater than or equals to UPDATED_ABONO
        defaultUsuariosShouldNotBeFound("abono.greaterOrEqualThan=" + UPDATED_ABONO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonoIsLessThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abono less than or equals to DEFAULT_ABONO
        defaultUsuariosShouldNotBeFound("abono.lessThan=" + DEFAULT_ABONO);

        // Get all the usuariosList where abono less than or equals to UPDATED_ABONO
        defaultUsuariosShouldBeFound("abono.lessThan=" + UPDATED_ABONO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where estado equals to DEFAULT_ESTADO
        defaultUsuariosShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the usuariosList where estado equals to UPDATED_ESTADO
        defaultUsuariosShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultUsuariosShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the usuariosList where estado equals to UPDATED_ESTADO
        defaultUsuariosShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where estado is not null
        defaultUsuariosShouldBeFound("estado.specified=true");

        // Get all the usuariosList where estado is null
        defaultUsuariosShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponibles equals to DEFAULT_TURNOS_DISPONIBLES
        defaultUsuariosShouldBeFound("turnosDisponibles.equals=" + DEFAULT_TURNOS_DISPONIBLES);

        // Get all the usuariosList where turnosDisponibles equals to UPDATED_TURNOS_DISPONIBLES
        defaultUsuariosShouldNotBeFound("turnosDisponibles.equals=" + UPDATED_TURNOS_DISPONIBLES);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponibles in DEFAULT_TURNOS_DISPONIBLES or UPDATED_TURNOS_DISPONIBLES
        defaultUsuariosShouldBeFound("turnosDisponibles.in=" + DEFAULT_TURNOS_DISPONIBLES + "," + UPDATED_TURNOS_DISPONIBLES);

        // Get all the usuariosList where turnosDisponibles equals to UPDATED_TURNOS_DISPONIBLES
        defaultUsuariosShouldNotBeFound("turnosDisponibles.in=" + UPDATED_TURNOS_DISPONIBLES);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponibles is not null
        defaultUsuariosShouldBeFound("turnosDisponibles.specified=true");

        // Get all the usuariosList where turnosDisponibles is null
        defaultUsuariosShouldNotBeFound("turnosDisponibles.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponibles greater than or equals to DEFAULT_TURNOS_DISPONIBLES
        defaultUsuariosShouldBeFound("turnosDisponibles.greaterOrEqualThan=" + DEFAULT_TURNOS_DISPONIBLES);

        // Get all the usuariosList where turnosDisponibles greater than or equals to UPDATED_TURNOS_DISPONIBLES
        defaultUsuariosShouldNotBeFound("turnosDisponibles.greaterOrEqualThan=" + UPDATED_TURNOS_DISPONIBLES);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesIsLessThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponibles less than or equals to DEFAULT_TURNOS_DISPONIBLES
        defaultUsuariosShouldNotBeFound("turnosDisponibles.lessThan=" + DEFAULT_TURNOS_DISPONIBLES);

        // Get all the usuariosList where turnosDisponibles less than or equals to UPDATED_TURNOS_DISPONIBLES
        defaultUsuariosShouldBeFound("turnosDisponibles.lessThan=" + UPDATED_TURNOS_DISPONIBLES);
    }


    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesLeyendaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponiblesLeyenda equals to DEFAULT_TURNOS_DISPONIBLES_LEYENDA
        defaultUsuariosShouldBeFound("turnosDisponiblesLeyenda.equals=" + DEFAULT_TURNOS_DISPONIBLES_LEYENDA);

        // Get all the usuariosList where turnosDisponiblesLeyenda equals to UPDATED_TURNOS_DISPONIBLES_LEYENDA
        defaultUsuariosShouldNotBeFound("turnosDisponiblesLeyenda.equals=" + UPDATED_TURNOS_DISPONIBLES_LEYENDA);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesLeyendaIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponiblesLeyenda in DEFAULT_TURNOS_DISPONIBLES_LEYENDA or UPDATED_TURNOS_DISPONIBLES_LEYENDA
        defaultUsuariosShouldBeFound("turnosDisponiblesLeyenda.in=" + DEFAULT_TURNOS_DISPONIBLES_LEYENDA + "," + UPDATED_TURNOS_DISPONIBLES_LEYENDA);

        // Get all the usuariosList where turnosDisponiblesLeyenda equals to UPDATED_TURNOS_DISPONIBLES_LEYENDA
        defaultUsuariosShouldNotBeFound("turnosDisponiblesLeyenda.in=" + UPDATED_TURNOS_DISPONIBLES_LEYENDA);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesLeyendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponiblesLeyenda is not null
        defaultUsuariosShouldBeFound("turnosDisponiblesLeyenda.specified=true");

        // Get all the usuariosList where turnosDisponiblesLeyenda is null
        defaultUsuariosShouldNotBeFound("turnosDisponiblesLeyenda.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesLeyendaHtmlIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponiblesLeyendaHtml equals to DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML
        defaultUsuariosShouldBeFound("turnosDisponiblesLeyendaHtml.equals=" + DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML);

        // Get all the usuariosList where turnosDisponiblesLeyendaHtml equals to UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML
        defaultUsuariosShouldNotBeFound("turnosDisponiblesLeyendaHtml.equals=" + UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesLeyendaHtmlIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponiblesLeyendaHtml in DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML or UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML
        defaultUsuariosShouldBeFound("turnosDisponiblesLeyendaHtml.in=" + DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML + "," + UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML);

        // Get all the usuariosList where turnosDisponiblesLeyendaHtml equals to UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML
        defaultUsuariosShouldNotBeFound("turnosDisponiblesLeyendaHtml.in=" + UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTurnosDisponiblesLeyendaHtmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where turnosDisponiblesLeyendaHtml is not null
        defaultUsuariosShouldBeFound("turnosDisponiblesLeyendaHtml.specified=true");

        // Get all the usuariosList where turnosDisponiblesLeyendaHtml is null
        defaultUsuariosShouldNotBeFound("turnosDisponiblesLeyendaHtml.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonosVencimientoHtmlIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abonosVencimientoHtml equals to DEFAULT_ABONOS_VENCIMIENTO_HTML
        defaultUsuariosShouldBeFound("abonosVencimientoHtml.equals=" + DEFAULT_ABONOS_VENCIMIENTO_HTML);

        // Get all the usuariosList where abonosVencimientoHtml equals to UPDATED_ABONOS_VENCIMIENTO_HTML
        defaultUsuariosShouldNotBeFound("abonosVencimientoHtml.equals=" + UPDATED_ABONOS_VENCIMIENTO_HTML);
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonosVencimientoHtmlIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abonosVencimientoHtml in DEFAULT_ABONOS_VENCIMIENTO_HTML or UPDATED_ABONOS_VENCIMIENTO_HTML
        defaultUsuariosShouldBeFound("abonosVencimientoHtml.in=" + DEFAULT_ABONOS_VENCIMIENTO_HTML + "," + UPDATED_ABONOS_VENCIMIENTO_HTML);

        // Get all the usuariosList where abonosVencimientoHtml equals to UPDATED_ABONOS_VENCIMIENTO_HTML
        defaultUsuariosShouldNotBeFound("abonosVencimientoHtml.in=" + UPDATED_ABONOS_VENCIMIENTO_HTML);
    }

    @Test
    @Transactional
    public void getAllUsuariosByAbonosVencimientoHtmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where abonosVencimientoHtml is not null
        defaultUsuariosShouldBeFound("abonosVencimientoHtml.specified=true");

        // Get all the usuariosList where abonosVencimientoHtml is null
        defaultUsuariosShouldNotBeFound("abonosVencimientoHtml.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByFechaProxAptoFisicoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where fechaProxAptoFisico equals to DEFAULT_FECHA_PROX_APTO_FISICO
        defaultUsuariosShouldBeFound("fechaProxAptoFisico.equals=" + DEFAULT_FECHA_PROX_APTO_FISICO);

        // Get all the usuariosList where fechaProxAptoFisico equals to UPDATED_FECHA_PROX_APTO_FISICO
        defaultUsuariosShouldNotBeFound("fechaProxAptoFisico.equals=" + UPDATED_FECHA_PROX_APTO_FISICO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFechaProxAptoFisicoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where fechaProxAptoFisico in DEFAULT_FECHA_PROX_APTO_FISICO or UPDATED_FECHA_PROX_APTO_FISICO
        defaultUsuariosShouldBeFound("fechaProxAptoFisico.in=" + DEFAULT_FECHA_PROX_APTO_FISICO + "," + UPDATED_FECHA_PROX_APTO_FISICO);

        // Get all the usuariosList where fechaProxAptoFisico equals to UPDATED_FECHA_PROX_APTO_FISICO
        defaultUsuariosShouldNotBeFound("fechaProxAptoFisico.in=" + UPDATED_FECHA_PROX_APTO_FISICO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFechaProxAptoFisicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where fechaProxAptoFisico is not null
        defaultUsuariosShouldBeFound("fechaProxAptoFisico.specified=true");

        // Get all the usuariosList where fechaProxAptoFisico is null
        defaultUsuariosShouldNotBeFound("fechaProxAptoFisico.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByFechaProxAptoFisicoStringIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where fechaProxAptoFisicoString equals to DEFAULT_FECHA_PROX_APTO_FISICO_STRING
        defaultUsuariosShouldBeFound("fechaProxAptoFisicoString.equals=" + DEFAULT_FECHA_PROX_APTO_FISICO_STRING);

        // Get all the usuariosList where fechaProxAptoFisicoString equals to UPDATED_FECHA_PROX_APTO_FISICO_STRING
        defaultUsuariosShouldNotBeFound("fechaProxAptoFisicoString.equals=" + UPDATED_FECHA_PROX_APTO_FISICO_STRING);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFechaProxAptoFisicoStringIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where fechaProxAptoFisicoString in DEFAULT_FECHA_PROX_APTO_FISICO_STRING or UPDATED_FECHA_PROX_APTO_FISICO_STRING
        defaultUsuariosShouldBeFound("fechaProxAptoFisicoString.in=" + DEFAULT_FECHA_PROX_APTO_FISICO_STRING + "," + UPDATED_FECHA_PROX_APTO_FISICO_STRING);

        // Get all the usuariosList where fechaProxAptoFisicoString equals to UPDATED_FECHA_PROX_APTO_FISICO_STRING
        defaultUsuariosShouldNotBeFound("fechaProxAptoFisicoString.in=" + UPDATED_FECHA_PROX_APTO_FISICO_STRING);
    }

    @Test
    @Transactional
    public void getAllUsuariosByFechaProxAptoFisicoStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where fechaProxAptoFisicoString is not null
        defaultUsuariosShouldBeFound("fechaProxAptoFisicoString.specified=true");

        // Get all the usuariosList where fechaProxAptoFisicoString is null
        defaultUsuariosShouldNotBeFound("fechaProxAptoFisicoString.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByDiasRestantesIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where diasRestantes equals to DEFAULT_DIAS_RESTANTES
        defaultUsuariosShouldBeFound("diasRestantes.equals=" + DEFAULT_DIAS_RESTANTES);

        // Get all the usuariosList where diasRestantes equals to UPDATED_DIAS_RESTANTES
        defaultUsuariosShouldNotBeFound("diasRestantes.equals=" + UPDATED_DIAS_RESTANTES);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDiasRestantesIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where diasRestantes in DEFAULT_DIAS_RESTANTES or UPDATED_DIAS_RESTANTES
        defaultUsuariosShouldBeFound("diasRestantes.in=" + DEFAULT_DIAS_RESTANTES + "," + UPDATED_DIAS_RESTANTES);

        // Get all the usuariosList where diasRestantes equals to UPDATED_DIAS_RESTANTES
        defaultUsuariosShouldNotBeFound("diasRestantes.in=" + UPDATED_DIAS_RESTANTES);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDiasRestantesIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where diasRestantes is not null
        defaultUsuariosShouldBeFound("diasRestantes.specified=true");

        // Get all the usuariosList where diasRestantes is null
        defaultUsuariosShouldNotBeFound("diasRestantes.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByTieneDeudaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where tieneDeuda equals to DEFAULT_TIENE_DEUDA
        defaultUsuariosShouldBeFound("tieneDeuda.equals=" + DEFAULT_TIENE_DEUDA);

        // Get all the usuariosList where tieneDeuda equals to UPDATED_TIENE_DEUDA
        defaultUsuariosShouldNotBeFound("tieneDeuda.equals=" + UPDATED_TIENE_DEUDA);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTieneDeudaIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where tieneDeuda in DEFAULT_TIENE_DEUDA or UPDATED_TIENE_DEUDA
        defaultUsuariosShouldBeFound("tieneDeuda.in=" + DEFAULT_TIENE_DEUDA + "," + UPDATED_TIENE_DEUDA);

        // Get all the usuariosList where tieneDeuda equals to UPDATED_TIENE_DEUDA
        defaultUsuariosShouldNotBeFound("tieneDeuda.in=" + UPDATED_TIENE_DEUDA);
    }

    @Test
    @Transactional
    public void getAllUsuariosByTieneDeudaIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where tieneDeuda is not null
        defaultUsuariosShouldBeFound("tieneDeuda.specified=true");

        // Get all the usuariosList where tieneDeuda is null
        defaultUsuariosShouldNotBeFound("tieneDeuda.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosBySaldoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where saldo equals to DEFAULT_SALDO
        defaultUsuariosShouldBeFound("saldo.equals=" + DEFAULT_SALDO);

        // Get all the usuariosList where saldo equals to UPDATED_SALDO
        defaultUsuariosShouldNotBeFound("saldo.equals=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySaldoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where saldo in DEFAULT_SALDO or UPDATED_SALDO
        defaultUsuariosShouldBeFound("saldo.in=" + DEFAULT_SALDO + "," + UPDATED_SALDO);

        // Get all the usuariosList where saldo equals to UPDATED_SALDO
        defaultUsuariosShouldNotBeFound("saldo.in=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySaldoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where saldo is not null
        defaultUsuariosShouldBeFound("saldo.specified=true");

        // Get all the usuariosList where saldo is null
        defaultUsuariosShouldNotBeFound("saldo.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosBySaldoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where saldo greater than or equals to DEFAULT_SALDO
        defaultUsuariosShouldBeFound("saldo.greaterOrEqualThan=" + DEFAULT_SALDO);

        // Get all the usuariosList where saldo greater than or equals to UPDATED_SALDO
        defaultUsuariosShouldNotBeFound("saldo.greaterOrEqualThan=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    public void getAllUsuariosBySaldoIsLessThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where saldo less than or equals to DEFAULT_SALDO
        defaultUsuariosShouldNotBeFound("saldo.lessThan=" + DEFAULT_SALDO);

        // Get all the usuariosList where saldo less than or equals to UPDATED_SALDO
        defaultUsuariosShouldBeFound("saldo.lessThan=" + UPDATED_SALDO);
    }


    @Test
    @Transactional
    public void getAllUsuariosByDomicilioIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where domicilio equals to DEFAULT_DOMICILIO
        defaultUsuariosShouldBeFound("domicilio.equals=" + DEFAULT_DOMICILIO);

        // Get all the usuariosList where domicilio equals to UPDATED_DOMICILIO
        defaultUsuariosShouldNotBeFound("domicilio.equals=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDomicilioIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where domicilio in DEFAULT_DOMICILIO or UPDATED_DOMICILIO
        defaultUsuariosShouldBeFound("domicilio.in=" + DEFAULT_DOMICILIO + "," + UPDATED_DOMICILIO);

        // Get all the usuariosList where domicilio equals to UPDATED_DOMICILIO
        defaultUsuariosShouldNotBeFound("domicilio.in=" + UPDATED_DOMICILIO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByDomicilioIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where domicilio is not null
        defaultUsuariosShouldBeFound("domicilio.specified=true");

        // Get all the usuariosList where domicilio is null
        defaultUsuariosShouldNotBeFound("domicilio.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsuariosByBarrioIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where barrio equals to DEFAULT_BARRIO
        defaultUsuariosShouldBeFound("barrio.equals=" + DEFAULT_BARRIO);

        // Get all the usuariosList where barrio equals to UPDATED_BARRIO
        defaultUsuariosShouldNotBeFound("barrio.equals=" + UPDATED_BARRIO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBarrioIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where barrio in DEFAULT_BARRIO or UPDATED_BARRIO
        defaultUsuariosShouldBeFound("barrio.in=" + DEFAULT_BARRIO + "," + UPDATED_BARRIO);

        // Get all the usuariosList where barrio equals to UPDATED_BARRIO
        defaultUsuariosShouldNotBeFound("barrio.in=" + UPDATED_BARRIO);
    }

    @Test
    @Transactional
    public void getAllUsuariosByBarrioIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where barrio is not null
        defaultUsuariosShouldBeFound("barrio.specified=true");

        // Get all the usuariosList where barrio is null
        defaultUsuariosShouldNotBeFound("barrio.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUsuariosShouldBeFound(String filter) throws Exception {
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idGorilas").value(hasItem(DEFAULT_ID_GORILAS)))
            .andExpect(jsonPath("$.[*].nombreCompleto").value(hasItem(DEFAULT_NOMBRE_COMPLETO)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].abono").value(hasItem(DEFAULT_ABONO.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].turnosDisponibles").value(hasItem(DEFAULT_TURNOS_DISPONIBLES)))
            .andExpect(jsonPath("$.[*].turnosDisponiblesLeyenda").value(hasItem(DEFAULT_TURNOS_DISPONIBLES_LEYENDA)))
            .andExpect(jsonPath("$.[*].turnosDisponiblesLeyendaHtml").value(hasItem(DEFAULT_TURNOS_DISPONIBLES_LEYENDA_HTML)))
            .andExpect(jsonPath("$.[*].abonosVencimientoHtml").value(hasItem(DEFAULT_ABONOS_VENCIMIENTO_HTML)))
            .andExpect(jsonPath("$.[*].fechaProxAptoFisico").value(hasItem(DEFAULT_FECHA_PROX_APTO_FISICO.toString())))
            .andExpect(jsonPath("$.[*].fechaProxAptoFisicoString").value(hasItem(DEFAULT_FECHA_PROX_APTO_FISICO_STRING.toString())))
            .andExpect(jsonPath("$.[*].diasRestantes").value(hasItem(DEFAULT_DIAS_RESTANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].tieneDeuda").value(hasItem(DEFAULT_TIENE_DEUDA.booleanValue())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO)))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].barrio").value(hasItem(DEFAULT_BARRIO)));

        // Check, that the count call also returns 1
        restUsuariosMockMvc.perform(get("/api/usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUsuariosShouldNotBeFound(String filter) throws Exception {
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuariosMockMvc.perform(get("/api/usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUsuarios() throws Exception {
        // Get the usuarios
        restUsuariosMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Update the usuarios
        Usuarios updatedUsuarios = usuariosRepository.findById(usuarios.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarios are not directly saved in db
        em.detach(updatedUsuarios);
        updatedUsuarios
            .idGorilas(UPDATED_ID_GORILAS)
            .nombreCompleto(UPDATED_NOMBRE_COMPLETO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .documento(UPDATED_DOCUMENTO)
            .abono(UPDATED_ABONO)
            .estado(UPDATED_ESTADO)
            .turnosDisponibles(UPDATED_TURNOS_DISPONIBLES)
            .turnosDisponiblesLeyenda(UPDATED_TURNOS_DISPONIBLES_LEYENDA)
            .turnosDisponiblesLeyendaHtml(UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML)
            .abonosVencimientoHtml(UPDATED_ABONOS_VENCIMIENTO_HTML)
            .fechaProxAptoFisico(UPDATED_FECHA_PROX_APTO_FISICO)
            .fechaProxAptoFisicoString(UPDATED_FECHA_PROX_APTO_FISICO_STRING)
            .diasRestantes(UPDATED_DIAS_RESTANTES)
            .tieneDeuda(UPDATED_TIENE_DEUDA)
            .saldo(UPDATED_SALDO)
            .domicilio(UPDATED_DOMICILIO)
            .barrio(UPDATED_BARRIO);
        UsuariosDTO usuariosDTO = usuariosMapper.toDto(updatedUsuarios);

        restUsuariosMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuariosDTO)))
            .andExpect(status().isOk());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getIdGorilas()).isEqualTo(UPDATED_ID_GORILAS);
        assertThat(testUsuarios.getNombreCompleto()).isEqualTo(UPDATED_NOMBRE_COMPLETO);
        assertThat(testUsuarios.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testUsuarios.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testUsuarios.getAbono()).isEqualTo(UPDATED_ABONO);
        assertThat(testUsuarios.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsuarios.getTurnosDisponibles()).isEqualTo(UPDATED_TURNOS_DISPONIBLES);
        assertThat(testUsuarios.getTurnosDisponiblesLeyenda()).isEqualTo(UPDATED_TURNOS_DISPONIBLES_LEYENDA);
        assertThat(testUsuarios.getTurnosDisponiblesLeyendaHtml()).isEqualTo(UPDATED_TURNOS_DISPONIBLES_LEYENDA_HTML);
        assertThat(testUsuarios.getAbonosVencimientoHtml()).isEqualTo(UPDATED_ABONOS_VENCIMIENTO_HTML);
        assertThat(testUsuarios.getFechaProxAptoFisico()).isEqualTo(UPDATED_FECHA_PROX_APTO_FISICO);
        assertThat(testUsuarios.getFechaProxAptoFisicoString()).isEqualTo(UPDATED_FECHA_PROX_APTO_FISICO_STRING);
        assertThat(testUsuarios.getDiasRestantes()).isEqualTo(UPDATED_DIAS_RESTANTES);
        assertThat(testUsuarios.isTieneDeuda()).isEqualTo(UPDATED_TIENE_DEUDA);
        assertThat(testUsuarios.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testUsuarios.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testUsuarios.getBarrio()).isEqualTo(UPDATED_BARRIO);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Create the Usuarios
        UsuariosDTO usuariosDTO = usuariosMapper.toDto(usuarios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuariosMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuariosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        int databaseSizeBeforeDelete = usuariosRepository.findAll().size();

        // Delete the usuarios
        restUsuariosMockMvc.perform(delete("/api/usuarios/{id}", usuarios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuarios.class);
        Usuarios usuarios1 = new Usuarios();
        usuarios1.setId(1L);
        Usuarios usuarios2 = new Usuarios();
        usuarios2.setId(usuarios1.getId());
        assertThat(usuarios1).isEqualTo(usuarios2);
        usuarios2.setId(2L);
        assertThat(usuarios1).isNotEqualTo(usuarios2);
        usuarios1.setId(null);
        assertThat(usuarios1).isNotEqualTo(usuarios2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuariosDTO.class);
        UsuariosDTO usuariosDTO1 = new UsuariosDTO();
        usuariosDTO1.setId(1L);
        UsuariosDTO usuariosDTO2 = new UsuariosDTO();
        assertThat(usuariosDTO1).isNotEqualTo(usuariosDTO2);
        usuariosDTO2.setId(usuariosDTO1.getId());
        assertThat(usuariosDTO1).isEqualTo(usuariosDTO2);
        usuariosDTO2.setId(2L);
        assertThat(usuariosDTO1).isNotEqualTo(usuariosDTO2);
        usuariosDTO1.setId(null);
        assertThat(usuariosDTO1).isNotEqualTo(usuariosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(usuariosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(usuariosMapper.fromId(null)).isNull();
    }
}
