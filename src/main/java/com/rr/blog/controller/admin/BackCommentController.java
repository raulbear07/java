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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
         commentService.updateComment(comment);
        Article article = articleService.getArticleByStatusAndId(null,comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());
    }
    /**
     * 删除评论
     *
     * @param id 批量ID
     */
    @RequestMapping(value = "/delete/{id}")
    public void deleteComment(@PathVariable("id") Integer id) {
        Comment comment = commentService.getCommentById(id);
        //删除评论
        commentService.deleteComment(id);
        //删除其子评论
        List<Comment> childCommentList = commentService.listChildComment(id);
        for (int i = 0; i < childCommentList.size(); i++) {
            commentService.deleteComment(childCommentList.get(i).getCommentId());
        }
        //更新文章的评论数
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());
    }
    @RequestMapping("/edit/{id}")
    public String editCommentView(@PathVariable("id") Integer id,
                                 @RequestParam(value = "pageIndex",required = false,defaultValue = "1")Integer pageIndex
                                 , Model model){
        Comment comment =commentService.getCommentById(id);
        model.addAttribute("comment",comment);
        return "Admin/Comment/edit"+pageIndex;

    }
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public String editCommentSubmit(Comment comment,  @RequestParam(value = "pageIndex",required = false,defaultValue = "1")Integer pageIndex,Model model){
        commentService.updateComment(comment);
        return "redirect:/admin/comment?pageIndex"+pageIndex;
    }
    /**
     * 回复评论页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/reply/{id}")
    public String replyCommentView(@PathVariable("id") Integer id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "Admin/Comment/reply";
    }
    /**
     * 回复评论提交
     *
     * @param request
     * @param comment
     * @return
     */
    @RequestMapping(value = "/replySubmit", method = RequestMethod.POST)
    public String replyCommentSubmit(HttpServletRequest request, Comment comment) {
        //文章评论数+1
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);
        articleService.updateArticleDetail(article);
        //添加评论
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(MyUtils.getIpAddr(request));
        commentService.insertComment(comment);
        return "redirect:/admin/comment";
    }



}
