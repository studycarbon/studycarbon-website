package cn.studycarbon;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(FdfsClientConfig.class)
@SpringBootApplication
public class Application {

    // 日志简单使用，后续优化
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    // 主函数
    public static void main(String[] args) {
        logger.info("main start =>");
        SpringApplication.run(Application.class, args);
    }
}
