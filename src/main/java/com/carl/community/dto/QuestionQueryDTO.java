package com.carl.community.dto;

import lombok.Data;

/**
 * @author zhaoq
 * @date 2020/3/10 21:17
 */
@Data
public class QuestionQueryDTO {

    private String search;
    private int start;
    private int size;

}
