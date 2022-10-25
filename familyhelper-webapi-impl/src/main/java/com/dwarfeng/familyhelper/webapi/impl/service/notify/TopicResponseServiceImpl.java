package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.clannad.stack.service.NotifyTopicMaintainService;
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
    private final NotifyTopicMaintainService clannadNotifyTopicMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TopicResponseServiceImpl(
            @Qualifier("notifyTopicMaintainService") TopicMaintainService topicMaintainService,
            @Qualifier("familyhelperClannadNotifyTopicMaintainService")
            NotifyTopicMaintainService clannadNotifyTopicMaintainService
    ) {
        this.topicMaintainService = topicMaintainService;
        this.clannadNotifyTopicMaintainService = clannadNotifyTopicMaintainService;
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
        StringIdKey topicKey = topicMaintainService.insert(topic);
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic clannadNotifyTopic
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic(
                topicKey, "通过 notify 模块插入/更新自动生成"
        );
        clannadNotifyTopicMaintainService.insertOrUpdate(clannadNotifyTopic);
        return topicKey;
    }

    @Override
    public void update(Topic topic) throws ServiceException {
        topicMaintainService.update(topic);
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic clannadNotifyTopic
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic(
                topic.getKey(), "通过 notify 模块插入/更新自动生成"
        );
        clannadNotifyTopicMaintainService.insertOrUpdate(clannadNotifyTopic);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        topicMaintainService.delete(key);
        clannadNotifyTopicMaintainService.delete(key);
    }

    @Override
    public PagedData<Topic> all(PagingInfo pagingInfo) throws ServiceException {
        return topicMaintainService.lookup(pagingInfo);
    }
}
