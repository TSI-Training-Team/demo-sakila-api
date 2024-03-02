package com.example.sakila.output;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedOutput<T> {

    @Getter
    public static class PageInfo {
        private final Integer number;
        private final Integer size;
        private final Long itemCount;
        private final Long totalItemCount;
        private final Integer pageCount;

        public PageInfo(Page<?> page) {
            this.number = page.getNumber() + 1;
            this.size = page.getSize();
            this.itemCount = (long)page.getNumberOfElements();
            this.totalItemCount = page.getTotalElements();
            this.pageCount = page.getTotalPages();
        }
    }

    private final List<T> items;
    private final PageInfo page;

    public PagedOutput(Page<T> page) {
        this.items = page.toList();
        this.page = new PageInfo(page);
    }
}
