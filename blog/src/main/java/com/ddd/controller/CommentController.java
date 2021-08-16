package com.ddd.controller;

import com.ddd.domain.Comment;
import com.ddd.domain.User;
import com.ddd.service.BlogService;
import com.ddd.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    @Value("${comment.avatar}")
    private String avatar;
    @GetMapping("comments/{blogId}")
    public  String comment(@PathVariable("blogId")Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }
    @PostMapping("/comments")
    public  String post(Comment comment, HttpSession session){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        User user = (User) session.getAttribute("user");
        if (user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else{
            comment.setAvatar(avatar);

        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
