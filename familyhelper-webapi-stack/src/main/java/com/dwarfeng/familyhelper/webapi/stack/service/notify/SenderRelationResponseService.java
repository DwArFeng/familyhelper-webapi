package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSenderRelation;
import com.dwarfeng.notify.stack.bean.entity.SenderRelation;
import com.dwarfeng.notify.stack.bean.entity.key.SenderRelationKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 发送器关联响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface SenderRelationResponseService extends Service {

    boolean exists(SenderRelationKey key) throws ServiceException;

    SenderRelation get(SenderRelationKey key) throws ServiceException;

    SenderRelationKey insert(SenderRelation senderRelation) throws ServiceException;

    void update(SenderRelation senderRelation) throws ServiceException;

    void delete(SenderRelationKey key) throws ServiceException;

    PagedData<SenderRelation> all(PagingInfo pagingInfo) throws ServiceException;

    DispSenderRelation getDisp(SenderRelationKey key) throws ServiceException;

    PagedData<DispSenderRelation> allDisp(PagingInfo pagingInfo) throws ServiceException;
}
