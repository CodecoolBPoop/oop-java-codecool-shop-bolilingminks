package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {

    private ProductDao productDao = ProductDaoMem.getInstance();
    private Supplier testSupplier;
    private ProductCategory testCategory;

    @BeforeEach
    private void setupDao() {
        testSupplier = new Supplier("Amazon", "test");
        testCategory = new ProductCategory("Tablet", "Hardware", "test");

        for (int i = 1; i < 20; i++) {
            productDao.remove(i);
        }

        for (int i = 0; i < 10; i++) {
            Product product = new Product("test" + i, 49.9f, "USD", "test.", testCategory, testSupplier, "product_1.jpg");
            productDao.add(product);
        }
    }

    @Test
    void testAdd() {

        List<Product> products = productDao.getAll();

        for (int i = 0; i < 10; i++) {
            Product product = products.get(i);
            assertEquals(product.getName(), "test" + i);
        }
    }

    @Test
    void testFind() {

        for (int i = 0; i < 20; i++) {
            if (i == 0){
                assertThrows(IllegalArgumentException.class, () -> {
                    productDao.find(0);
                });
            }
            else if (i <= 10){
                assertNotNull(productDao.find(i));
            }
            else {
                assertNull(productDao.find(i));
            }
        }
    }

    @Test
    void remove() {

        for (int i = 1; i <= 10; i++) {
            productDao.remove(i);
            assertNull(productDao.find(i));
        }
    }

    @Test
    void getAll() {
        for (Product product : productDao.getAll()) {
            assertNotNull(product);
        }
    }

    @Test
    void testGetBySupplier() {
        Supplier wrongSupplier = new Supplier("not a supplier", "definitely");

        assertNotNull(productDao.getBy(testSupplier));

        List<Product> actual = productDao.getBy(wrongSupplier);
        List<Product> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test
    void testGetByProductCategory() {
        ProductCategory wrongCategory = new ProductCategory("not a category", "nope", "really not");

        assertNotNull(productDao.getBy(testCategory));

        List<Product> actual = productDao.getBy(wrongCategory);
        List<Product> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

}