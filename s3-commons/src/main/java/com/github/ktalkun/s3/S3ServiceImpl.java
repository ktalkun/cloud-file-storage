package com.github.ktalkun.s3;

import static software.amazon.awssdk.core.sync.RequestBody.fromInputStream;

import java.io.IOException;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@AllArgsConstructor
public class S3ServiceImpl implements S3Service {

  private final String bucketName;

  private final S3Client s3Client;

  @Override
  public void uploadFile(InputStream inputStream, String key) throws IOException {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();
    RequestBody requestBody = fromInputStream(inputStream, inputStream.available());

    s3Client.putObject(putObjectRequest, requestBody);
  }


  @Override
  public InputStream downloadFile(String key) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    return s3Client.getObjectAsBytes(getObjectRequest).asInputStream();
  }
}
