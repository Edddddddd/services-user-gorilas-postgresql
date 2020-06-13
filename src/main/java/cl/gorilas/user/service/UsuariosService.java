package cl.gorilas.user.service;

import cl.gorilas.user.domain.Usuarios;
import cl.gorilas.user.repository.UsuariosRepository;
import cl.gorilas.user.service.dto.UsuariosDTO;
import cl.gorilas.user.service.mapper.UsuariosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Usuarios.
 */
@Service
@Transactional
public class UsuariosService {

    private final Logger log = LoggerFactory.getLogger(UsuariosService.class);

    private final UsuariosRepository usuariosRepository;

    private final UsuariosMapper usuariosMapper;

    public UsuariosService(UsuariosRepository usuariosRepository, UsuariosMapper usuariosMapper) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
    }

    /**
     * Save a usuarios.
     *
     * @param usuariosDTO the entity to save
     * @return the persisted entity
     */
    public UsuariosDTO save(UsuariosDTO usuariosDTO) {
        log.debug("Request to save Usuarios : {}", usuariosDTO);
        Usuarios usuarios = usuariosMapper.toEntity(usuariosDTO);
        usuarios = usuariosRepository.save(usuarios);
        return usuariosMapper.toDto(usuarios);
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UsuariosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuariosRepository.findAll(pageable)
            .map(usuariosMapper::toDto);
    }


    /**
     * Get one usuarios by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UsuariosDTO> findOne(Long id) {
        log.debug("Request to get Usuarios : {}", id);
        return usuariosRepository.findById(id)
            .map(usuariosMapper::toDto);
    }

    /**
     * Delete the usuarios by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuarios : {}", id);        usuariosRepository.deleteById(id);
    }
}
