package com.revature.services;

import com.revature.daos.CategoryDAO;
import com.revature.daos.ProductDAO;
import com.revature.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    @Mock
    private ProductDAO productDAO;


    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAllProducts() {

        List<Product> expectedProducts = Collections.singletonList(new Product());
        when(productDAO.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
        verify(productDAO, times(1)).findAll();
    }

    @Test
    public void testAddProductShouldReturnTrueAfterProductIsAdded() {

        Product product = new Product();
        when(productDAO.findById(anyInt())).thenReturn(Optional.empty());

        boolean isAdded = productService.addProduct(product, 1, "description");

        assertTrue(isAdded);
        verify(productDAO, times(1)).findById(1);
        verify(productDAO, times(1)).save(product);
    }

    @Test
    public void testAddProductShouldReturnFalseWhenProductExists() {

        Product existingProduct = new Product();
        when(productDAO.findById(anyInt())).thenReturn(Optional.of(existingProduct));

        boolean isAdded = productService.addProduct(new Product(), 1, "description");

        assertFalse(isAdded);
        verify(productDAO, times(1)).findById(1);
        verify(productDAO, never()).save(any());
    }


}