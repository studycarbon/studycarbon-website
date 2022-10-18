package cn.studycarbon.config;

import cn.studycarbon.controller.HelloStudycarbonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    public static Logger logger = LoggerFactory.getLogger(HelloStudycarbonController.class);

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("start static resources...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
