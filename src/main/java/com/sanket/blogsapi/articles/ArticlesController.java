package com.sanket.blogsapi.articles;

import com.sanket.blogsapi.articles.dtos.*;
import com.sanket.blogsapi.services.tokens.TokensService;
import com.sanket.blogsapi.users.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        // this is implemented by searchArticles() method
        // used Post method to implement searchArticles
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

    /**
     * Get an article by ID
     *
     * @param id Article ID
     * @return Article
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticleById(@PathVariable("id") UUID id) {
        ArticleEntity article = articlesService.getArticleById(id);
        ArticleResponseDTO responseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * Get all articles by author ID
     *
     * @param authorId Author ID
     * @return List of articles
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<ArticlesListResponseDTO> getAllArticlesByAuthorId(@PathVariable("authorId") UUID authorId) {
        List<ArticleEntity> articles = articlesService.getAllArticlesByAuthorId(authorId);

        // TODO: this model mapping did not work
        // ArticlesListResponseDTO responseDTO = modelMapper.map(articles, ArticlesListResponseDTO.class);

        // convert to DTO
        ArticlesListResponseDTO responseDTO = mapToArticlesListResponseDTO(articles);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * Create a new article
     *
     * @param requestDTO  Article details
     * @param bearerToken Bearer token of the user creating the article
     * @return Created article
     */
    @PostMapping("")
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody CreateArticleRequestDTO requestDTO, @RequestHeader("Authorization") String bearerToken) {
        UUID userId = tokensService.getUserIdFromToken(bearerToken);
        ArticleEntity article = modelMapper.map(requestDTO, ArticleEntity.class);
        ArticleEntity newArticle = articlesService.createArticle(article, userId);
        ArticleResponseDTO responseDTO = modelMapper.map(newArticle, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Update an article
     *
     * @param updateArticleRequestDTO Article details
     * @param id                      Article ID
     * @return Updated article
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@RequestBody UpdateArticleRequestDTO updateArticleRequestDTO, @PathVariable("id") UUID id) {
        ArticleEntity articleEntity = modelMapper.map(updateArticleRequestDTO, ArticleEntity.class);
        ArticleEntity article = articlesService.updateArticle(id, articleEntity);
        ArticleResponseDTO responseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Publish an article
     *
     * @param id Article ID
     * @return Published article
     */
    @PatchMapping("/publish/{id}")
    public ResponseEntity<ArticleResponseDTO> publishArticle(@PathVariable("id") UUID id) {
        ArticleEntity article = articlesService.publishArticle(id);
        ArticleResponseDTO responseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Delete an article
     *
     * @param id Article ID
     * @return Deleted article
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> deleteArticle(@PathVariable("id") UUID id) {
        ArticleEntity article = articlesService.deleteArticle(id);
        ArticleResponseDTO responseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Move an article to draft
     *
     * @param id Article ID
     * @return Drafted article
     */
    @PatchMapping("/move-to-draft/{id}")
    public ResponseEntity<ArticleResponseDTO> moveArticleToDraft(@PathVariable("id") UUID id) {
        ArticleEntity article = articlesService.moveArticleToDraft(id);
        ArticleResponseDTO responseDTO = modelMapper.map(article, ArticleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Like an article
     *
     * @param articleId   Article ID
     * @param bearerToken Bearer token of the user liking the article
     * @return Article reaction
     */
    @PatchMapping("/like/{id}")
    public ResponseEntity<ArticleReactionResponseDTO> likeArticle(@PathVariable("id") UUID articleId, @RequestHeader("Authorization") String bearerToken) {
        UUID userId = tokensService.getUserIdFromToken(bearerToken);
        ArticleReactionType reaction = articlesService.likeArticle(articleId, userId);
        ArticleReactionResponseDTO responseDTO = new ArticleReactionResponseDTO();
        responseDTO.setArticleId(articleId);
        responseDTO.setUserId(userId);
        responseDTO.setReaction(reaction);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Dislike an article
     *
     * @param articleId   Article ID
     * @param bearerToken Bearer token of the user disliking the article
     * @return Article reaction
     */
    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<ArticleReactionResponseDTO> dislikeArticle(@PathVariable("id") UUID articleId, @RequestHeader("Authorization") String bearerToken) {
        UUID userId = tokensService.getUserIdFromToken(bearerToken);
        ArticleReactionType reaction = articlesService.dislikeArticle(articleId, userId);
        ArticleReactionResponseDTO responseDTO = new ArticleReactionResponseDTO();
        responseDTO.setArticleId(articleId);
        responseDTO.setUserId(userId);
        responseDTO.setReaction(reaction);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    /**
     * Get all likes for an article
     *
     * @param articleId Article ID
     * @return Article likes details
     */
    @GetMapping("/likes/{id}")
    public ResponseEntity<ArticleLikesResponseDTO> getArticleLikes(@PathVariable("id") UUID articleId) {
        Set<UserEntity> users = articlesService.getArticleLikes(articleId);

        ArticleLikesResponseDTO responseDTO = new ArticleLikesResponseDTO();
        Set<ArticleLikedByUserResponseDTO> likedBy = new HashSet<>();
        for (UserEntity user : users) {
            ArticleLikedByUserResponseDTO userResponseDTO = modelMapper.map(user, ArticleLikedByUserResponseDTO.class);
            likedBy.add(userResponseDTO);
        }
        responseDTO.setArticleId(articleId);
        responseDTO.setLikedBy(likedBy);
        responseDTO.setLikesCount(likedBy.size());

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * Get all articles / search articles / filter articles
     *
     * @param requestDTO filter criteria details
     * @return list of articles
     */
    @PostMapping("search")
    public ResponseEntity<ArticlesListResponseDTO> searchArticles(@RequestBody ArticlesFilterCriteriaRequestDTO requestDTO) {
        List<ArticleEntity> articles = articlesService.searchArticles(requestDTO);
        // convert to DTO
        ArticlesListResponseDTO responseDTO = mapToArticlesListResponseDTO(articles);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /**
     * convert article entity list to DTO
     *
     * @param articles article entity list
     * @return article DTO list
     */
    private ArticlesListResponseDTO mapToArticlesListResponseDTO(List<ArticleEntity> articles) {
        ArticlesListResponseDTO responseDTO = new ArticlesListResponseDTO();
        // use linked hash set to preserve sorting order of results
        responseDTO.setArticles(new LinkedHashSet<>());
        for (ArticleEntity article : articles) {
            ArticleResponseDTO articleResponseDTO = modelMapper.map(article, ArticleResponseDTO.class);
            responseDTO.getArticles().add(articleResponseDTO);
        }
        return responseDTO;
    }
}
