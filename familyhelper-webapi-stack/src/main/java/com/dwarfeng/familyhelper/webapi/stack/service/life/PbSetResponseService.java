package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbSet;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 个人最佳集合响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PbSetResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    PbSet get(LongIdKey key) throws ServiceException;

    PagedData<PbSet> all(PagingInfo pagingInfo) throws ServiceException;

    DispPbSet getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispPbSet> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPbSet> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createPbSet(StringIdKey userKey, PbSetCreateInfo pbSetCreateInfo)
            throws ServiceException;

    void updatePbSet(StringIdKey userKey, PbSetUpdateInfo pbSetUpdateInfo) throws ServiceException;

    void removePbSet(StringIdKey userKey, LongIdKey pbSetKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, PbSetPermissionUpsertInfo permissionUpsertInfo) throws ServiceException;

    void removePermission(StringIdKey userKey, PbSetPermissionRemoveInfo permissionRemoveInfo) throws ServiceException;
}
