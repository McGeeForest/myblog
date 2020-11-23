package com.foresttech.myblog.Platform.Repository;

import com.foresttech.myblog.Platform.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article, Long> {

}
