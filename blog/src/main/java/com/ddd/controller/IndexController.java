package com.ddd.controller;

import com.ddd.domain.Type;
import com.ddd.service.BlogService;
import com.ddd.service.TagService;
import com.ddd.service.TypeService;
import com.ddd.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by limi on 2017/10/13.
 */
@Controller
public class IndexController {
@Autowired
    BlogService blogService;
@Autowired
    TypeService typeService;
@Autowired
    TagService tagService;
    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updatetime"}
            ,direction = Sort.Direction.DESC) Pageable pageable ,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendTop(8));
        return "index";
    }
    @PostMapping("search")
  public  String search(@PageableDefault(size = 8,sort = {"updatetime"}
          ,direction = Sort.Direction.DESC) Pageable pageable ,
                        Model model,String query){
       model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
  }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
       model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
    @GetMapping("/foot/newblog")
    public  String about(Model model){
model.addAttribute("newblogs",blogService.listRecommendTop(3));
        return "_fragments::newblogList";
    }

}
