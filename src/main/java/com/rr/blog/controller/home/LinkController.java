package com.rr.blog.controller.home;

import com.rr.blog.dto.JsonResult;
import com.rr.blog.entity.Article;
import com.rr.blog.entity.Link;
import com.rr.blog.enums.LimitCount;
import com.rr.blog.enums.LinkStatus;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
public class LinkController {
    @Autowired
    private LinkService linkService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping("/applyLink")
    public String applyLinkView(Model model) {
        List<Article> mostCommentArticleList= articleService.listArticleByCommentCount(LimitCount.COUNT_HIGH.getValue());
        model.addAttribute("mostCommentArticleList",mostCommentArticleList);
        return "Home/page/applyLine";
    }
    @RequestMapping(name = "/applyLinkSubmit",method = RequestMethod.POST,produces = "text/plain;charset=UTF-8")
    public void applySubmitLink(Link link){
        link.setLinkUpdateTime(new Date());
        link.setLinkCreateTime(new Date());
        link.setLinkStatus(LinkStatus.HIDDEN.getValue());
         linkService.insertLink(link);
    }

}
