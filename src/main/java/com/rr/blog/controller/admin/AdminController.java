package com.rr.blog.controller.admin;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Comment;
import com.rr.blog.entity.User;
import com.rr.blog.enums.LimitCount;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.CommentService;
import com.rr.blog.service.UserService;
import com.rr.blog.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;
    @RequestMapping("/admin")
    public String index(Model model){
        List<Article> articleList=articleService.listRecentArticle(LimitCount.COUNT_LOW.getValue());
        model.addAttribute("articleList",articleList);
        List<Comment> commentList=commentService.listRecentComment(LimitCount.COUNT_LOW.getValue());
        model.addAttribute("commentList",commentList);
        return  "/Admin/index";
    }
    @RequestMapping("/login")
    public  String loginPage(){
        return "Admin/login";
    }
    @ResponseBody
    @RequestMapping(value = "/loginVerify",method= RequestMethod.POST,produces ="text/plain;charset=UTF-8" )
    public String loginVerify(HttpServletRequest request , HttpServletResponse response){
        HashMap<String ,Object> map=new HashMap<String, Object>();
        String userName =request.getParameter("username");
        String password=request.getParameter("password");
        String remeberme=request.getParameter("remeberme");
        User user=userService.getUserByName(userName);
        if(user==null){
            map.put("code",0);
            map.put("msg", "用户名无效！");
        }
        else if(!user.getUserPass().equals(password)){
            map.put("code",0);
            map.put("msg", "密码错误！");
        }
        else{
            //登录成功
            map.put("code", 1);
            map.put("msg", "");
            request.getSession().setAttribute("user", user);
            if(remeberme!=null){
                Cookie nameCookie=new Cookie("username",userName);
                nameCookie.setMaxAge(60*60*24*3);
                Cookie pwdCookie = new Cookie("password", password);
                pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
            }
        }
        user.setUserLastLoginIp(MyUtils.getIpAddr(request));
        user.setUserLastLoginTime(new Date());
        userService.updateUser(user);
        String result = new JSONObject(map).toString();
        return result;
    }
}
