package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.Poab;
import com.dwarfeng.familyhelper.finance.stack.bean.key.PoabKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispPoab;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 账本权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.2
 */
public interface PoabResponseService extends Service {

    boolean exists(PoabKey key) throws ServiceException;

    Poab get(PoabKey key) throws ServiceException;

    DispPoab getDisp(PoabKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Poab> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPoab> childForAccountBookDisp(
            LongIdKey accountBookKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
