package com.example.minifb.util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;

public class S3Util {
    private static final String BUCKET = Config.get("aws.bucket");
    private static final String CLOUDFRONT = Config.get("aws.cloudfront");
    private static final Region REGION = Region.of(Config.get("aws.region"));

    private static final S3Client s3;

    static {
        AwsBasicCredentials creds = AwsBasicCredentials.create(
                Config.get("aws.accessKey"),
                Config.get("aws.secretKey")
        );
        s3 = S3Client.builder()
                .region(REGION)
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }

    public static String upload(String originalFilename, InputStream inputStream, long contentLength, String contentType) throws Exception {
        String ext = "";
        int idx = originalFilename.lastIndexOf('.');
        if (idx >= 0) ext = originalFilename.substring(idx);
        String key = "posts/" + UUID.randomUUID().toString() + ext;

        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType(contentType)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3.putObject(por, RequestBody.fromInputStream(inputStream, contentLength));

        if (CLOUDFRONT != null && !CLOUDFRONT.trim().isEmpty()) {
            String domain = CLOUDFRONT.endsWith("/") ? CLOUDFRONT.substring(0, CLOUDFRONT.length() - 1) : CLOUDFRONT;
            return domain + "/" + key;
        } else {
            return "https://" + BUCKET + ".s3.amazonaws.com/" + key;
        }
    }
}
