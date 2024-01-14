package com.github.ktalkun.configuration;

import com.github.ktalkun.s3.S3Service;
import com.github.ktalkun.s3.S3ServiceImpl;
import java.net.URI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class ApplicationConfig {

  @Value("${amazon.s3.bucket-name}")
  private String bucketName;

  @Value("${amazon.access.access-key-id}")
  private String accessKeyId;

  @Value("${amazon.access.access-key-secret}")
  private String accessKeySecret;

  @Value("${amazon.region}")
  private String region;

  @Value("${amazon.s3.url}")
  private String url;

  @Bean
  public S3Client s3Client() {
    S3ClientBuilder s3ClientBuilder = S3Client.builder()
        .region(Region.of(region))
        .forcePathStyle(true)
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKeyId, accessKeySecret)));

    if (StringUtils.isNotBlank(url)) {
      s3ClientBuilder.endpointOverride(URI.create(url));
    }

    return s3ClientBuilder.build();
  }

  @Bean
  public S3Service defaultS3Service(S3Client s3Client) {
    return new S3ServiceImpl(bucketName, s3Client);
  }
}
