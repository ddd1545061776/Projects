package com.ddd.service;

import com.ddd.domain.Blog;
import com.ddd.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    /**
     * 根据id查询博客
    * @param: id
    */
    Blog getBlog(Long id);

    /**
     * 根据条件分页查询博客
    * @param: pageable
     * @param: blog
    */
    Page<Blog> listBlog (Pageable pageable, BlogQuery blog);
    /**
     * 查询博客返回前端显示
    * @param: pageable
    */
    Page<Blog> listBlog (Pageable pageable);
    /**
     * 新增
    * @param: blog
    */
    Blog saveBlog(Blog blog);
    /**
     * 更新博客
    * @param: id
     * @param: blog0
    */
    Blog updateBlog(Long id,Blog blog);
    /**
     * 删除博客
    * @param: id
    */
    void deleteBlog(Long id);
    /**
     * 根据size大小查询多少条推荐博客列表
    * @param: size
    */
    List<Blog> listRecommendTop(Integer size);
    /**
     * 全文查询
    * @param: query
     * @param: pageable
    */
    Page<Blog> listBlog (String query,Pageable pageable);
    /**
     * 根据id查询博客详情并且转化为markdown格式
    * @param: id
    */
    Blog  getAndConvert(Long id);
    /**
     * 根据tagId查询相对应的博客
    * @param: tagId
     * @param: pageable
    */
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    /**
     * 根据年份对博客归档
    * @param:
    */
    Map<String,List<Blog>> archiveBlog();
    /**
     * 查询博客总条数
    * @param:
    */
    Long countBlog();

}
