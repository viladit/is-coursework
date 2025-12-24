package com.example.is_curse_work.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class AddProductForm {
    @NotBlank
    private String name;

    private Long categoryId;

    @NotNull
    private Long zoneId;

    private String barcode;

    private OffsetDateTime expiresAt;

    private boolean locked;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public OffsetDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(OffsetDateTime expiresAt) { this.expiresAt = expiresAt; }
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
}

