package ru.aston.hometask.service.dto;

import java.util.Arrays;
import java.util.Optional;

public enum EFileType {
    JSON,
    CSV,
    XLSX;

    public static Optional<EFileType> getEFileType(String type) {
        return Arrays.stream(EFileType.values())
                .filter(eFileType -> eFileType.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
