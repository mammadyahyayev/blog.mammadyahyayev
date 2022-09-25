package az.blog.domain.entity;

import az.blog.domain.enumeration.SocialMedia;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String description;
    private String address;

    @ElementCollection
    @CollectionTable(name = "author_social_account",
            joinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "url")
    @Column(name = "social_media")
    private Map<SocialMedia, String> socialAccounts = new HashMap<>();

    @OneToMany(mappedBy = "author")
    private Set<Article> articles = new HashSet<>();
}
