package team.redrock.prize.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.prize.exception.ValidException;
import team.redrock.prize.pojo.response.UserResponse;
import team.redrock.prize.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public UserResponse userLogin(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String password, HttpServletRequest request, HttpServletResponse response) throws ValidException {


        UserResponse userResponse = userService.Login(username,password,request,response);
        int status = userResponse.getStatus();
        switch(status){
            case -1:
                throw new ValidException("Username checks invalid");
            case  -2:
                throw new ValidException("Password cannot be null");
            case  -3:
                throw new ValidException("Username doesn't exist");
            case  -4:
                throw new ValidException("Password checks invalid");
            default:
                break;
        }
        return userResponse;

    }


}
