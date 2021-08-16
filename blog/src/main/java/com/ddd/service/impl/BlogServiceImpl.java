package com.ddd.service.impl;

import com.ddd.NotFoundException;
import com.ddd.dao.BlogDao;
import com.ddd.domain.Blog;
import com.ddd.domain.Type;
import com.ddd.service.BlogService;
import com.ddd.util.MarkdownUtils;
import com.ddd.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
  private BlogDao blogDao;
    @Override
    @Transactional
    public Blog getBlog(Long id) {
        return blogDao.findOne(id);
    }
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(blog.getTitle())) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (!StringUtils.isEmpty(blog.getTypeId())) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
//                cq.where(predicates.toArray(new Predicate[predicates.size()]));

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);

    }
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null) {
            blog.setCreatetime(new Date());
            blog.setUpdatetime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdatetime(new Date());
        }
        return blogDao.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogDao.findOne(id);
        if(b==null){
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog,b);
        return  blogDao.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.delete(id);
    }

    @Override
    public List<Blog> listRecommendTop(Integer size) {
        Pageable pageable=new PageRequest(0,size,new Sort(Sort.Direction.DESC,"updatetime"));
        return blogDao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query,pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.findOne(id);
        if(blog==null){
            throw  new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content =b.getContent();
        MarkdownUtils.markdownToHtmlExtensions(content);
        b.setContent(content);
          blogDao.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                    Join join=root.join("tags");//关联到tag表
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogDao.findGroupYear();
        Map<String, List<Blog>> map=new HashMap<>();
        for(String year: years){
            map.put(year,blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }
}
