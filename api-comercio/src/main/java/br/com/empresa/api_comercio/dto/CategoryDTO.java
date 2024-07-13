package br.com.empresa.api_comercio.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.entities.Product;

public class CategoryDTO {

	private Long id;
	private String name;
	
	private List<ProductDTO> products = new ArrayList<>();
	
	public CategoryDTO() {
	}

	public CategoryDTO(Long id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		
		id = entity.getId();
		name = entity.getName();
	}

	public CategoryDTO(Category entity, List<Product> products) {
		this(entity);
		products.forEach(x -> this.products.add(new ProductDTO(x)));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}
	
	
	
}
