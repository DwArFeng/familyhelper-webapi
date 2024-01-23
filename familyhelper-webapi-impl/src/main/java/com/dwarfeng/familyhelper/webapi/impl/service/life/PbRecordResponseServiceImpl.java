package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbRecordCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbRecordUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbRecord;
import com.dwarfeng.familyhelper.life.stack.service.PbRecordMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbRecordOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbRecordResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PbRecordResponseServiceImpl implements PbRecordResponseService {

    private final PbRecordMaintainService pbRecordMaintainService;
    private final PbRecordOperateService pbRecordOperateService;

    public PbRecordResponseServiceImpl(
            @Qualifier("familyhelperLifePbRecordMaintainService") PbRecordMaintainService pbRecordMaintainService,
            @Qualifier("familyhelperLifePbRecordOperateService") PbRecordOperateService pbRecordOperateService
    ) {
        this.pbRecordMaintainService = pbRecordMaintainService;
        this.pbRecordOperateService = pbRecordOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return pbRecordMaintainService.exists(key);
    }

    @Override
    public PbRecord get(LongIdKey key) throws ServiceException {
        return pbRecordMaintainService.get(key);
    }

    @Override
    public PagedData<PbRecord> all(PagingInfo pagingInfo) throws ServiceException {
        return pbRecordMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<PbRecord> childForPbItemRecordedDateAsc(LongIdKey pbItemKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbRecordMaintainService.lookup(
                PbRecordMaintainService.CHILD_FOR_ITEM_RECORDED_DATE_ASC, new Object[]{pbItemKey}, pagingInfo
        );
    }

    @Override
    public PagedData<PbRecord> childForPbItemRecordedDateDesc(LongIdKey pbItemKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pbRecordMaintainService.lookup(
                PbRecordMaintainService.CHILD_FOR_ITEM_RECORDED_DATE_DESC, new Object[]{pbItemKey}, pagingInfo
        );
    }

    @Override
    public LongIdKey createPbRecord(StringIdKey userKey, PbRecordCreateInfo pbRecordCreateInfo) throws
            ServiceException {
        return pbRecordOperateService.createPbRecord(userKey, pbRecordCreateInfo);
    }

    @Override
    public void updatePbRecord(StringIdKey userKey, PbRecordUpdateInfo pbRecordUpdateInfo) throws ServiceException {
        pbRecordOperateService.updatePbRecord(userKey, pbRecordUpdateInfo);
    }

    @Override
    public void removePbRecord(StringIdKey userKey, LongIdKey pbRecordKey) throws ServiceException {
        pbRecordOperateService.removePbRecord(userKey, pbRecordKey);
    }
}
