package com.ddd.controller;

import com.ddd.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController  {
    @Autowired
    private BlogService blogService;
    @GetMapping("archives")
    public  String  archive(Model model){
model.addAttribute("archiveMap",blogService.archiveBlog());
model.addAttribute("blogCount",blogService.countBlog());
 return "archives";
    }
}
