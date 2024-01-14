package com.github.ktalkun.s3;

import java.io.IOException;
import java.io.InputStream;

public interface S3Service {

  void uploadFile(InputStream inputStream, String key) throws IOException;

  InputStream downloadFile(String key);
}
