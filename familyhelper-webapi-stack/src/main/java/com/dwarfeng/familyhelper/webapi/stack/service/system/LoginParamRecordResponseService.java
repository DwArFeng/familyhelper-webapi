package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginParamRecord;
import com.dwarfeng.acckeeper.stack.bean.key.RecordKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录参数记录响应服务。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public interface LoginParamRecordResponseService extends Service {

    boolean exists(RecordKey key) throws ServiceException;

    LoginParamRecord get(RecordKey key) throws ServiceException;

    PagedData<LoginParamRecord> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginParamRecord> childForLoginHistory(LongIdKey loginHistoryKey, PagingInfo pagingInfo)
            throws ServiceException;

}
