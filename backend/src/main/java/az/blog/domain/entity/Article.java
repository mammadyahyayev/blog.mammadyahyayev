package az.blog.domain.entity;

import az.blog.domain.enumeration.ArticleStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate publishedDate;
    private String url;

    @Enumerated
    private ArticleStatus status;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}
