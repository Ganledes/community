package com.carl.community.dto;

import com.carl.community.model.User;
import lombok.Data;

/**
 * @author zhaoq
 * @date 2020/3/8 17:54
 */
@Data
public class NotificationDTO {
    private Long id;

    private Integer status;

    private Long gmtCreate;

    private String outerTittle;

    private String action;

    private User creator;
}
