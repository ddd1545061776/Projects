package com.ddd.controller;

import com.ddd.domain.Blog;
import com.ddd.domain.Type;
import com.ddd.service.BlogService;
import com.ddd.service.TypeService;
import com.ddd.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public  String types(@PathVariable("id") Long id, @PageableDefault(size = 8,sort = {"updatetime"}
            ,direction = Sort.Direction.DESC) Pageable pageable ,
                         Model model){
        List<Type> types =  typeService.listTypeTop(1000);
        if (id == -1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        Page<Blog> blogs = blogService.listBlog(pageable, blogQuery);
        model.addAttribute("types",types);
        model.addAttribute("page",blogs);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
