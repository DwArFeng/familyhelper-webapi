package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbRecordCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbRecordUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 个人最佳记录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PbRecordResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    PbRecord get(LongIdKey key) throws ServiceException;

    PagedData<PbRecord> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PbRecord> childForPbItem(LongIdKey pbItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    LongIdKey createPbRecord(StringIdKey userKey, PbRecordCreateInfo pbRecordCreateInfo)
            throws ServiceException;

    void updatePbRecord(StringIdKey userKey, PbRecordUpdateInfo pbRecordUpdateInfo) throws ServiceException;

    void removePbRecord(StringIdKey userKey, LongIdKey pbRecordKey) throws ServiceException;
}
