package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.MetaIndicatorResponseService;
import com.dwarfeng.notify.stack.bean.entity.MetaIndicator;
import com.dwarfeng.notify.stack.bean.key.MetaIndicatorKey;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("notifyMetaIndicatorResponseServiceImpl")
public class MetaIndicatorResponseServiceImpl implements MetaIndicatorResponseService {

    private final MetaIndicatorMaintainService metaIndicatorMaintainService;
    private final MetaMaintainService metaMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MetaIndicatorResponseServiceImpl(
            @Qualifier("notifyMetaIndicatorMaintainService")
            MetaIndicatorMaintainService metaIndicatorMaintainService,
            @Qualifier("notifyMetaMaintainService")
            MetaMaintainService metaMaintainService
    ) {
        this.metaIndicatorMaintainService = metaIndicatorMaintainService;
        this.metaMaintainService = metaMaintainService;
    }

    @Override
    public boolean exists(MetaIndicatorKey key) throws ServiceException {
        return metaIndicatorMaintainService.exists(key);
    }

    @Override
    public MetaIndicator get(MetaIndicatorKey key) throws ServiceException {
        return metaIndicatorMaintainService.get(key);
    }

    @Override
    public MetaIndicatorKey insert(MetaIndicator metaIndicator) throws ServiceException {
        return metaIndicatorMaintainService.insert(metaIndicator);
    }

    @Override
    public void update(MetaIndicator metaIndicator) throws ServiceException {
        metaIndicatorMaintainService.update(metaIndicator);
    }

    @Override
    public void delete(MetaIndicatorKey key) throws ServiceException {
        metaIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<MetaIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return metaIndicatorMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<MetaIndicator> childForTopic(StringIdKey topicKey, PagingInfo pagingInfo)
            throws ServiceException {
        return metaIndicatorMaintainService.lookup(
                MetaIndicatorMaintainService.CHILD_FOR_TOPIC_META_ID_ASC,
                new Object[]{topicKey}, pagingInfo
        );
    }

    @Override
    public List<MetaIndicator> metaMissing(
            LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey
    ) throws ServiceException {
        // 找到事物属性的所有事物，并将主键的属性值映射至列表中。
        List<String> existsProperties = metaMaintainService.lookupAsList(
                MetaMaintainService.CHILD_FOR_NOTIFY_SETTING_TOPIC_USER_META_ID_ASC,
                new Object[]{notifySettingKey, topicKey, userKey}
        ).stream().map(t -> t.getKey().getMetaId()).collect(Collectors.toList());

        // 找到当前事物类型中所有的事物，以属性值为主键映射为 LinkedHashMap，便于处理，同时保证数据的顺序。
        Map<String, MetaIndicator> indicatorMap = metaIndicatorMaintainService.lookupAsList(
                MetaIndicatorMaintainService.CHILD_FOR_TOPIC_META_ID_ASC, new Object[]{topicKey}
        ).stream().collect(Collectors.toMap(
                t -> t.getKey().getMetaId(), Function.identity(), throwingMerger(), LinkedHashMap::new
        ));

        // Map 移除主键，并返回列表。
        existsProperties.forEach(indicatorMap.keySet()::remove);
        return new ArrayList<>(indicatorMap.values());
    }

    private <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
}
