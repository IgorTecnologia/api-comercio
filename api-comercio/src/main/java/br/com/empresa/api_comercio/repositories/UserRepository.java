package br.com.empresa.api_comercio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empresa.api_comercio.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public List<User> findAllByFirstNameContainingIgnoreCase(@Param("firstName") String firstName);
}
