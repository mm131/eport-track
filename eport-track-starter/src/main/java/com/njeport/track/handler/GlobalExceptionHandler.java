package com.njeport.track.handler;

import com.njeport.track.common.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author limingming
 * @date 2019/12/31
 */
@ControllerAdvice("com.njeport.track")
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void unhandledExceptionHandler(HttpServletResponse response, Exception ex) throws Exception {
        log.error("捕捉到异常: {}", ex);
        ResponseUtils.setHeader(response);
        response.setStatus(HttpStatus.SC_BAD_REQUEST);
        response.getWriter().print("{\"success\": false, \"code\":\"" + HttpStatus.SC_BAD_REQUEST + "\", \"message\":\"" + ex.getMessage()
                + "\", \"result\":\"\"}");
        response.getWriter().flush();
    }
}
