package com.github.ktalkun.file;

import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  @PostMapping("/")
  public void uploadFile(@RequestParam("file") MultipartFile file) {
    fileService.uploadFile(file);
  }

  @GetMapping("/{key}")
  public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable("key") String key) {
    InputStream inputStream = fileService.downloadFile(key);
    return ResponseEntity.ok().body(outputStream -> FileCopyUtils.copy(inputStream, outputStream));
  }

  @GetMapping("/")
  public ResponseEntity<Object> tmp() {
    return ResponseEntity.ok("tmp");
  }

}
