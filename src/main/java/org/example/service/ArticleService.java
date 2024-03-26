package org.example.service;

import org.example.container.Container;
import org.example.dao.ArticleDao;
import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService(){
        articleDao = org.example.container.Container.articleDao;
    }
    public List<Article> getForPrintArticles(String searchKeyword) {
        return articleDao.getForPrintArticles(searchKeyword);
    }
    public List<Article> getForPrintArticles() {
        return this.getForPrintArticles(null);
    }

    public Article getAritcleById(int id) {
        return articleDao.getAritcleById(id);
    }

    public void remove(Article foundArticle) {
        articleDao.remove(foundArticle);
    }

    public int getNewId() {
        return articleDao.getNewId();
    }

    public void write(Article article) {
        articleDao.add(article);
    }
}
