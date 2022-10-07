package az.blog.resource;

import az.blog.domain.dto.ArticleCreationDTO;
import az.blog.domain.dto.ArticleModificationDTO;
import az.blog.domain.entity.Article;
import az.blog.repository.ArticleRepository;
import az.blog.resource.errors.BadRequestException;
import az.blog.resource.errors.Operation;
import az.blog.resource.vm.ArticleVM;
import az.blog.resource.vm.OperationResult;
import az.blog.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ArticleResource {

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/articles")
    public ResponseEntity<List<ArticleVM>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleVM> articleVMS = new ArrayList<>();
        articles.forEach(article -> {
            ArticleVM articleVM = new ArticleVM(article);
            articleVMS.add(articleVM);
        });
        return new ResponseEntity<>(articleVMS, HttpStatus.OK);
    }

    @PostMapping(value = "/articles")
    public ResponseEntity<OperationResult> createArticle(@Valid @RequestBody ArticleCreationDTO articleDTO) {
        boolean isInserted = articleService.insertArticle(articleDTO);
        if (!isInserted) {
            return new ResponseEntity<>(Operation.unknownError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Operation.createdSuccessfully("Article created"), HttpStatus.CREATED);
    }

    @PutMapping(value = "/articles/{id}")
    public ResponseEntity<OperationResult> updateArticle(@PathVariable("id") Long id, @RequestBody @Valid ArticleModificationDTO articleDTO) {
        if (id == null) {
            throw new BadRequestException("id", "Id cannot be null or less than a zero");
        }

        articleService.updateArticle(id, articleDTO);
        return new ResponseEntity<>(Operation.updatedSuccessfully("Article updated"), HttpStatus.OK);
    }

    @DeleteMapping(value = "/articles/{id}")
    public ResponseEntity<OperationResult> deleteArticle(@PathVariable Long id) {
        if(id == null) {
            throw new ValidationException("Id cannot be null");
        }

        articleService.deleteArticle(id);
        return new ResponseEntity<>(Operation.deletedSuccessfully("Article updated"), HttpStatus.OK);
    }

}
