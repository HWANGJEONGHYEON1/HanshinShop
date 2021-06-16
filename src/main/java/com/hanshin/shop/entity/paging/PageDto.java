package com.hanshin.shop.entity.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDto {

    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private Criteria criteria;

    public PageDto(int total, Criteria criteria) {
        this.total = total;
        this.criteria = criteria;

        this.endPage = (int) (Math.ceil(criteria.getPageNum() / 10.0)) * 10;
        this.startPage = endPage - 9;

        int realEnd = (int) (Math.ceil((total * 1.0) / criteria.getAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}
