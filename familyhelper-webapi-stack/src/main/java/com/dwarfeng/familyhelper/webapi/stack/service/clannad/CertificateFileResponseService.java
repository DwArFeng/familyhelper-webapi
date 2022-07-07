package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateFile;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateFileUploadInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.CertificateFileInfo;
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
public interface CertificateFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    CertificateFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<CertificateFileInfo> childForCertificate(LongIdKey certificateKey, PagingInfo pagingInfo)
            throws ServiceException;

    CertificateFile downloadCertificateFile(StringIdKey userKey, LongIdKey certificateFileKey) throws ServiceException;

    void uploadCertificateFile(StringIdKey userKey, CertificateFileUploadInfo certificateFileUploadInfo)
            throws ServiceException;

    void removeCertificateFile(StringIdKey userKey, LongIdKey certificateFileKey) throws ServiceException;
}
