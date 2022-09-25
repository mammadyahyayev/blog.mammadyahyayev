package az.blog.resource;

import az.blog.domain.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/v1")
public class ArticleResource {

    @GetMapping(value = "/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return null;
    }

    @PostMapping(value = "/article")
    public ResponseEntity<Void> createArticle() {
        return null;
    }

    @PutMapping(value = "/article")
    public ResponseEntity<Void> updateArticle() {
        return null;
    }

    @DeleteMapping(value = "/article")
    public ResponseEntity<Void> deleteArticle() {
        return null;
    }

}
