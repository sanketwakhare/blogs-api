package com.sanket.blogsapi.articles.filters;

import com.sanket.blogsapi.articles.ArticleEntity;
import com.sanket.blogsapi.articles.dtos.ArticlesFilterCriteriaRequestDTO;
import com.sanket.blogsapi.common.sort.SortDirection;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticlesFilter {

    /**
     * Filter articles based on filter criteria
     *
     * @param articlesList       List of articles to be filtered
     * @param filterCriteria Filter criteria
     * @return Filtered list of articles
     */
    public Set<ArticleEntity> filterArticles(List<ArticleEntity> articlesList, ArticlesFilterCriteriaRequestDTO filterCriteria) {

        Set<ArticleEntity> articles = new HashSet<>(articlesList);

        // filter by user id
        UUID authorId = filterCriteria.getAuthorId();
        if (!Objects.isNull(authorId)) {
            articles = articles.stream().filter(article ->
                    article.getAuthors().stream().anyMatch(author -> author.getId().equals(authorId))
            ).collect(Collectors.toSet());
        }
        // filter by tags
        Set<String> tags = filterCriteria.getTags();
        tags = tags.stream().filter(tag -> !tag.isBlank()).collect(Collectors.toSet());
        if (!tags.isEmpty()) {
            Set<String> finalTags = tags;
            articles = articles.stream()
                    .filter(article -> article.getTags().stream().anyMatch(finalTags::contains))
                    .collect(Collectors.toSet());
        }
        // filter by date range
        Date fromDate = filterCriteria.getFromDate();
        Date toDate = filterCriteria.getToDate();
        if (!Objects.isNull(fromDate) && !Objects.isNull(toDate)) {
            articles = articles.stream()
                    .filter(article -> article.getCreatedAt().after(fromDate) && article.getCreatedAt().before(toDate))
                    .collect(Collectors.toSet());
        }

        // sort articles by createdAt date
        if (!Objects.isNull(filterCriteria.getSortCriteria())) {
            SortDirection direction = filterCriteria.getSortCriteria().getDirection();
            switch (direction) {
                case DESC ->
                        articles = articles.stream().sorted(Comparator.comparing(ArticleEntity::getCreatedAt).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
                case ASC ->
                        articles = articles.stream().sorted(Comparator.comparing(ArticleEntity::getCreatedAt)).collect(Collectors.toCollection(LinkedHashSet::new));
            }

        }

        return articles;
    }
}
