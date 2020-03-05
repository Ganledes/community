package com.carl.community.dto;

import com.carl.community.model.User;
import lombok.Data;

/**
 * @author zhaoq
 * @date 2020/3/5 15:17
 */
@Data
public class CommentDTO {

    private Long id;

    private String content;

    private Integer parentType;

    private Long parentId;

    private Long commenter;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private User user;
}
