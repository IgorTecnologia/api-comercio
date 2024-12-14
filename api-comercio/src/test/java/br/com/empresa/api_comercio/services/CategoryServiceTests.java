package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.CategoryServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Transactional
@SpringBootTest
public class CategoryServiceTests {

    private UUID existingId;
    private UUID nonExistingId;
    private Long countTotalElements;

    @Autowired
    private CategoryServiceImpl service;

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setUp() throws Exception{

        Optional<Category> obj = repository.findAll().stream().findFirst();;
        existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        nonExistingId = UUID.randomUUID();
        countTotalElements = 3L;
    }

    @Test
    public void findAllPagedShouldReturnAllCategories(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<CategoryDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        CategoryDTO dto = service.findById(existingId);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        CategoryDTO dto = Factory.createdCategoryDTO();

        dto = service.insert(dto);

        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        CategoryDTO dto = Factory.createdCategoryDTO();

        dto = service.update(existingId, dto);

        Assertions.assertEquals(countTotalElements, repository.count());
    }
}
