package com.example.is_curse_work.service;

import com.example.is_curse_work.dto.*;
import com.example.is_curse_work.model.Product;
import com.example.is_curse_work.model.ProductHistory;
import com.example.is_curse_work.repository.ProductHistoryRepository;
import com.example.is_curse_work.repository.ProductRepository;
import com.example.is_curse_work.repository.function.ProductFunctionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductFunctionRepository productFn;
    private final ProductRepository products;
    private final ProductHistoryRepository history;

    public ProductServiceImpl(ProductFunctionRepository productFn, ProductRepository products, ProductHistoryRepository history) {
        this.productFn = productFn;
        this.products = products;
        this.history = history;
    }

    @Override
    @Transactional
    public Long addProduct(Long userId, AddProductForm form) {
        if (form.getZoneId() == null) throw new IllegalArgumentException("Zone is required");
        if (form.getName() == null || form.getName().isBlank()) throw new IllegalArgumentException("Name is required");

        OffsetDateTime expiresAt = null;
        if (form.getExpiresAt() != null) {
            expiresAt = form.getExpiresAt().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }

        return productFn.addProduct(
                userId,
                form.getZoneId(),
                form.getCategoryId(),
                form.getName(),
                form.getBarcode(),
                expiresAt,
                false
        );
    }

    @Override
    public List<UserProductDto> getMyProducts(Long userId) {
        return productFn.getUserProducts(userId);
    }

    @Override
    public ProductDetailView getProductDetail(Long requesterId, Long productId) {
        Product p = products.findById(productId).orElseThrow();
        if (!p.getOwner().getUserId().equals(requesterId)) {
            throw new SecurityException("Forbidden");
        }
        List<ProductHistory> h = history.findByProduct_ProductIdOrderByCreatedAtDesc(productId);
        return ProductDetailView.from(p, h);
    }

    @Override
    @Transactional
    public void move(Long requesterId, Long productId, MoveProductForm form) {
        productFn.moveProduct(productId, form.getToZoneId(), requesterId, form.getComment());
    }

    @Override
    @Transactional
    public void extend(Long requesterId, Long productId, ExtendProductForm form) {
        productFn.extendProduct(productId, form.getNewExpiresAt(), requesterId, form.getComment());
    }

    @Override
    @Transactional
    public void setStatus(Long requesterId, Long productId, SetStatusForm form) {
        productFn.setStatus(productId, form.getStatus(), requesterId, form.getComment());
    }
}
