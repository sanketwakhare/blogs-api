package com.sanket.blogsapi.articles.exceptions;

import com.sanket.blogsapi.articles.constants.ArticlesErrorMessages;

import java.util.UUID;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException() {
        super(ArticlesErrorMessages.ARTICLE_NOT_FOUND);
    }

    public ArticleNotFoundException(UUID id) {
        super(String.format(ArticlesErrorMessages.ARTICLE_WITH_ID_NOT_FOUND, id));
    }
}
