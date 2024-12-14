package br.com.empresa.api_comercio.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.empresa.api_comercio.entities.Product;

public interface ProductRepository extends JpaRepository<Product, UUID>{

	public List<Product> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
