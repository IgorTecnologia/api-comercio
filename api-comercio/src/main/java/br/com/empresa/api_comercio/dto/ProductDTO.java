package br.com.empresa.api_comercio.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.entities.Product;

public class ProductDTO {

	private Long id;
	private String name;
	private double price;
	private String description;
	private String imgUrl;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, double price, String description, String imgUrl) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.imgUrl = imgUrl;
	}
	
	public ProductDTO(Product entity) {
		
		id = entity.getId();
		name = entity.getName();
		price = entity.getPrice();
		description = entity.getDescription();
		imgUrl = entity.getImgUrl();
	}
	
	public ProductDTO(Product entity, List<Category> categories) {
		
		this(entity);
		categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	
	
}
