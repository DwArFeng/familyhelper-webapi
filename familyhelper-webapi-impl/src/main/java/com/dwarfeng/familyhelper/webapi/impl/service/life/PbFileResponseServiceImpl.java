package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbFileInfo;
import com.dwarfeng.familyhelper.life.stack.service.PbFileInfoMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PbFileResponseServiceImpl implements PbFileResponseService {

    private final PbFileInfoMaintainService pbFileInfoMaintainService;
    private final PbFileOperateService pbFileOperateService;

    public PbFileResponseServiceImpl(
            @Qualifier("familyhelperLifePbFileInfoMaintainService")
            PbFileInfoMaintainService pbFileInfoMaintainService,
            @Qualifier("familyhelperLifePbFileOperateService")
            PbFileOperateService pbFileOperateService
    ) {
        this.pbFileInfoMaintainService = pbFileInfoMaintainService;
        this.pbFileOperateService = pbFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return pbFileInfoMaintainService.exists(key);
    }

    @Override
    public PbFileInfo get(LongIdKey key) throws ServiceException {
        return pbFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<PbFileInfo> childForPbRecord(LongIdKey pbRecordKey, PagingInfo pagingInfo) throws ServiceException {
        return pbFileInfoMaintainService.lookup(
                PbFileInfoMaintainService.CHILD_FOR_RECORD, new Object[]{pbRecordKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbFileInfo> childForPbRecordInspectedDateDesc(LongIdKey pbRecordKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbFileInfoMaintainService.lookup(
                PbFileInfoMaintainService.CHILD_FOR_RECORD_INSPECTED_DATE_DESC, new Object[]{pbRecordKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbFileInfo> childForPbRecordOriginNameAsc(LongIdKey pbRecordKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbFileInfoMaintainService.lookup(
                PbFileInfoMaintainService.CHILD_FOR_RECORD_ORIGIN_NAME_ASC, new Object[]{pbRecordKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbFileInfo> childForPbRecordUploadedDateAsc(LongIdKey pbRecordKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbFileInfoMaintainService.lookup(
                PbFileInfoMaintainService.CHILD_FOR_RECORD_UPLOADED_DATE_ASC, new Object[]{pbRecordKey}, pagingInfo
        );
    }

    @Override
    public PbFile downloadPbFile(StringIdKey userKey, LongIdKey pbFileKey) throws ServiceException {
        return pbFileOperateService.downloadPbFile(userKey, pbFileKey);
    }

    @Override
    public void uploadPbFile(StringIdKey userKey, PbFileUploadInfo pbFileUploadInfo) throws ServiceException {
        pbFileOperateService.uploadPbFile(userKey, pbFileUploadInfo);
    }

    @Override
    public void removePbFile(StringIdKey userKey, LongIdKey pbFileKey) throws ServiceException {
        pbFileOperateService.removePbFile(userKey, pbFileKey);
    }
}
