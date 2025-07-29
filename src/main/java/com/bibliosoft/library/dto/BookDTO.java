package com.bibliosoft.library.dto;

// this is a DTO class
public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private Long borrowedByUserId;
    private boolean borrowed;

    public BookDTO() {
    }
    
    public BookDTO(Long id, String title, Long authorId, Long borrowedByUserId, boolean borrowed) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.borrowedByUserId = borrowedByUserId;
        this.borrowed = borrowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBorrowedByUserId() {
        return borrowedByUserId;
    }

    public void setBorrowedByUserId(Long borrowedByUserId) {
        this.borrowedByUserId = borrowedByUserId;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", borrowedByUserId=" + borrowedByUserId +
                ", borrowed=" + borrowed +
                '}';
    }
}
