package com.wxf.config;

import com.wxf.domain.User;
import javafx.application.Application;
import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
public class WebFilter {
    Logger log = LoggerFactory.getLogger(Application.class);
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/notebook/*");
        registration.addUrlPatterns("/note/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
    public class MyFilter implements Filter {
        @Override
        public void destroy() {
        }

        private String login="/log_in.html";
        @Override

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            //从请求重获取 请求的url

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session=req.getSession();
            String path = req.getRequestURI();

            if(path.endsWith(login)){
                chain.doFilter(request, response);
                return;
            }
            if(path.endsWith("alert_error/html")){
                chain.doFilter(request, response);
                return;
            }
            //检查用户是否登录
            User user = (User) session.getAttribute("loginUser");
            if(user==null){
                //用绝对路径重定向，安全
                res.sendRedirect(req.getContextPath()+login);
                return;
            }
            chain.doFilter(request, response);//这个方法的意义是调用后续的请求
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {

        }
    }

}
