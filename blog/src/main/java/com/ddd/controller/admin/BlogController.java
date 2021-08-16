package com.ddd.controller.admin;

import com.ddd.domain.Blog;
import com.ddd.domain.User;
import com.ddd.service.BlogService;
import com.ddd.service.TagService;
import com.ddd.service.TypeService;
import com.ddd.vo.BlogQuery;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private  static  final   String INPUT="admin/blogs-input";
    private  static  final   String LIST="admin/blogs";
    private  static  final   String REDIRECT_LIST="redirect:/admin/blogs";
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @GetMapping("/blogs")
   public  String  blogs(@PageableDefault(size = 2,sort = {"updatetime"}
   ,direction = Sort.Direction.DESC) Pageable pageable , BlogQuery blog,
                         Model model){
      model.addAttribute("types" , typeService.listType()) ;
        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("page",blogs);
        return  LIST;
}
    @PostMapping("/blogs/search")
    public  String  search(@PageableDefault(size = 2,sort = {"updatetime"}
            ,direction = Sort.Direction.DESC) Pageable pageable , BlogQuery blog,
                          Model model){
        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("page",blogs);
        return  "admin/blogs::blogList";
    }
    @GetMapping("blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blogs", new Blog());
        return INPUT;
    }
    @PostMapping("blogs")
    public  String post(Blog blog, HttpSession session
                       , RedirectAttributes redirectAttributes){
      blog.setUser((User) session.getAttribute("user"));
      blog.setType(typeService.getType(blog.getType().getId()));
      blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog blog1 = blogService.saveBlog(blog);
        if (blog1 == null) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }

        return REDIRECT_LIST;
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("types" , typeService.listType()) ;
        model.addAttribute("tags" , tagService.listTag()) ;
    }
    @GetMapping("blogs/{id}/input")
    public String editInput(Model model,@PathVariable("id") Long id) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blogs",blog);
    return INPUT;
    }
    @GetMapping("blogs/{id}/delete")
    public String delete (@PathVariable("id") Long id ,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功"   );
        return REDIRECT_LIST;
    }
}
