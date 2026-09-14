package com.shopping.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@Controller
public class DownloadImageController {

    @Value("${app.upload.dir}")
    private String uploadDir;

    // Gọi ảnh: <img src="${pageContext.request.contextPath}/image?fname=abc.jpg">
    @GetMapping("/image")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@RequestParam("fname") String fname) throws Exception {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path requestedPath = uploadPath.resolve(fname).normalize();
        if (!requestedPath.startsWith(uploadPath)) {
            return ResponseEntity.badRequest().build();
        }
        File file = requestedPath.toFile();
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        String contentType = Files.probeContentType(file.toPath());
        return ResponseEntity.ok()
                .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }
}
