package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 个人最佳文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PbFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    PbFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<PbFileInfo> childForPbRecord(LongIdKey pbRecordKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PbFileInfo> childForPbRecordInspectedDateDesc(LongIdKey pbRecordKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PbFileInfo> childForPbRecordOriginNameAsc(LongIdKey pbRecordKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PbFileInfo> childForPbRecordUploadedDateAsc(LongIdKey pbRecordKey, PagingInfo pagingInfo)
            throws ServiceException;

    PbFile downloadPbFile(StringIdKey userKey, LongIdKey pbFileKey) throws ServiceException;

    void uploadPbFile(StringIdKey userKey, PbFileUploadInfo pbFileUploadInfo) throws ServiceException;

    void removePbFile(StringIdKey userKey, LongIdKey pbFileKey) throws ServiceException;
}
