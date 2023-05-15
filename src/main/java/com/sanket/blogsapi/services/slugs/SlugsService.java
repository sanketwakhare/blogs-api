package com.sanket.blogsapi.services.slugs;

import com.sanket.blogsapi.common.constants.CommonConstants;
import com.sanket.blogsapi.services.slugs.exceptions.SlugGenerationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SlugsService {

    /**
     * Generates a slug from the title
     *
     * @param title Title of the article
     * @return Slug generated from the title
     */
    public String generateSlug(String title) {
        try {
            return title.toLowerCase()
                    .replaceAll("[:;,.~`()+*&^%#$@!]", CommonConstants.EMPTY_STRING)
                    .replaceAll(CommonConstants.SPACE, CommonConstants.HYPHEN);
        } catch (Exception e) {
            throw new SlugGenerationException(title);
        }
    }
}
