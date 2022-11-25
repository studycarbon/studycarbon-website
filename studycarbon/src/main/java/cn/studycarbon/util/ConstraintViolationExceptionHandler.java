package cn.studycarbon.util;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

// 持久化bean验证
public class ConstraintViolationExceptionHandler {
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }

        String messages = StringUtils.join(msgList.toString(),";");
        return messages;
    }
}
