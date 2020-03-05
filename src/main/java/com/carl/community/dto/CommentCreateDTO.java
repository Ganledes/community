package com.carl.community.dto;

import lombok.Data;

/**
 * @author zhaoq
 * @date 2020/3/3 16:35
 */
@Data
public class CommentCreateDTO {
    private String content;
    private Long parentId;
    private Integer parentType;
}
