package cn.studycarbon;

import cn.studycarbon.controller.HelloStudycarbonController;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests {
    public static Logger logger = LoggerFactory.getLogger(HelloStudycarbonController.class);
    @Test
    void appTests() {
        logger.info("appTests...");
    }
}
