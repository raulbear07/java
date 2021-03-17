package com.rr.blog.controller.home;

import com.github.pagehelper.PageInfo;
import com.rr.blog.entity.*;
import com.rr.blog.enums.ArticleStatus;
import com.rr.blog.enums.LinkStatus;
import com.rr.blog.enums.NoticeStatus;
import com.rr.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = {"/","/article"})
    public String index(@RequestParam("pageIndex")Integer pageIndex, @RequestParam("pageSize")Integer pageSize,
                        Model model){
        HashMap<String ,Object> criteria =new HashMap<>(1);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articleList =articleService.pageArticle(pageIndex,pageSize,criteria);
        model.addAttribute("pageInfo", articleList);

        List<Notice> noticeList =noticeService.listNotice(NoticeStatus.NORMAL.getValue());
        model.addAttribute("noticeList",noticeList);

        //友情链接
        List<Link> linkList = linkService.listLink(LinkStatus.NORMAL.getValue());
        model.addAttribute("linkList", linkList);

        //侧边栏显示
        //标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        //最新评论
        List<Comment> recentCommentList = commentService.listRecentComment(10);
        model.addAttribute("recentCommentList", recentCommentList);
        model.addAttribute("pageUrlPrefix", "/article?pageIndex");
        return "Home/index";
    }

    public
}
