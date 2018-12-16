//package team.redrock.prize.interceptor;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import team.redrock.prize.exception.MyException;
//import team.redrock.prize.pojo.response.GlobalErrorResponse;
//
//import java.util.Objects;
//
//@ControllerAdvice
//public class MyExceptionHandler {
//
//    @ExceptionHandler(value = MyException.class)
//    public GlobalErrorResponse handleServiceException(MyException exception) {
//
//        return new GlobalErrorResponse(exception.getCode(),exception.getMsg());
//    }
//            //其他异常
//    @ExceptionHandler
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public GlobalErrorResponse hadleServerException(Exception exception) {
//        exception.printStackTrace();
//        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        String msg = "server error, please try again later";
//        Class exceptionClazz = exception.getClass();
//        if (Objects.equals(MissingServletRequestParameterException.class, exceptionClazz)) {
//            msg = "incorrect parameter";
//            httpStatus = HttpStatus.BAD_REQUEST;
//        } else if (Objects.equals(HttpRequestMethodNotSupportedException.class, exceptionClazz)) {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            msg = exception.getMessage();
//        }
//        return new GlobalErrorResponse(httpStatus.value(),msg);
//    }
//}
