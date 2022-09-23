package az.blog.domain.dto;

import az.blog.domain.entity.Author;
import az.blog.domain.enumeration.ArticleStatus;

import java.time.LocalDate;

public class ArticleDTO {
    private Long id;
    private String title;
    private LocalDate publishedDate;
    private String url;
    private ArticleStatus status;
    private Author author;
}
