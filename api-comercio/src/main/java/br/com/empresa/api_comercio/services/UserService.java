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

import br.com.empresa.api_comercio.dto.RoleDTO;
import br.com.empresa.api_comercio.dto.UserDTO;
import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.repositories.RoleRepository;
import br.com.empresa.api_comercio.repositories.UserRepository;
import br.com.empresa.api_comercio.services.exception.DataBaseException;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {

		Page<User> page = repository.findAll(pageable);
		return page.map(x -> new UserDTO(x));
	}

	@Transactional(readOnly = true)
	public List<UserDTO> queryMethod(String firstName){
		
		List<User> list = repository.findAllByFirstNameContainingIgnoreCase(firstName);
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {

		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert(UserDTO dto) {

		User entity = new User();
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {

		Optional<User> obj = repository.findById(id);

		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
		copyDtoToEntity(entity, dto);
		repository.save(entity);
		return new UserDTO(entity);
	}

	public void deleteById(Long id) {

		try {
			Optional<User> obj = repository.findById(id);
			if (obj.isEmpty()) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}
			repository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(User entity, UserDTO dto) {

		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());

		entity.getRoles().clear();

		for (RoleDTO roleDto : dto.getRoles()) {
			@SuppressWarnings("deprecation")
			Role role = roleRepository.getOne(roleDto.getId());

			entity.getRoles().add(role);

		}
	}
}
