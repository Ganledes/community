package com.carl.community.service;

import com.carl.community.dto.NotificationDTO;
import com.carl.community.dto.PaginationDTO;
import com.carl.community.enums.NotificationStatus;
import com.carl.community.enums.NotificationType;
import com.carl.community.mapper.NotificationMapper;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.Notification;
import com.carl.community.model.NotificationExample;
import com.carl.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoq
 * @date 2020/3/8 17:52
 */
@Service
public class NotificationService {

    private NotificationMapper notificationMapper;

    private UserMapper userMapper;

    public NotificationService(NotificationMapper notificationMapper, UserMapper userMapper) {
        this.notificationMapper = notificationMapper;
        this.userMapper = userMapper;
    }

    public void create(Notification notification) {
        notification.setGmtCreate(System.currentTimeMillis());
        notificationMapper.insertSelective(notification);
    }

    public PaginationDTO<NotificationDTO> list(Long userId, int page, int size) {
        PaginationDTO<NotificationDTO> pagination = new PaginationDTO<>();
        pagination.setPage(page);
        pagination.setSize(size);
        int start = (page - 1) * size;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(start, size));
        ArrayList<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            User creator = userMapper.selectByPrimaryKey(notification.getCreatorId());
            notificationDTO.setCreator(creator);
            notificationDTO.setAction(NotificationType.getActionByType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pagination.setList(notificationDTOS);
        Integer count = Math.toIntExact(notificationMapper.countByExample(new NotificationExample()));
        pagination.setCount(count);
        pagination.setPagination(page, size, count);
        return pagination;
    }

    public int getUnreadCount(long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatus.UNREAD.getStatus());
        long value = notificationMapper.countByExample(notificationExample);
        return Math.toIntExact(value);
    }

    public void allRead(Long receiverId) {
        Notification notification = new Notification();
        notification.setStatus(NotificationStatus.READ.getStatus());
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(receiverId);
        notificationMapper.updateByExampleSelective(notification, notificationExample);
    }

    public NotificationDTO readOne(Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        notification.setStatus(NotificationStatus.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        return notificationDTO;
    }

}
