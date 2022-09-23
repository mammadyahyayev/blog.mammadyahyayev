package az.blog.domain.dto;

import az.blog.domain.entity.Article;
import az.blog.domain.enumeration.SocialMedia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthorDTO {
    private Long id;
    private String name;
    private String surname;
    private String description;
    private String address;
    private Map<SocialMedia, String> socialAccounts = new HashMap<>();
    private Set<Article> articles = new HashSet<>();
}
