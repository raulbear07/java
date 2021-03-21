package com.rr.blog.controller.home;

import com.rr.blog.entity.Article;
import com.rr.blog.entity.Notice;
import com.rr.blog.service.ArticleService;
import com.rr.blog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/notice/{noticeId}")
    public String NoticeDetailView(@PathVariable("noticeId")Integer noticeId, Model model){
        Notice notice =noticeService.getNoticeById(noticeId);
        model.addAttribute("notice",notice);
        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/noticeDetail";
    }
}
