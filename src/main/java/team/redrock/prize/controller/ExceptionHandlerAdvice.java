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
         String msg =  e.getMessage();
         if(msg.equals("服务异常")){
             return new ErrorResponse(515, e.getMessage());
         }
         if(msg.equals("token验证无效")){
             return new ErrorResponse(-1,e.getMessage());
         }
         if(msg.equals("获取AccessToken失败")){
             return new ErrorResponse(-1,e.getMessage());
         }
       return  new ErrorResponse(415,e.getMessage());
    }
}
