package com.carl.community.dto;

import com.carl.community.model.User;
import lombok.Data;

/**
 * @author zhaoq
 */
@Data
public class QuestionDTO {

    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private String tag;
    private User user;

}
