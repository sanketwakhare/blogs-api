package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.articles.dtos.ArticleResponseDTO;
import com.sanket.blogsapi.articles.dtos.CreateArticleRequestDTO;
import com.sanket.blogsapi.services.tokens.TokensService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesService articlesService;
    private final TokensService tokensService;
    private final ModelMapper modelMapper;

    public ArticlesController(@Autowired ArticlesService articlesService,
                              @Autowired TokensService tokensService,
                              @Autowired ModelMapper modelMapper) {
        this.articlesService = articlesService;
        this.tokensService = tokensService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<Void> getAllArticles() {
        // TODO 05:
        //  1. call articlesService.getAllArticles()
        //  2. respond with 200 OK and list of articles

        // TODO 07:
        //  1. add filter by author id `?authorId=1`
        //  2. add filter by tag `?tag=java`
        //  3. add sort by date `?sortBy=date`
        //  4. add filter by date range `?from=2021-01-01&to=2021-01-31`
        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<Void> getArticleById() {
        // TODO 06:
        //  1. call articlesService.getArticleById()
        //  2. respond with 200 OK and article details
        return null;
    }

    /**
     * Create a new article
     *
     * @param requestDTO  Article details
     * @param bearerToken Bearer token of the user creating the article
     * @return Created article
     */
    @PostMapping("")
    ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody CreateArticleRequestDTO requestDTO, @RequestHeader("Authorization") String bearerToken) {
        UUID userId = tokensService.getUserIdFromToken(bearerToken);
        ArticleEntity article = modelMapper.map(requestDTO, ArticleEntity.class);
        ArticleEntity newArticle = articlesService.createArticle(article, userId);
        ArticleResponseDTO responseDTO = modelMapper.map(newArticle, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> updateArticle() {
        // TODO 09:
        //  1. create a ArticleUpdateDTO (containing title, description, body, tags)
        //  2. call articlesService.updateArticle() with those details
        //  3. check that client sends a token which validates this user
        //  4. respond with 202 ACCEPTED if article is updated successfully
        return null;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteArticle() {
        // TODO 10:
        //  1. call articlesService.deleteArticle()
        //  2. check that client sends a token which validates this user
        //  3. respond with 202 ACCEPTED if article is deleted successfully
        return null;
    }

    @PutMapping("/{id}/like")
    ResponseEntity<Void> likeArticle() {
        // TODO 11:
        //  1. call articlesService.likeArticle()
        //  2. check that client sends a token which validates this user
        //  3. respond with 202 ACCEPTED if article is liked successfully
        return null;
    }

    @DeleteMapping("/{id}/like")
    ResponseEntity<Void> unlikeArticle() {
        // TODO 12:
        //  1. call articlesService.unlikeArticle()
        //  2. check that client sends a token which validates this user
        //  3. respond with 202 ACCEPTED if article is unliked successfully
        return null;
    }
}
