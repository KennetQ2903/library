package com.bibliosoft.library.request;

public record AddBookRequest(
        String title,
        Long authorId) {

}
