package ru.aston.hometask.service;

import lombok.RequiredArgsConstructor;
import ru.aston.hometask.dao.api.IProductDao;
import ru.aston.hometask.service.dto.ProductDto;
import ru.aston.hometask.service.api.IProductService;
import ru.aston.hometask.service.dto.ProductReadDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductDao productDao;

    @Override
    public void save(List<ProductDto> products) {
        productDao.save(products);
    }

    @Override
    public List<ProductReadDto> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<ProductReadDto> findById(Long id) {
        return productDao.findById(id);
    }
}
