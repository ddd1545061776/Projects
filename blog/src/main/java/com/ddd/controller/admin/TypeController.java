package com.ddd.controller.admin;

import com.ddd.domain.Type;
import com.ddd.service.TypeService;
import jdk.nashorn.internal.runtime.logging.Logger;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {
     @Autowired
    TypeService typeService;
     @GetMapping("types")
    public  String  types(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 2) Pageable pageable
     ,Model model) {
         Page<Type> typePage = typeService.listPage(pageable);
         model.addAttribute("page", typePage);
         return "admin/types";
     }
     @GetMapping("types/input")
   public  String input(Model model){
         model.addAttribute("type",new Type());
         return "admin/types-input";
     }
    @PostMapping("types")
    public  String post(@Valid Type type, BindingResult result,
                        RedirectAttributes redirectAttributes,
                         Model model){
        Type byName = typeService.findByName(type.getName());
        if (byName !=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
          return "admin/types-input";
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
         Type type1 = typeService.saveType(type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else{
            redirectAttributes.addFlashAttribute("message","新增成功");
        }

        return "redirect:/admin/types";
    }
    @GetMapping("types/{id}/input")
   public  String   editInput(@PathVariable("id") Long id , Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
   }
    @PostMapping("types/{id}")
    public  String editpost(@Valid Type type, BindingResult result,
                        @PathVariable("id") Long id,
                        RedirectAttributes redirectAttributes,
                        Model model){
        Type byName = typeService.findByName(type.getName());
        if (byName !=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
            return "admin/types-input";
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id,type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功");
        }

        return "redirect:/admin/types";
    }
    @GetMapping("types/{id}/delete")
    public  String  delete(@PathVariable("id") Long id , Model model,RedirectAttributes redirectAttributes){
       typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
