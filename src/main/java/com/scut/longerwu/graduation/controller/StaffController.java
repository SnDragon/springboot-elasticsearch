package com.scut.longerwu.graduation.controller;

import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import javax.servlet.http.*;
import java.util.*;

@RestController
@RequestMapping(value = "/api/staffs")
public class StaffController {

    @Resource
    private StaffService staffService;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public Result doLogin(@RequestParam(value = "account")String account,
                          @RequestParam(value = "password")String password,
                          HttpSession session){
        Staff staff=staffService.doLogin(account,password);
        if(staff!=null){
            session.setAttribute("staff",staff);
            return new Result(ResultEnum.SUCCESS,staff);
        }else{
            return new Result(ResultEnum.LOGIN_FAIL);
        }
    }

    @RequestMapping(value = "/getLoginInfo")
    public Result isLogin(HttpSession session){
        Map<String,Object> resultMap=new HashMap<>();
        Staff staff= (Staff) session.getAttribute("staff");
        resultMap.put("name",staff==null?"":staff.getName());
        return new Result(ResultEnum.SUCCESS,resultMap);
    }

    @RequestMapping(value = "/logout")
    public Result logout(HttpSession session){
        session.setAttribute("staff",null);
        return new Result(ResultEnum.SUCCESS);
    }


}
