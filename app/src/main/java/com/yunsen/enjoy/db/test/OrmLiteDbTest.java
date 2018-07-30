package com.yunsen.enjoy.db.test;

import android.test.AndroidTestCase;

import com.j256.ormlite.dao.Dao;
import com.yunsen.enjoy.db.DatabaseHelper;
import com.yunsen.enjoy.db.dao.ArticleDao;
import com.yunsen.enjoy.db.dao.UserDao;
import com.yunsen.enjoy.db.entity.Article;
import com.yunsen.enjoy.db.entity.Student;
import com.yunsen.enjoy.db.entity.User;

import java.sql.SQLException;
import java.util.List;

public class OrmLiteDbTest extends AndroidTestCase {
    public void testAddArticle() {
        User u = new User();
        u.setName("xxx");
        new UserDao(getContext()).add(u);
        Article article = new Article();
        article.setTitle("ORMLite");
        article.setUser(u);
        new ArticleDao(getContext()).add(article);

    }

    public void testGetArticleById() {
        Article article = new ArticleDao(getContext()).get(1);
    }

    public void testGetArticleWithUser() {

        Article article = new ArticleDao(getContext()).getArticleWithUser(1);
//        L.e(article.getUser() + " , " + article.getTitle());
    }

    public void testListArticlesByUserId() {

        List<Article> articles = new ArticleDao(getContext()).listByUserId(1);
//        L.e(articles.toString());
    }

    public void testGetUserById() {
        User user = new UserDao(getContext()).get(1);
//        L.e(user.getName());
        if (user.getArticles() != null)
            for (Article article : user.getArticles()) {
//                L.e(article.toString());
            }
    }

    public void testAddStudent() throws SQLException {
        Dao dao = DatabaseHelper.getHelper(getContext()).getDao(Student.class);
        Student student = new Student();
        student.setDao(dao);
        student.setName("xxx");
        student.create();
    }


}
