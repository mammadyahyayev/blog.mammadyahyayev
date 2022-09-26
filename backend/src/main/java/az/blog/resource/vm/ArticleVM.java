package az.blog.resource.vm;

import az.blog.domain.entity.Article;
import az.blog.domain.enumeration.ArticleStatus;

import java.time.LocalDate;

public class ArticleVM {
    private Long id;
    private String title;
    private LocalDate publishedDate;
    private String url;
    private ArticleStatus status;
    private String author;

    public ArticleVM(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.publishedDate = article.getPublishedDate();
        this.url = article.getUrl();
        this.status = article.getStatus();
        this.author = article.getAuthorFullName();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getUrl() {
        return url;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public String getAuthor() {
        return author;
    }
}
