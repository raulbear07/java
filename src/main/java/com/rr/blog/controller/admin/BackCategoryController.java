package com.rr.blog.controller.admin;

import com.rr.blog.entity.Category;
import com.rr.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class BackCategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public ModelAndView categoryList(){
        List<Category> categoryList =categoryService.listCategory();
        ModelAndView modelAndView =new ModelAndView("Admin/Category/index");
        modelAndView.addObject("categoryList",categoryList);
        return  modelAndView;
    }
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertCategorySubmit(Category category){
        categoryService.insertCategory(category);
        return "redirect:/admin/category";
    }
    @RequestMapping(value ="/delete/{id}")
    public String deleteCategory(@PathVariable("id")Integer id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView editCategoryView(@PathVariable("id")Integer id){
        ModelAndView modelAndView =new ModelAndView("Admin/Category/edit");
        Category category =categoryService.getCategoryById(id);
        modelAndView.addObject("category",category);
        List<Category> categoryList =categoryService.listCategory();
        modelAndView.addObject("categoryList",categoryList);
        return modelAndView;
    }
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editCategorySubmit(Category category)  {
        categoryService.updateCategory(category);
        return "redirect:/admin/category";
    }

}
