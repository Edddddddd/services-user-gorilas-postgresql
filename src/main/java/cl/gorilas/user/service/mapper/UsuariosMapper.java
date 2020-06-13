package cl.gorilas.user.service.mapper;

import cl.gorilas.user.domain.*;
import cl.gorilas.user.service.dto.UsuariosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Usuarios and its DTO UsuariosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuariosMapper extends EntityMapper<UsuariosDTO, Usuarios> {



    default Usuarios fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setId(id);
        return usuarios;
    }
}
