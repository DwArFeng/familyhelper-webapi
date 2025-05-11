package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poatac;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatacKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoatac;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板活动权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface PoatacResponseService extends Service {

    boolean exists(PoatacKey key) throws ServiceException;

    Poatac get(PoatacKey key) throws ServiceException;

    DispPoatac getDisp(PoatacKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poatac> childForActivityTemplate(LongIdKey activityTemplateKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPoatac> childForActivityTemplateDisp(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
