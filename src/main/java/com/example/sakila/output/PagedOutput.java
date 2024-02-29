package com.example.sakila.output;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PagedOutput<T> {

    @Data
    private static class PageInfo {
        private Integer number;
        private Integer size;
        private Integer itemCount;
        private Long totalItemCount;
        private Integer pageCount;

        public PageInfo(Page<?> page) {
            this.number = page.getNumber();
            this.size = page.getSize();
            this.itemCount = page.getNumberOfElements();
            this.totalItemCount = page.getTotalElements();
            this.pageCount = page.getTotalPages();
        }
    }

    private Iterable<T> items;
    private PageInfo page;

    public PagedOutput(Page<T> page) {
        this.items = page.toList();
        this.page = new PageInfo(page);
    }
}
