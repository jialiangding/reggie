package jialiang_ding.reggie.controller;

import jialiang_ding.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private  String basePath;


    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());
        File directory = new File(basePath);
        String originalFilename = file.getOriginalFilename();
        String reportPath = directory.getCanonicalPath()+ "/upload/image/";
        log.info(reportPath);
        File folder = new File(reportPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file.transferTo(new File(folder, originalFilename));

        return  null;
    }
}
