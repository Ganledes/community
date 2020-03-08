package com.carl.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoq
 */
@Data
public class PaginationDTO<T> {

    private Integer page;
    private Integer size;
    private Integer totalPage;
    private Integer count;
    private Boolean showNext;
    private Boolean showFirst;
    private Boolean showPrevious;
    private Boolean showEndPage;

    private List<Integer> pages;

    private List<T> list;

    public void setPagination(Integer page, Integer size, Integer count) {
        totalPage = count % size == 0 ? count / size : count / size + 1;
        if (totalPage == 0) {
            totalPage = 1;
        }
        pages = new ArrayList<>();
        pages.add(page);
        int pageCount = 3;
        for (int i = 1; i < pageCount + 1; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        showNext = page < totalPage;
        showPrevious = page > 1;
        showFirst = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);
    }
}
