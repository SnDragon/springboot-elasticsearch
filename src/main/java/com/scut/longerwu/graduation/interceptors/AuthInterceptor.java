package com.scut.longerwu.graduation.interceptors;

import com.alibaba.fastjson.*;
import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.vo.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;
import java.io.*;

/**
 * 授权拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session=request.getSession();
        if(session.getAttribute("staff")==null){
            Result result=new Result(ResultEnum.UNAUTHORIZED);
            response.setHeader("Content-type","application/json;charset=UTF-8");
            OutputStream stream=response.getOutputStream();
            stream.write(JSON.toJSONBytes(result));
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
