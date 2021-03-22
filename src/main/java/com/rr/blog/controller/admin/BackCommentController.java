package com.rr.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Comment;
import com.rr.blog.entity.User;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.CommentService;
import com.rr.blog.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/Admin/Comment")
public class BackCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "")
    public ModelAndView commentListView(@RequestParam(value = "pageIndex",defaultValue = "1",required = false)Integer pageIndex,
                                        @RequestParam(value = "pageSize",defaultValue = "10",required = false)Integer pageSize){
        ModelAndView modelAndView =new ModelAndView("Admin/Comment/index");
        PageInfo<Comment> commentPageInfo =commentService.listCommentPage(pageIndex,pageSize);
        modelAndView.addObject("pageInfo",commentPageInfo);
        modelAndView.addObject("pageUrlPrefix","Admin/Comment/index?pageSize"+pageIndex);
        return modelAndView;
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insertComment(HttpServletRequest request,Comment comment){
        User user =(User) request.getSession().getAttribute("user");
         comment.setCommentIp(MyUtils.getIpAddr(request));
         comment.setCommentCreateTime(new Date());
         commentService.updateCommnet(comment);
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(),comment.getCommentArticleId());
        article.setArticleCommentCount(article.getA);


    }
}
