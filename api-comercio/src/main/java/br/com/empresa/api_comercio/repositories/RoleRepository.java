package br.com.empresa.api_comercio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empresa.api_comercio.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	public List<Role> findAllByAuthorityContainingIgnoreCase(@Param("authority") String authority);
}
