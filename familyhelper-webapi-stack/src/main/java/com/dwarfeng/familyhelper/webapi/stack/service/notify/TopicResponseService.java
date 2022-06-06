package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 主题响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface TopicResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Topic get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Topic topic) throws ServiceException;

    void update(Topic topic) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Topic> all(PagingInfo pagingInfo) throws ServiceException;
}
