package br.com.empresa.api_comercio.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empresa.api_comercio.dto.CategoryDTO;
import br.com.empresa.api_comercio.dto.ProductDTO;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.repositories.CategoryRepository;
import br.com.empresa.api_comercio.repositories.ProductRepository;
import br.com.empresa.api_comercio.services.exception.DataBaseException;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> page = repository.findAll(pageable);
		return page.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<ProductDTO> queryMethod(String name){
		
		List<Product> list = repository.findAllByNameContainingIgnoreCase(name);
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Optional<Product> obj = repository.findById(id);

		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		@SuppressWarnings("deprecation")
		Product entity = repository.getOne(id);
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new ProductDTO(entity);
	}

	public void deleteById(Long id) {

		try {
			Optional<Product> obj = repository.findById(id);
			if (obj.isEmpty()) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}
			repository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integriry Violation");
		}
	}

	public void copyDtoToEntity(Product entity, ProductDTO dto) {

		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());

		entity.getCategories().clear();

		for (CategoryDTO catDto : dto.getCategories()) {
			@SuppressWarnings("deprecation")
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
	}
}
