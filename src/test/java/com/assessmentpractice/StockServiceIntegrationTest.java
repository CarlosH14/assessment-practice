package com.assessmentpractice;

import com.assessmentpractice.entity.Product;
import com.assessmentpractice.entity.StockLog;
import com.assessmentpractice.exception.InsufficientStockException;
import com.assessmentpractice.repository.ProductRepository;
import com.assessmentpractice.repository.StockLogRepository;
import com.assessmentpractice.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockLogRepository stockLogRepository;

    @BeforeEach
    public void setUp() {
        stockLogRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testDecreaseStockSuccess() {
        // Create a product with initial stock
        Product product = new Product("Test Product", 100);
        product = productRepository.save(product);

        // Decrease stock
        stockService.decreaseStock(product.getId(), 30);

        // Verify product stock decreased
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(70, updatedProduct.getQty(), "Product quantity should be decreased to 70");

        // Verify stock log was created
        List<StockLog> logs = stockLogRepository.findByProductId(product.getId());
        assertEquals(1, logs.size(), "One stock log should be created");
        assertEquals(-30, logs.get(0).getDelta(), "Stock log delta should be -30");
        assertEquals(product.getId(), logs.get(0).getProductId(), "Stock log should reference the product");
        assertNotNull(logs.get(0).getCreatedAt(), "Stock log should have a timestamp");
    }

    @Test
    public void testDecreaseStockInsufficientStock() {
        // Create a product with limited stock
        Product product = new Product("Limited Product", 10);
        product = productRepository.save(product);
        final Long productId = product.getId();

        // Attempt to decrease stock by more than available
        InsufficientStockException exception = assertThrows(
                InsufficientStockException.class,
                () -> stockService.decreaseStock(productId, 20),
                "Should throw InsufficientStockException when stock is insufficient"
        );

        assertTrue(exception.getMessage().contains("Insufficient stock"),
                "Exception message should indicate insufficient stock");

        // Verify product stock was NOT decreased (transaction rolled back)
        Product unchangedProduct = productRepository.findById(productId).orElseThrow();
        assertEquals(10, unchangedProduct.getQty(), "Product quantity should remain 10 after failed transaction");

        // Verify no stock log was created (transaction rolled back)
        List<StockLog> logs = stockLogRepository.findByProductId(productId);
        assertEquals(0, logs.size(), "No stock log should be created after failed transaction");
    }

    @Test
    public void testDecreaseStockProductNotFound() {
        // Attempt to decrease stock for non-existent product
        InsufficientStockException exception = assertThrows(
                InsufficientStockException.class,
                () -> stockService.decreaseStock(999L, 10),
                "Should throw InsufficientStockException when product not found"
        );

        assertTrue(exception.getMessage().contains("Product not found"),
                "Exception message should indicate product not found");
    }

    @Test
    public void testDecreaseStockMultipleTimes() {
        // Create a product with stock
        Product product = new Product("Multi Test Product", 100);
        product = productRepository.save(product);

        // Decrease stock multiple times
        stockService.decreaseStock(product.getId(), 20);
        stockService.decreaseStock(product.getId(), 30);
        stockService.decreaseStock(product.getId(), 15);

        // Verify product stock
        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(35, updatedProduct.getQty(), "Product quantity should be 35 after multiple decreases");

        // Verify multiple stock logs were created
        List<StockLog> logs = stockLogRepository.findByProductId(product.getId());
        assertEquals(3, logs.size(), "Three stock logs should be created");
    }
}
