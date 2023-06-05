package com.ITBlogs.models;

import lombok.Data;

@Data
public class PaginatedResult<T> {
    private T result;
    private int currentPage;
    private int totalPages;
}
