package com.foresttech.myblog.Platform.Api;

import com.foresttech.myblog.Platform.Model.Article;
import com.foresttech.myblog.Platform.Repository.ArticleRepo;
import com.foresttech.myblog.Platform.Service.ArticleService;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article")
public class ArticleApi {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("add")
    public Message addArticle(){
        Article article = new Article();
        article.setContent("文章内容");
        article.setDate("2020-11-18 11:18:00");
        article.setUser("McGeeForest");

        articleService.addArticle(article);
        Message message = new Message();
        message.setResHead(ResponseHead.SUCCESS);
        message.setResData("添加成功");
        return message;
    }

}
