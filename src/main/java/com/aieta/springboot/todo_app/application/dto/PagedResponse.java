package com.aieta.springboot.todo_app.application.dto;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    private PagedResponse(List<T> content, int page, int size, long totalElements, 
            int totalPages, boolean first, boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }

    public static <T, R> PagedResponse<R> fromPage(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream().map(mapper).toList();

        return new PagedResponse<>(
            content, 
            page.getNumber(), 
            page.getSize(), 
            page.getTotalElements(), 
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
        );
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }
}
