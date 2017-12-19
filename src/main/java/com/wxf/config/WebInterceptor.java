package com.wxf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxf.controller.JsonResult;
import com.wxf.domain.User;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by TYZ027 on 2017/11/24.
 */
@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {
    Logger log = LoggerFactory.getLogger(Application.class);
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/notebook/*").addPathPatterns("/note/*").addPathPatterns("/user/exit.do");
        super.addInterceptors(registry);
    }
    public class MyInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
            String path=req.getRequestURI();
            //如果没有登录就返回错误的json消息
            //如果登录了就放过请求
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("loginUser");
            if(user==null){
                JsonResult result = new JsonResult("需要重新登录！");
                //利用response对象反馈结果
                res.setContentType("application/json;charset=utf-8");
                res.setCharacterEncoding("utf-8");
                //
                ObjectMapper mapper = new ObjectMapper();
                String json =mapper.writeValueAsString(result);
                res.getWriter().println(json);
                res.flushBuffer();
                //  // --//之间的代码就是responseBody的代码
                return false;
            }
            return true;//放过请求
        }
        @Override
        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        }
        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        }
    }
}
