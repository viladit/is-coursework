package com.example.is_curse_work.service;

import com.example.is_curse_work.dto.*;

import java.util.List;

public interface ProductService {
    Long addProduct(Long userId, AddProductForm form);
    List<UserProductDto> getMyProducts(Long userId);
    ProductDetailView getProductDetail(Long requesterId, Long productId);

    void move(Long requesterId, Long productId, MoveProductForm form);
    void extend(Long requesterId, Long productId, ExtendProductForm form);
    void setStatus(Long requesterId, Long productId, SetStatusForm form);
}
