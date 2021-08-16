package com.ddd.controller.admin;

import com.ddd.domain.Tag;
import com.ddd.domain.Type;
import com.ddd.service.TagService;
import com.ddd.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class TagController {
    @Autowired
    TagService tagService;
    @GetMapping("tags")
    public  String  Tags(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 2) Pageable pageable
            , Model model) {
        Page<Tag> TagPage = tagService.listPage(pageable);
        model.addAttribute("page", TagPage);
        return "admin/tags";
    }
    @GetMapping("tags/input")
    public  String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @PostMapping("tags")
    public  String post(@Valid Tag tag, BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        Tag byName = tagService.findByName(tag.getName());
        if (byName != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
            return "admin/tags-input";
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }

        return "redirect:/admin/tags";
    }
    @GetMapping("tags/{id}/input")
    public  String   editInput(@PathVariable("id") Long id , Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }
    @PostMapping("tags/{id}")
    public  String editpost(@Valid Tag tag, BindingResult result,
                            @PathVariable("id") Long id,
                            RedirectAttributes redirectAttributes,
                            Model model){
        Tag byName = tagService.findByName(tag.getName());
        if (byName !=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
            return "admin/tags-input";
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.updateTag(id,tag);
        if(tag1==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功");
        }

        return "redirect:/admin/tags";
    }
    @GetMapping("tags/{id}/delete")
    public  String  delete(@PathVariable("id") Long id , Model model,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

}
