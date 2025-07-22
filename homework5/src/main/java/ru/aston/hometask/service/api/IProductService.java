package ru.aston.hometask.service.api;

import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.service.dto.ProductReadDto;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    void save(List<ProductDto> products);
    List<ProductReadDto> findAll();
    Optional<ProductReadDto> findById(Long id);
}
