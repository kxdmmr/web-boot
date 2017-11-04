package com.kxdmmr.blog.security;

import com.kxdmmr.blog.config.BlogUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kxdmmr on 2017/8/23.
 * 取出每次请求中的客户端IP，然后交由黑名单管理器决定是否放行该IP
 */
public class AttackInterceptor implements HandlerInterceptor {
    Logger LOG = Logger.getLogger(AttackInterceptor.class);
    BlackList blackList = BlackList.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String clientIP = BlogUtils.getClientIP(request);
        String uri = request.getRequestURI();
        if(! blackList.gateway(clientIP)) {
            LOG.info(String.format("Refuse [%s] visit URL [%s]", clientIP, uri));
            return false;
        }
        LOG.info(String.format("Allow [%s] visit URL [%s]", clientIP, uri));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }



}
