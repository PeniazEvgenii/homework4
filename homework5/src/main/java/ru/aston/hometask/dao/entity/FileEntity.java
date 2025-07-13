package ru.aston.hometask.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    private UUID id;
    private EStatus status;
    private OffsetDateTime dtCreate;
    private OffsetDateTime dtUpdate;
}
