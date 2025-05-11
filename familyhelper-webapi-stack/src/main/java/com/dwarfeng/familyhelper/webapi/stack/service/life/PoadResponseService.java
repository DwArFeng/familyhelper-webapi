package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poad;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoadKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoad;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动数据权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface PoadResponseService extends Service {

    boolean exists(PoadKey key) throws ServiceException;

    Poad get(PoadKey key) throws ServiceException;

    DispPoad getDisp(PoadKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poad> childForActivityDataSet(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPoad> childForActivityDataSetDisp(
            LongIdKey activityDataSetKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
