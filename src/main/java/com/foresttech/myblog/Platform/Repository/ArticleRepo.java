package com.foresttech.myblog.Platform.Repository;

import com.foresttech.myblog.Platform.Model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Articles, Long> {

}
