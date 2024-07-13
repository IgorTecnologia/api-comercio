package br.com.empresa.api_comercio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empresa.api_comercio.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	public List<Product> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
