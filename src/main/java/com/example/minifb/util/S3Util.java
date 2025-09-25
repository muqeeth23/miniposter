package com.example.minifb.util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;

public class S3Util {

    private static final String BUCKET = AppConfig.get("aws.s3.bucket");
    private static final Region REGION = Region.of(AppConfig.get("aws.region"));
    private static final S3Client s3;

    static {
        AwsBasicCredentials creds = AwsBasicCredentials.create(
                AppConfig.get("aws.accessKey"), AppConfig.get("aws.secretKey")
        );
        s3 = S3Client.builder()
                .region(REGION)
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }

    /**
     * Uploads input to S3 privately and returns the S3 key (not public URL).
     * CloudFront OAC must be configured for the CloudFront distribution to access this key.
     */
    public static String uploadPrivate(String originalFilename, InputStream input, long contentLength, String contentType) {
        try {
            String ext = "";
            int idx = originalFilename.lastIndexOf('.');
            if (idx >= 0) ext = originalFilename.substring(idx);
            String key = "posts/" + UUID.randomUUID().toString() + ext;

            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3.putObject(req, RequestBody.fromInputStream(input, contentLength));
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Helper to build CloudFront URL given S3 key (OAC must allow CloudFront to serve it). */
    public static String cloudFrontUrl(String key) {
        String domain = AppConfig.get("aws.cloudfront.domain");
        if (domain == null || domain.trim().isEmpty()) {
            // fallback to S3 public URL (not recommended)
            return "https://" + BUCKET + ".s3.amazonaws.com/" + key;
        }
        if (domain.endsWith("/")) domain = domain.substring(0, domain.length() - 1);
        return "https://" + domain + "/" + key;
    }
}
