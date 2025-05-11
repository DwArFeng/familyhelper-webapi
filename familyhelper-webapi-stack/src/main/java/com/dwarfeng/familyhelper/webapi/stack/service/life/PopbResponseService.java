package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.Popb;
import com.dwarfeng.familyhelper.life.stack.bean.key.PopbKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPopb;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 个人记录权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PopbResponseService extends Service {

    boolean exists(PopbKey key) throws ServiceException;

    Popb get(PopbKey key) throws ServiceException;

    DispPopb getDisp(PopbKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Popb> childForPbSet(LongIdKey pbSetKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPopb> childForPbSetDisp(
            LongIdKey pbSetKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
