package com.healthcare.helix.reportModule.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
public class CloudStorageService {
    @Value("${cloudflare.r2.endpoint}")
    private String endpoint;

    @Value("${cloudflare.r2.access-key}")
    private String accessKey;

    @Value("${cloudflare.r2.secret-key}")
    private String secretKey;


    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;


    @PostConstruct
    public void logConfig() {
        log.info(">>>>>>> R2 Endpoint: [{}]", endpoint);
        log.info(">>>>>>>>> R2 Access Key length: [{}]", accessKey != null ? accessKey.length() : "NULL");
    }

    private S3Client getS3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    private S3Presigner getPresigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    /**
     * File ko R2 mein upload karta hai, aur unique storage key return karta hai
     */
    public String uploadFile(MultipartFile file, UUID userId) {
        String key = userId + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try (S3Client s3Client = getS3Client()) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

            return key;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to R2", e);
        }
    }

    /**
     * Temporary (time-limited) URL generate karta hai file download/view karne ke liye
     * Bucket private rahega, koi permanent public URL nahi banega
     */
    public String generatePresignedUrl(String key) {
        try (S3Presigner presigner = getPresigner()) {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15))
                    .getObjectRequest(getRequest)
                    .build();

            return presigner.presignGetObject(presignRequest).url().toString();
        }
    }

    public void deleteFile(String key) {
        try (S3Client s3Client = getS3Client()) {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteRequest);
        }
    }
}
