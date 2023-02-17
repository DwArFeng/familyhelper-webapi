package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.MetaIndicator;
import com.dwarfeng.notify.stack.bean.key.MetaIndicatorKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 元数据指示器响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface MetaIndicatorResponseService extends Service {

    boolean exists(MetaIndicatorKey key) throws ServiceException;

    MetaIndicator get(MetaIndicatorKey key) throws ServiceException;

    MetaIndicatorKey insert(MetaIndicator metaIndicator) throws ServiceException;

    void update(MetaIndicator metaIndicator) throws ServiceException;

    void delete(MetaIndicatorKey key) throws ServiceException;

    PagedData<MetaIndicator> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<MetaIndicator> childForTopic(StringIdKey topicKey, PagingInfo pagingInfo) throws ServiceException;

    /**
     * 获取某个用户在指定的通知设置和主题下缺失的元数据对应的指示器。
     *
     * <p>
     * 该方法不采用分页。
     *
     * @param notifySettingKey 元数据对应的通知主键。
     * @param topicKey         元数据对应的主题主键。
     * @param userKey          元数据对应的用户主键。
     * @return 缺失的元数据对应的指示器组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<MetaIndicator> metaMissing(
            LongIdKey notifySettingKey, StringIdKey topicKey, StringIdKey userKey
    ) throws ServiceException;
}
