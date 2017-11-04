package com.kxdmmr.blog.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kxdmmr.blog.dao.ArticleDao;

import javax.annotation.Resource;

@Controller
public class ArticleController {
	Logger LOG = Logger.getLogger(ArticleController.class);
	@Value("${ui_desc_page_limit}") int ui_desc_page_limit;

	@Resource
	ArticleDao articleDao;

	@GetMapping("/")
	String index(Model model){
		return "index";
	}


}
