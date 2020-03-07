package com.carl.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhaoq
 * @date 2020/3/7 18:50
 */
@Data
public class TagDTO {
    private String category;
    private List<String> tags;
}
