package com.assessmentpractice.service;

import com.assessmentpractice.entity.Product;
import com.assessmentpractice.entity.StockLog;
import com.assessmentpractice.exception.InsufficientStockException;
import com.assessmentpractice.repository.ProductRepository;
import com.assessmentpractice.repository.StockLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;

    public StockService(ProductRepository productRepository, StockLogRepository stockLogRepository) {
        this.productRepository = productRepository;
        this.stockLogRepository = stockLogRepository;
    }

    @Transactional
    public void decreaseStock(Long productId, int qty) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InsufficientStockException("Product not found with id: " + productId));

        if (product.getQty() < qty) {
            throw new InsufficientStockException(
                    String.format("Insufficient stock for product %s. Available: %d, Requested: %d",
                            product.getName(), product.getQty(), qty)
            );
        }

        // Decrease stock
        product.setQty(product.getQty() - qty);
        productRepository.save(product);

        // Log the stock change (delta is negative for decrease)
        StockLog stockLog = new StockLog(productId, -qty);
        stockLogRepository.save(stockLog);
    }
}
