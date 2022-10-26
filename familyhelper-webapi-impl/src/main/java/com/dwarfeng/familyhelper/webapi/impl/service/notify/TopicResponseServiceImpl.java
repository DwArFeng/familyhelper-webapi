package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.TopicResponseService;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.notify.stack.service.TopicMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TopicResponseServiceImpl implements TopicResponseService {

    private final TopicMaintainService topicMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TopicResponseServiceImpl(
            @Qualifier("notifyTopicMaintainService") TopicMaintainService topicMaintainService
    ) {
        this.topicMaintainService = topicMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return topicMaintainService.exists(key);
    }

    @Override
    public Topic get(StringIdKey key) throws ServiceException {
        return topicMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(Topic topic) throws ServiceException {
        return topicMaintainService.insert(topic);
    }

    @Override
    public void update(Topic topic) throws ServiceException {
        topicMaintainService.update(topic);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        topicMaintainService.delete(key);
    }

    @Override
    public PagedData<Topic> all(PagingInfo pagingInfo) throws ServiceException {
        return topicMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Topic> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return topicMaintainService.lookup(TopicMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo);
    }
}
