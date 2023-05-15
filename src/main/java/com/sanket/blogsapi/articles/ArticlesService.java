package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.services.slugs.SlugsService;
import com.sanket.blogsapi.users.UserEntity;
import com.sanket.blogsapi.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final UsersService usersService;
    private final SlugsService slugsService;

    public ArticlesService(@Autowired ArticlesRepository articlesRepository,
                           @Autowired UsersService usersService,
                           @Autowired SlugsService slugsService) {
        this.articlesRepository = articlesRepository;
        this.usersService = usersService;
        this.slugsService = slugsService;
    }

    /**
     * Create an article
     *
     * @param article Article to be created
     * @param userId  User ID of the user creating the article
     * @return Created article
     */
    public ArticleEntity createArticle(ArticleEntity article, UUID userId) {
        // get author details
        UserEntity author = usersService.findById(userId);
        List<UserEntity> authors = new ArrayList<>();
        authors.add(author);

        // generate slug from title
        String slug = slugsService.generateSlug(article.getTitle());

        // create new article
        ArticleEntity newArticle = ArticleEntity.builder()
                .title(article.getTitle())
                .subtitle(article.getSubtitle())
                .body(article.getBody())
                .slug(slug)
                .authors(authors)
                .build();

        // save article
        return articlesRepository.save(newArticle);
    }
}
