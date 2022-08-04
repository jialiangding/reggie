package jialiang_ding.reggie;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("jialiang_ding.reggie.mapper")
public class ReggieApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplicationTests.class, args);
        log.info("启动");

    }
}
