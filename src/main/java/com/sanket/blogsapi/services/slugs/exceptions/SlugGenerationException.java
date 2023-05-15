package com.sanket.blogsapi.services.slugs.exceptions;

import com.sanket.blogsapi.services.slugs.constants.SlugsErrorMessages;

public class SlugGenerationException extends RuntimeException {

    public SlugGenerationException() {
        super(SlugsErrorMessages.ERROR_GENERATING_SLUG);
    }

    public SlugGenerationException(String title) {
        super(String.format(SlugsErrorMessages.ERROR_GENERATING_SLUG_FROM_TITLE, title));
    }
}
