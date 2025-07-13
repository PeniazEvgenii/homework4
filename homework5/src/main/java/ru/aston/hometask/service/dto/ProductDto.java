package ru.aston.hometask.service.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDto {
    private String name;
    private BigDecimal price;
    private int quantity;

    public ProductDto() {
    }

    public ProductDto(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return quantity == that.quantity && Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity);
    }
}
