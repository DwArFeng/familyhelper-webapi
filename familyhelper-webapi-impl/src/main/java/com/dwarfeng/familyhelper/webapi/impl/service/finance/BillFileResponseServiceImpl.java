package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.BillFile;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BillFileUploadInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BillFileInfo;
import com.dwarfeng.familyhelper.finance.stack.service.BillFileInfoMaintainService;
import com.dwarfeng.familyhelper.finance.stack.service.BillFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BillFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BillFileResponseServiceImpl implements BillFileResponseService {

    private final BillFileInfoMaintainService billFileInfoMaintainService;
    private final BillFileOperateService billFileOperateService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public BillFileResponseServiceImpl(
            @Qualifier("familyhelperFinanceBillFileInfoMaintainService")
            BillFileInfoMaintainService billFileInfoMaintainService,
            @Qualifier("familyhelperFinanceBillFileOperateService")
            BillFileOperateService billFileOperateService
    ) {
        this.billFileInfoMaintainService = billFileInfoMaintainService;
        this.billFileOperateService = billFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return billFileInfoMaintainService.exists(key);
    }

    @Override
    public BillFileInfo get(LongIdKey key) throws ServiceException {
        return billFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<BillFileInfo> childForFundChange(LongIdKey fundChangeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return billFileInfoMaintainService.lookup(
                BillFileInfoMaintainService.CHILD_FOR_FUND_CHANGE, new Object[]{fundChangeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<BillFileInfo> childForFundChangeIndexAsc(LongIdKey fundChangeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return billFileInfoMaintainService.lookup(
                BillFileInfoMaintainService.CHILD_FOR_FUND_CHANGE_INDEX_ASC, new Object[]{fundChangeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<BillFileInfo> childForFundChangeIndexDesc(LongIdKey fundChangeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return billFileInfoMaintainService.lookup(
                BillFileInfoMaintainService.CHILD_FOR_FUND_CHANGE_INDEX_DESC, new Object[]{fundChangeKey}, pagingInfo
        );
    }

    @Override
    public BillFile downloadBillFile(StringIdKey userKey, LongIdKey billFileKey) throws ServiceException {
        return billFileOperateService.downloadBillFile(userKey, billFileKey);
    }

    @Override
    public void uploadBillFile(StringIdKey userKey, BillFileUploadInfo billFileUploadInfo) throws ServiceException {
        billFileOperateService.uploadBillFile(userKey, billFileUploadInfo);
    }

    @Override
    public void removeBillFile(StringIdKey userKey, LongIdKey billFileKey) throws ServiceException {
        billFileOperateService.removeBillFile(userKey, billFileKey);
    }
}
