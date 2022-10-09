package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.clannad.stack.service.NotifyTopicMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.Topic;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.TopicResponseService;
import com.dwarfeng.notify.stack.service.TopicMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicResponseServiceImpl implements TopicResponseService {

    private final TopicMaintainService notifyTopicMaintainService;
    private final NotifyTopicMaintainService clannadNotifyTopicMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TopicResponseServiceImpl(
            @Qualifier("notifyTopicMaintainService")
            TopicMaintainService notifyTopicMaintainService,
            @Qualifier("familyhelperClannadNotifyTopicMaintainService")
            NotifyTopicMaintainService clannadNotifyTopicMaintainService
    ) {
        this.notifyTopicMaintainService = notifyTopicMaintainService;
        this.clannadNotifyTopicMaintainService = clannadNotifyTopicMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return notifyTopicMaintainService.exists(key);
    }

    @Override
    public Topic get(StringIdKey key) throws ServiceException {
        com.dwarfeng.notify.stack.bean.entity.Topic notifyTopic
                = notifyTopicMaintainService.get(key);
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic clannadNotifyTopic
                = clannadNotifyTopicMaintainService.get(key);
        return new Topic(
                key, notifyTopic.getLabel(), notifyTopic.isEnabled(), notifyTopic.getPriority(),
                clannadNotifyTopic.isPreferred(), clannadNotifyTopic.getCoolDownDuration(),
                notifyTopic.getRemark()
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public StringIdKey insert(Topic topic) throws ServiceException {
        com.dwarfeng.notify.stack.bean.entity.Topic notifyTopic
                = new com.dwarfeng.notify.stack.bean.entity.Topic(
                topic.getKey(), topic.getLabel(), topic.getRemark(), topic.isEnabled(), topic.getPriority()
        );
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic clannadNotifyTopic
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic(
                topic.getKey(), "通过 topic 插入/更新自动生成", topic.isPreferred(), topic.getCoolDownDuration()
        );
        notifyTopicMaintainService.insert(notifyTopic);
        clannadNotifyTopicMaintainService.insertOrUpdate(clannadNotifyTopic);

        return topic.getKey();
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(Topic topic) throws ServiceException {
        com.dwarfeng.notify.stack.bean.entity.Topic notifyTopic
                = new com.dwarfeng.notify.stack.bean.entity.Topic(
                topic.getKey(), topic.getLabel(), topic.getRemark(), topic.isEnabled(), topic.getPriority()
        );
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic clannadNotifyTopic
                = new com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic(
                topic.getKey(), "通过 topic 插入/更新自动生成", topic.isPreferred(), topic.getCoolDownDuration()
        );
        notifyTopicMaintainService.update(notifyTopic);
        clannadNotifyTopicMaintainService.insertOrUpdate(clannadNotifyTopic);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        notifyTopicMaintainService.delete(key);
        clannadNotifyTopicMaintainService.delete(key);
    }

    @Override
    public PagedData<Topic> all(PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.notify.stack.bean.entity.Topic> notifyLookup
                = notifyTopicMaintainService.lookup(pagingInfo);
        return toVoPagedData(notifyLookup);
    }

    private Topic toVo(
            com.dwarfeng.notify.stack.bean.entity.Topic notifyTopic
    ) throws ServiceException {
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.NotifyTopic clannadNotifyTopic
                = clannadNotifyTopicMaintainService.get(notifyTopic.getKey());
        return new Topic(
                notifyTopic.getKey(), notifyTopic.getLabel(), notifyTopic.isEnabled(), notifyTopic.getPriority(),
                clannadNotifyTopic.isPreferred(), clannadNotifyTopic.getCoolDownDuration(),
                notifyTopic.getRemark()
        );
    }

    private PagedData<Topic> toVoPagedData(
            PagedData<com.dwarfeng.notify.stack.bean.entity.Topic> notifyLookup
    ) throws ServiceException {
        List<Topic> topics = new ArrayList<>();
        for (com.dwarfeng.notify.stack.bean.entity.Topic notifyTopic : notifyLookup.getData()) {
            topics.add(toVo(notifyTopic));
        }
        return new PagedData<>(
                notifyLookup.getCurrentPage(), notifyLookup.getTotalPages(), notifyLookup.getRows(),
                notifyLookup.getCount(), topics
        );
    }
}
