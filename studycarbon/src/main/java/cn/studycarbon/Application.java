package cn.studycarbon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    // 日志简单使用，后续优化
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        logger.debug("===================================main======================================");
        SpringApplication.run(Application.class, args);
    }

}
