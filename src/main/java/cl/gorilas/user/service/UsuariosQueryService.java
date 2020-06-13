package cl.gorilas.user.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import cl.gorilas.user.domain.Usuarios;
import cl.gorilas.user.domain.*; // for static metamodels
import cl.gorilas.user.repository.UsuariosRepository;
import cl.gorilas.user.service.dto.UsuariosCriteria;
import cl.gorilas.user.service.dto.UsuariosDTO;
import cl.gorilas.user.service.mapper.UsuariosMapper;

/**
 * Service for executing complex queries for Usuarios entities in the database.
 * The main input is a {@link UsuariosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsuariosDTO} or a {@link Page} of {@link UsuariosDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuariosQueryService extends QueryService<Usuarios> {

    private final Logger log = LoggerFactory.getLogger(UsuariosQueryService.class);

    private final UsuariosRepository usuariosRepository;

    private final UsuariosMapper usuariosMapper;

    public UsuariosQueryService(UsuariosRepository usuariosRepository, UsuariosMapper usuariosMapper) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
    }

    /**
     * Return a {@link List} of {@link UsuariosDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsuariosDTO> findByCriteria(UsuariosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Usuarios> specification = createSpecification(criteria);
        return usuariosMapper.toDto(usuariosRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsuariosDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuariosDTO> findByCriteria(UsuariosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Usuarios> specification = createSpecification(criteria);
        return usuariosRepository.findAll(specification, page)
            .map(usuariosMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuariosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Usuarios> specification = createSpecification(criteria);
        return usuariosRepository.count(specification);
    }

    /**
     * Function to convert UsuariosCriteria to a {@link Specification}
     */
    private Specification<Usuarios> createSpecification(UsuariosCriteria criteria) {
        Specification<Usuarios> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Usuarios_.id));
            }
            if (criteria.getIdGorilas() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdGorilas(), Usuarios_.idGorilas));
            }
            if (criteria.getNombreCompleto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreCompleto(), Usuarios_.nombreCompleto));
            }
            if (criteria.getCorreoElectronico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorreoElectronico(), Usuarios_.correoElectronico));
            }
            if (criteria.getDocumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumento(), Usuarios_.documento));
            }
            if (criteria.getAbono() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAbono(), Usuarios_.abono));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Usuarios_.estado));
            }
            if (criteria.getTurnosDisponibles() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTurnosDisponibles(), Usuarios_.turnosDisponibles));
            }
            if (criteria.getTurnosDisponiblesLeyenda() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTurnosDisponiblesLeyenda(), Usuarios_.turnosDisponiblesLeyenda));
            }
            if (criteria.getTurnosDisponiblesLeyendaHtml() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTurnosDisponiblesLeyendaHtml(), Usuarios_.turnosDisponiblesLeyendaHtml));
            }
            if (criteria.getAbonosVencimientoHtml() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbonosVencimientoHtml(), Usuarios_.abonosVencimientoHtml));
            }
            if (criteria.getFechaProxAptoFisico() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaProxAptoFisico(), Usuarios_.fechaProxAptoFisico));
            }
            if (criteria.getFechaProxAptoFisicoString() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaProxAptoFisicoString(), Usuarios_.fechaProxAptoFisicoString));
            }
            if (criteria.getDiasRestantes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiasRestantes(), Usuarios_.diasRestantes));
            }
            if (criteria.getTieneDeuda() != null) {
                specification = specification.and(buildSpecification(criteria.getTieneDeuda(), Usuarios_.tieneDeuda));
            }
            if (criteria.getSaldo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldo(), Usuarios_.saldo));
            }
            if (criteria.getDomicilio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomicilio(), Usuarios_.domicilio));
            }
            if (criteria.getBarrio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarrio(), Usuarios_.barrio));
            }
        }
        return specification;
    }
}
