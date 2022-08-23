package jialiang_ding.reggie.controller;

import jialiang_ding.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

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

        String filename= UUID.randomUUID().toString()+originalFilename;
        log.info(reportPath);
        File folder = new File(reportPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file.transferTo(new File(folder, filename));
        return  R.success(filename);
    }

    @GetMapping("/download")
    public  R<String> download(String name, HttpServletResponse response){
        //输入流 通过输入流 读取文件内容
        try {
            File directory = new File(basePath);
            String reportPath = directory.getCanonicalPath()+ "/upload/image/";
            FileInputStream fileInputStream=new FileInputStream(new File(reportPath+name));
            //输出流，通过输出流将文件写回浏览器 在浏览器展示图片
            ServletOutputStream outputStream=response.getOutputStream();
            response.setContentType("image/jpeg");
            int len=0;
            byte[] bytes=new  byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //输出流，通过输出流将文件写回浏览器 在浏览器展示图片
        return  null;
    }
}
