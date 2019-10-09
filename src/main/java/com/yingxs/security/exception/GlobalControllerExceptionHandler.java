package com.yingxs.security.exception;

import com.yingxs.security.support.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器
 * @author yingxs
 * @date 2019-10-18 10:50:42
 * @email ying_xs@163.com
 */
@RestControllerAdvice
//@Slf4j
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleResponse constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            Path propertyPath = cvl.getPropertyPath();
//            msgList.add(propertyPath.toString()+","+cvl.getMessageTemplate());
            msgList.add(cvl.getMessageTemplate());
        }
        String msg = constraintViolations.iterator().next().getMessageTemplate();
        return new SimpleResponse(msg);
    }


    @ExceptionHandler(value = ClassCastException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponse handlerClassCastException(ClassCastException e) {
        e.printStackTrace();
        return new SimpleResponse(e.getMessage());
    }



    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponse handlerRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return new SimpleResponse(e.getMessage());
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponse constraintException(Exception e) {
        e.printStackTrace();
        return new SimpleResponse(e.getMessage());
    }


}
