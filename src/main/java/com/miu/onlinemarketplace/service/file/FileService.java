package com.miu.onlinemarketplace.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String uploadFiles(MultipartFile file, List<String> path);

    String downloadImage(String fileName);
}
