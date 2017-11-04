package com.kxdmmr.blog.config;

import com.kxdmmr.blog.security.AttackInterceptor;
import com.kxdmmr.blog.security.DashboardInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kxdmmr on 2017/8/23.
 * Spring boot 中的WebMvcConfigurerAdapter封装了所有用到的可配置项
 */
@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new AttackInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new DashboardInterceptor()).addPathPatterns("/dashboard/**");
        super.addInterceptors(registry);
    }

}
