package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispMeta;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.MetaResponseService;
import com.dwarfeng.notify.stack.bean.entity.Meta;
import com.dwarfeng.notify.stack.bean.entity.MetaIndicator;
import com.dwarfeng.notify.stack.bean.key.MetaIndicatorKey;
import com.dwarfeng.notify.stack.bean.key.MetaKey;
import com.dwarfeng.notify.stack.service.MetaIndicatorMaintainService;
import com.dwarfeng.notify.stack.service.MetaMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("notifyMetaResponseServiceImpl")
public class MetaResponseServiceImpl implements MetaResponseService {

    private final MetaMaintainService metaMaintainService;
    private final MetaIndicatorMaintainService metaIndicatorMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MetaResponseServiceImpl(
            @Qualifier("notifyMetaMaintainService")
            MetaMaintainService metaMaintainService,
            @Qualifier("notifyMetaIndicatorMaintainService")
            MetaIndicatorMaintainService metaIndicatorMaintainService
    ) {
        this.metaMaintainService = metaMaintainService;
        this.metaIndicatorMaintainService = metaIndicatorMaintainService;
    }

    @Override
    public boolean exists(MetaKey key) throws ServiceException {
        return metaMaintainService.exists(key);
    }

    @Override
    public Meta get(MetaKey key) throws ServiceException {
        return metaMaintainService.get(key);
    }

    @Override
    public MetaKey insert(Meta meta) throws ServiceException {
        return metaMaintainService.insert(meta);
    }

    @Override
    public void update(Meta meta) throws ServiceException {
        metaMaintainService.update(meta);
    }

    @Override
    public void delete(MetaKey key) throws ServiceException {
        metaMaintainService.delete(key);
    }

    @Override
    public PagedData<Meta> all(PagingInfo pagingInfo) throws ServiceException {
        return metaMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Meta> childForNotifySettingTopicUser(
            LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return metaMaintainService.lookup(
                MetaMaintainService.CHILD_FOR_NOTIFY_SETTING_TOPIC_USER_META_ID_ASC,
                new Object[]{notifySettingKey, topicKey, userKey}, pagingInfo
        );
    }

    @Override
    public DispMeta getDisp(MetaKey key) throws ServiceException {
        Meta meta = get(key);
        return toDisp(meta);
    }

    @Override
    public PagedData<DispMeta> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<Meta> lookup = all(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispMeta> childForNotifySettingTopicUserDisp(
            LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Meta> lookup = childForNotifySettingTopicUser(notifySettingKey, topicKey, userKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    private DispMeta toDisp(Meta meta) throws ServiceException {
        String topicId = meta.getKey().getTopicId();
        String metaId = meta.getKey().getMetaId();
        MetaIndicator indicator = metaIndicatorMaintainService.getIfExists(
                new MetaIndicatorKey(topicId, metaId)
        );
        return DispMeta.of(meta, indicator);
    }

    private PagedData<DispMeta> toDispPagedData(PagedData<Meta> lookup) throws ServiceException {
        List<DispMeta> dispMetas = new ArrayList<>();
        for (Meta meta : lookup.getData()) {
            dispMetas.add(toDisp(meta));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispMetas
        );
    }
}
