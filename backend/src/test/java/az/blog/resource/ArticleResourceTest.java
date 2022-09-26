package az.blog.resource;

import az.blog.domain.dto.ArticleCreationDTO;
import az.blog.domain.entity.Article;
import az.blog.domain.enumeration.ArticleStatus;
import az.blog.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
class ArticleResourceTest {

    @MockBean
    ArticleService articleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllArticlesTest() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Java Tutorial");
        article.setUrl("https://www.javatutorial.com/java");
        article.setPublishedDate(LocalDate.now());
        article.setStatus(ArticleStatus.PUBLISHED);

        List<Article> articleList = new ArrayList<>();
        articleList.add(article);

        when(articleService.getAllArticles()).thenReturn(articleList);

        mockMvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Java Tutorial")))
                .andExpect(jsonPath("$[0].author", Matchers.nullValue()));
    }

    @Test
    void createArticleTest() throws Exception {
        ArticleCreationDTO articleDTO = new ArticleCreationDTO();
        articleDTO.setTitle("Java Tutorial");
        articleDTO.setUrl("https://www.javatutorial.com/java");
        articleDTO.setCreationDate(LocalDate.now());

        when(articleService.insertArticle(Mockito.any())).thenReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(articleDTO);

        mockMvc.perform(post("/api/v1/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(json)).andExpect(status().isCreated());
    }

    @Test
    void should_throw_validation_exception() throws Exception {
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_bad_request_result_when_request_data_is_null() throws Exception {
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest());
    }
    
}