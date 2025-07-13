package ru.aston.hometask.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.aston.hometask.adapter.FileResourcePartAdapter;
import ru.aston.hometask.adapter.IFileResource;
import ru.aston.hometask.dao.entity.EStatus;
import ru.aston.hometask.exception.FileLoadException;
import ru.aston.hometask.service.dto.EFileType;
import ru.aston.hometask.service.api.IUploadService;
import ru.aston.hometask.service.ServiceFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/files")
@MultipartConfig
public class FileServlet extends HttpServlet {
    private static final String PART_KEY_FILE = "file";

    private final IUploadService uploadService = ServiceFactory.getUploadService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart(PART_KEY_FILE);
        IFileResource fileResource = new FileResourcePartAdapter(part);

        if (fileResource.getSize() == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Файл не передан");
            return;
        }

        String extension = getFileExtension(fileResource);
        Optional<EFileType> maybeType = EFileType.getEFileType(extension);
        if (maybeType.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Тип файла не поддерживается: " + extension);
            return;
        }

        try {
            UUID id = uploadService.upload(fileResource, maybeType.get());
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().write("Файл загружен. ID для отслеживания: " + id);
        } catch (FileLoadException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не передан параметр id");
            return;
        }

        UUID id = UUID.fromString(idParam);
        Optional<String> maybeStatus = uploadService.findStatusById(id)
                .map(EStatus::name);
        if (maybeStatus.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Файл с ID не найден: " + idParam);
        } else {
            resp.getWriter().write(maybeStatus.get());
        }
    }

    private String getFileExtension(IFileResource fileResource) {
        String fileName = fileResource.getName();
        int pointIndex = fileName.lastIndexOf(".");
        return fileName.substring(pointIndex + 1);
    }
}
