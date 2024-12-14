package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    Page<RoleDTO> findAllPaged(Pageable pageable);

    List<RoleDTO> queryMethod(String authority);

    RoleDTO findById(UUID id);

    RoleDTO insert(RoleDTO dto);

    RoleDTO update(UUID id, RoleDTO dto);

    void deleteById(UUID id);
}
