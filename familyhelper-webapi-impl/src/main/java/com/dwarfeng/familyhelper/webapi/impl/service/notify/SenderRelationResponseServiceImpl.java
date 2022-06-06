package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSenderRelation;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.SenderRelationResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.bean.entity.SenderInfo;
import com.dwarfeng.notify.stack.bean.entity.SenderRelation;
import com.dwarfeng.notify.stack.bean.entity.Topic;
import com.dwarfeng.notify.stack.bean.entity.key.SenderRelationKey;
import com.dwarfeng.notify.stack.service.NotifySettingMaintainService;
import com.dwarfeng.notify.stack.service.SenderInfoMaintainService;
import com.dwarfeng.notify.stack.service.SenderRelationMaintainService;
import com.dwarfeng.notify.stack.service.TopicMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SenderRelationResponseServiceImpl implements SenderRelationResponseService {

    private final SenderRelationMaintainService senderRelationMaintainService;
    private final NotifySettingMaintainService notifySettingMaintainService;
    private final TopicMaintainService topicMaintainService;
    private final SenderInfoMaintainService senderInfoMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SenderRelationResponseServiceImpl(
            @Qualifier("notifySenderRelationMaintainService")
            SenderRelationMaintainService senderRelationMaintainService,
            @Qualifier("notifyNotifySettingMaintainService")
            NotifySettingMaintainService notifySettingMaintainService,
            @Qualifier("notifyTopicMaintainService")
            TopicMaintainService topicMaintainService,
            @Qualifier("notifySenderInfoMaintainService")
            SenderInfoMaintainService senderInfoMaintainService
    ) {
        this.senderRelationMaintainService = senderRelationMaintainService;
        this.notifySettingMaintainService = notifySettingMaintainService;
        this.topicMaintainService = topicMaintainService;
        this.senderInfoMaintainService = senderInfoMaintainService;
    }

    @Override
    public boolean exists(SenderRelationKey key) throws ServiceException {
        return senderRelationMaintainService.exists(key);
    }

    @Override
    public SenderRelation get(SenderRelationKey key) throws ServiceException {
        return senderRelationMaintainService.get(key);
    }

    @Override
    public SenderRelationKey insert(SenderRelation senderRelation) throws ServiceException {
        return senderRelationMaintainService.insert(senderRelation);
    }

    @Override
    public void update(SenderRelation senderRelation) throws ServiceException {
        senderRelationMaintainService.update(senderRelation);
    }

    @Override
    public void delete(SenderRelationKey key) throws ServiceException {
        senderRelationMaintainService.delete(key);
    }

    @Override
    public PagedData<SenderRelation> all(PagingInfo pagingInfo) throws ServiceException {
        return senderRelationMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispSenderRelation getDisp(SenderRelationKey key) throws ServiceException {
        SenderRelation senderRelation = get(key);
        return toDisp(senderRelation);
    }

    @Override
    public PagedData<DispSenderRelation> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<SenderRelation> lookup = all(pagingInfo);
        return toDispPagedData(lookup);
    }

    private DispSenderRelation toDisp(SenderRelation senderRelation) throws ServiceException {
        NotifySetting notifySetting = notifySettingMaintainService.getIfExists(
                new LongIdKey(senderRelation.getKey().getNotifySettingId())
        );
        Topic topic = topicMaintainService.getIfExists(new StringIdKey(senderRelation.getKey().getTopicId()));
        SenderInfo senderInfo = null;
        if (Objects.nonNull(senderRelation.getSenderInfoKey())) {
            senderInfo = senderInfoMaintainService.getIfExists(senderRelation.getSenderInfoKey());
        }
        return DispSenderRelation.of(senderRelation, notifySetting, topic, senderInfo);
    }

    private PagedData<DispSenderRelation> toDispPagedData(PagedData<SenderRelation> lookup) throws ServiceException {
        List<DispSenderRelation> dispSenderRelations = new ArrayList<>();
        for (SenderRelation senderRelation : lookup.getData()) {
            dispSenderRelations.add(toDisp(senderRelation));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispSenderRelations
        );
    }
}
