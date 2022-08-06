package jialiang_ding.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Slf4j
public class WebMvcConfig  extends WebMvcConfigurationSupport {
    //路径比较 工具类 类似


    /**
     * 设置静态资源映射
     不做这个的话  默认是访问static 或者templeate下的静态资源文件
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始资源映射");
        //classpath 对应的就是recouse这个目录  当访问链接中带 /backend/ 的路径名称时 就访问到 classpath:/backend/
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");





    }
}
