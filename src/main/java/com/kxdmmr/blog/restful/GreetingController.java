package com.kxdmmr.blog.restful;

import com.kxdmmr.blog.dao.ArticleDao;
import com.kxdmmr.blog.pojo.Article;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 负责GET类型的API请求，需验证请求头：x-requested-by:sycki
 * Created by kxdmmr on 2017/9/9.
 */
@RestController
public class GreetingController {
    @Resource
    ArticleDao articleDao;

    /**
     * 根据文章名返回文章json串
     * @param articleName
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/api/articles/{articleName}")
    public Article greeting(@PathVariable("articleName") String articleName, HttpServletRequest request, HttpServletResponse response) {
        if(! "sycki".equals(request.getHeader("x-requested-by"))){
            response.setStatus(403);
            return null;
        }
        // TODO 返回的json串没有被序列化，应该在spring中配置一个序列化器
        Article article = this.articleDao.selectArticleByName(articleName);
        return article;
    }

}
