package az.blog.service;

import az.blog.domain.dto.ArticleCreationDTO;
import az.blog.domain.dto.ArticleModificationDTO;
import az.blog.domain.entity.Article;
import az.blog.repository.ArticleRepository;
import az.blog.resource.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.size() != 0 ? articles :
                Collections.emptyList();
    }

    public boolean insertArticle(ArticleCreationDTO articleDTO) {
        if (articleDTO == null) {
            log.error("articleDTO is null");
            return false;
        }

        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setUrl(articleDTO.getUrl());
        article.setStatus(articleDTO.getStatus());
        article.setCreationDate(articleDTO.getCreationDate());
        article.setAuthor(articleDTO.getAuthor());

        articleRepository.save(article);
        return true;
    }

    //TODO: Convert method return type to ArticleDTO
    public void updateArticle(Long id, ArticleModificationDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + id));

        article.setTitle(articleDTO.getTitle());
        article.setUrl(articleDTO.getUrl());
        article.setStatus(articleDTO.getStatus());
        article.setPublishedDate(articleDTO.getPublishedDate());
        article.setCreationDate(articleDTO.getCreationDate());
        article.setAuthor(articleDTO.getAuthor());

        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
