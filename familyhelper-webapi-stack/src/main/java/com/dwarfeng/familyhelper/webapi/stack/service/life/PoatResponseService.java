package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Poat;
import com.dwarfeng.familyhelper.life.stack.bean.key.PoatKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPoat;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface PoatResponseService extends Service {

    boolean exists(PoatKey key) throws ServiceException;

    Poat get(PoatKey key) throws ServiceException;

    DispPoat getDisp(PoatKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poat> childForActivityTemplate(LongIdKey activityTemplateKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPoat> childForActivityTemplateDisp(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
