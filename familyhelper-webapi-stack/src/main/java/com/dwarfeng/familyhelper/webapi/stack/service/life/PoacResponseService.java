package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoacKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPoac;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface PoacResponseService extends Service {

    boolean exists(PoacKey key) throws ServiceException;

    Poac get(PoacKey key) throws ServiceException;

    DispPoac getDisp(PoacKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poac> childForActivity(LongIdKey activityKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPoac> childForActivityDisp(
            LongIdKey activityKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
