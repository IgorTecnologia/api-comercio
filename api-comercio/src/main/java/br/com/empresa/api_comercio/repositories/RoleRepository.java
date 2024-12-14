package br.com.empresa.api_comercio.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.api_comercio.entities.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	public List<Role> findAllByAuthorityContainingIgnoreCase(@Param("authority") String authority);
}
