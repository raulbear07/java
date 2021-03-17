package com.rr.blog.controller.home;

import cn.hutool.http.HtmlUtil;
import com.rr.blog.dto.JsonResult;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Comment;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.enums.Role;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.CommentService;
import com.rr.blog.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public JsonResult insertComment(HttpServletRequest requset, Comment comment){
        if(requset.getSession().getAttribute("user")!=null){
            comment.setCommentRole(Role.ADMIN.getValue());
        }
        else
            comment.setCommentRole(Role.VISITOR.getValue());
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(MyUtils.getIpAddr(requset));
        comment.setCommentAuthorAvatar(MyUtils.getGravatar(comment.getCommentAuthorEmail()));
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(HtmlUtil.escape(comment.getCommentAuthorName()));
        comment.setCommentAuthorEmail(HtmlUtil.escape(comment.getCommentAuthorEmail()));
        comment.setCommentAuthorUrl(HtmlUtil.escape(comment.getCommentAuthorUrl()));
        try{
            commentService.insertComment(comment);
            //更新文章的评论数
            Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
            articleService.updateCommentCount(article.getArticleId());
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  new JsonResult().fail(ex.getMessage());
        }
        return new JsonResult().ok();
    }

}
