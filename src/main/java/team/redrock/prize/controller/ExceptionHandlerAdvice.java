package team.redrock.prize.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.ErrorResponse;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ValidException.class)
    public ErrorResponse handleException(ValidException e) {
        return new ErrorResponse("-1", e.getMessage());
    }
}
