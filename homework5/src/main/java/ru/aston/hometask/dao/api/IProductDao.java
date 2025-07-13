package ru.aston.hometask.dao.api;

import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.service.dto.ProductReadDto;

import java.util.List;
import java.util.Optional;

public interface IProductDao {
    void save(List<ProductDto> products);
    Optional<ProductReadDto> findById(Long id);
    List<ProductReadDto> findAll();
}
