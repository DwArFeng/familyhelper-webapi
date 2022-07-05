package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.BillFile;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BillFileUploadInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BillFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 票据文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface BillFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    BillFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<BillFileInfo> childForFundChange(LongIdKey fundChangeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<BillFileInfo> childForFundChangeIndexAsc(LongIdKey fundChangeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<BillFileInfo> childForFundChangeIndexDesc(LongIdKey fundChangeKey, PagingInfo pagingInfo)
            throws ServiceException;

    BillFile downloadBillFile(StringIdKey userKey, LongIdKey billFileKey) throws ServiceException;

    void uploadBillFile(StringIdKey userKey, BillFileUploadInfo billFileUploadInfo) throws ServiceException;

    void removeBillFile(StringIdKey userKey, LongIdKey billFileKey) throws ServiceException;
}
