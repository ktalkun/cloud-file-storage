package com.github.ktalkun.file;

import com.github.ktalkun.exception.ProcessException;
import com.github.ktalkun.s3.S3Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Service s3Service;

    public void uploadFile(MultipartFile multipartFile) {
        String key = UUID.randomUUID().toString();
        try {
            s3Service.uploadFile(multipartFile.getInputStream(), key);
        } catch (IOException e) {
            throw new ProcessException("Provided file couldn't be correctly processed", e);
        }
    }

    public InputStream downloadFile(String key) {
        return s3Service.downloadFile(key);
    }
}
