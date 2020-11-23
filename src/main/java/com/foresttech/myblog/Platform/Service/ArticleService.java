package com.foresttech.myblog.Platform.Service;

import com.foresttech.myblog.Platform.Model.Article;
import com.foresttech.myblog.Platform.Repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    public void addArticle(Article article){
        articleRepo.save(article);
    }
}
