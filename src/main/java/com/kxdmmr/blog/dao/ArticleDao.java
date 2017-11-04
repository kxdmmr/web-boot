package com.kxdmmr.blog.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.*;

import com.kxdmmr.blog.pojo.Article;

@Mapper
public interface ArticleDao {
	
	@Select("select * from blog_articles where status='publish' order by create_date desc limit #{count}")
	public ArrayList<Article> selectArticles(@Param("count") int count);

	@Select("select * from blog_articles where id = #{id}")
	public Article selectArticleById(@Param("id") int id);

	@Select("select * from blog_articles where id = (select max(id) from blog_articles where status='publish' and en_name = #{articleName})")
	public Article selectArticleByName(@Param("articleName") String articleName);

	@Update("update blog_articles set status = #{status} where id = #{id}")
	public void updateArticleStatusById(@Param("status") String status, @Param("id") int id);

	@Update("update blog_articles set status = #{status} where en_name = #{en_name}")
	public void updateArticleStatusByName(@Param("status") String status, @Param("en_name") String en_name);

	@Insert("insert into blog_articles(id,parent_id,title,en_name,content,author,create_date," +
			"change_date,status,tags,like_count,unlike_count,viewer_count) values(#{id},#{parent_id}," +
			"#{title},#{en_name},#{content},#{author},#{create_date},#{change_date},#{status}," +
			"#{tags},#{like_count},#{unlike_count},#{viewer_count})")
	public void insertArticle(@Param("id") int id, @Param("parent_id") int parent_id, @Param("title") String title, @Param("en_name") String en_name, @Param("content") String content, @Param("author") int author, @Param("create_date") String create_date, @Param("change_date") String change_date, @Param("status") String status, @Param("tags") String tags, @Param("like_count") int like_count, @Param("unlike_count") int unlike_count, @Param("viewer_count") int viewer_count);

	@Select("select max(id) from ${tablesName}")
	public int getMaxIdByTable(@Param("tablesName") String tablesName);

	@Update("update blog_articles set viewer_count = viewer_count + 1 where id = #{id}")
	public void setViewerPlus(@Param("id") int id);

	@Update("update blog_articles set like_count = like_count + 1 where id = #{id}")
	public void setLikePlus(@Param("id") int id);

	@Update("update blog_articles set like_count = like_count - 1 where id = #{id}")
	public void setLikeMinus(@Param("id") int id);

	@Insert("insert into blog_user_action(client_id,resource,action) values(#{clientId},#{resource},#{action})")
	public void insertUserAction(@Param("clientId") String clientId, @Param("resource") String resource, @Param("action") String action);

	@Delete("delete from blog_user_action where client_id = #{clientId} and resource = #{resource} and action = #{action}")
	public void deleteUserAction(@Param("clientId") String id, @Param("resource") String resource, @Param("action") String action);

}
