package com.miu.onlinemarketplace.service.file;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public FileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFiles(MultipartFile file, List<String> path) {

        String objectName = pathGenerate(path) + file.getName();
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
            return objectName;
        } catch (Exception e) {
            log.error("Error on uploading file {}, {}", file.getOriginalFilename(), e.getMessage());
        }
        return null;
    }

    public String downloadImage(String filePath) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filePath)
                            .expiry(7, TimeUnit.DAYS)
                            .build());
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            log.error("Error on download file from {}, {}", filePath, e.getMessage());
            return null;
        }
    }

    private String pathGenerate(List<String> path) {
        StringBuilder pathBuilder = new StringBuilder();
        for (String pt : path) {
            if (pt != null && !pt.equals("")) pathBuilder.append(pt).append("/");
        }
        return pathBuilder.toString();
    }

}
