package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.ProductServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class ProductServiceTests {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductRepository repository;

    private UUID existingId;
    private UUID nonExistingId;
    String name;
    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        Optional<Product> obj = repository.findAll().stream().findFirst();;
        existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        nonExistingId = UUID.randomUUID();
        name = "Bolo";
        countTotalElements = 4L;
    }

    @Test
    public void findAllPagedShouldReturnAllProducts(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<ProductDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnListFilteredByName(){

        List<ProductDTO> list = service.queryMethod(name);

        Assertions.assertNotNull(list);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        ProductDTO dto = service.findById(existingId);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenObjectStructure(){

        ProductDTO dto = Factory.createdProductDTO();

        service.insert(dto);

        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() {

        ProductDTO dto = Factory.createdProductDTO();

        service.update(existingId, dto);

        Assertions.assertEquals(countTotalElements, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        service.deleteById(existingId);

        Assertions.assertEquals(countTotalElements -1, repository.count());
    }
}
