package com.asuna.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

    //在调用所有处理请求的方法之前被自动调用执行的方法

    /**
     * 检测全局session对象中是否含有Uid数据,如果有则放行，没有则重定向回登录界面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url + controller映射)
     * @return true则放行，false则拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //HttpServletRequest对象来获取全局的session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null){
            // 说明用户没有登录过界面，重定向回登录界面
            response.sendRedirect("/web/login.html");
            // 结束后续的调用
            return false;
        }
        // 请求放行
        return true;
    }

    //在ModelAndView对象返回之后被调用的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //在视图被渲染完毕之后被调用的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
