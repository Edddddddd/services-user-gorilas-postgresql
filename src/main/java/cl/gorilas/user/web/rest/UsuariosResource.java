package cl.gorilas.user.web.rest;
import cl.gorilas.user.service.UsuariosService;
import cl.gorilas.user.web.rest.errors.BadRequestAlertException;
import cl.gorilas.user.web.rest.util.HeaderUtil;
import cl.gorilas.user.web.rest.util.PaginationUtil;
import cl.gorilas.user.service.dto.UsuariosDTO;
import cl.gorilas.user.service.dto.UsuariosCriteria;
import cl.gorilas.user.service.UsuariosQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Usuarios.
 */
@RestController
@RequestMapping("/api")
public class UsuariosResource {

    private final Logger log = LoggerFactory.getLogger(UsuariosResource.class);

    private static final String ENTITY_NAME = "servicesUsuarioGorilasUsuarios";

    private final UsuariosService usuariosService;

    private final UsuariosQueryService usuariosQueryService;

    public UsuariosResource(UsuariosService usuariosService, UsuariosQueryService usuariosQueryService) {
        this.usuariosService = usuariosService;
        this.usuariosQueryService = usuariosQueryService;
    }

    /**
     * POST  /usuarios : Create a new usuarios.
     *
     * @param usuariosDTO the usuariosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuariosDTO, or with status 400 (Bad Request) if the usuarios has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usuarios")
    public ResponseEntity<UsuariosDTO> createUsuarios(@RequestBody UsuariosDTO usuariosDTO) throws URISyntaxException {
        log.debug("REST request to save Usuarios : {}", usuariosDTO);
        if (usuariosDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuariosDTO result = usuariosService.save(usuariosDTO);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usuarios : Updates an existing usuarios.
     *
     * @param usuariosDTO the usuariosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuariosDTO,
     * or with status 400 (Bad Request) if the usuariosDTO is not valid,
     * or with status 500 (Internal Server Error) if the usuariosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usuarios")
    public ResponseEntity<UsuariosDTO> updateUsuarios(@RequestBody UsuariosDTO usuariosDTO) throws URISyntaxException {
        log.debug("REST request to update Usuarios : {}", usuariosDTO);
        if (usuariosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuariosDTO result = usuariosService.save(usuariosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuariosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuarios : get all the usuarios.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of usuarios in body
     */
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuariosDTO>> getAllUsuarios(UsuariosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Usuarios by criteria: {}", criteria);
        Page<UsuariosDTO> page = usuariosQueryService.findByCriteria(criteria, Pageable.unpaged());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usuarios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /usuarios/count : count all the usuarios.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/usuarios/count")
    public ResponseEntity<Long> countUsuarios(UsuariosCriteria criteria) {
        log.debug("REST request to count Usuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(usuariosQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /usuarios/:id : get the "id" usuarios.
     *
     * @param id the id of the usuariosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuariosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuariosDTO> getUsuarios(@PathVariable Long id) {
        log.debug("REST request to get Usuarios : {}", id);
        Optional<UsuariosDTO> usuariosDTO = usuariosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuariosDTO);
    }

    /**
     * DELETE  /usuarios/:id : delete the "id" usuarios.
     *
     * @param id the id of the usuariosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuarios(@PathVariable Long id) {
        log.debug("REST request to delete Usuarios : {}", id);
        usuariosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
