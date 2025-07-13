package ru.aston.hometask.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Data
public class ProductReadDto {
    private final long id;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    private final OffsetDateTime dtCreate;
    private final OffsetDateTime dtUpdate;
}
