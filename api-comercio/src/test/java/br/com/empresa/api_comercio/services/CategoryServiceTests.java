package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.*;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

@Transactional
@SpringBootTest
public class CategoryServiceTests {

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalElements;

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 4L;
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
