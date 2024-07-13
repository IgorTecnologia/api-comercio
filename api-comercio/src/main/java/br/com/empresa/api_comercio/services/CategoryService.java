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
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.repositories.CategoryRepository;
import br.com.empresa.api_comercio.services.exception.DataBaseException;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {

		Page<Category> page = repository.findAll(pageable);

		return page.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> queryMethod(String name) {
		
		List<Category> list = repository.findAllByNameContainingIgnoreCase(name);
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {

		Optional<Category> obj = repository.findById(id);

		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found" + id));

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		entity.setName(dto.getName());
		repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {

		@SuppressWarnings("deprecation")
		Category entity = repository.getOne(id);
		entity.setName(dto.getName());
		repository.save(entity);
		return new CategoryDTO(entity);

	}

	public void deleteById(Long id) {
		try {

			Optional<Category> obj = repository.findById(id);
			if (obj.isEmpty()) {
				throw new ResourceNotFoundException("Id not found " + id);
			}
			repository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}
}
