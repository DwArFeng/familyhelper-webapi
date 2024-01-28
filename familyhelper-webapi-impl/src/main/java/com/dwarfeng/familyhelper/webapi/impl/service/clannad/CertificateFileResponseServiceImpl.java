package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateFile;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateFileUploadInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateThumbnail;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.CertificateFileInfo;
import com.dwarfeng.familyhelper.clannad.stack.service.CertificateFileInfoMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.CertificateFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.CertificateFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CertificateFileResponseServiceImpl implements CertificateFileResponseService {

    private final CertificateFileInfoMaintainService certificateFileInfoMaintainService;
    private final CertificateFileOperateService certificateFileOperateService;

    public CertificateFileResponseServiceImpl(
            @Qualifier("familyhelperClannadCertificateFileInfoMaintainService")
            CertificateFileInfoMaintainService certificateFileInfoMaintainService,
            @Qualifier("familyhelperClannadCertificateFileOperateService")
            CertificateFileOperateService certificateFileOperateService
    ) {
        this.certificateFileInfoMaintainService = certificateFileInfoMaintainService;
        this.certificateFileOperateService = certificateFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return certificateFileInfoMaintainService.exists(key);
    }

    @Override
    public CertificateFileInfo get(LongIdKey key) throws ServiceException {
        return certificateFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<CertificateFileInfo> childForCertificate(LongIdKey certificateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return certificateFileInfoMaintainService.lookup(
                CertificateFileInfoMaintainService.CHILD_FOR_CERTIFICATE, new Object[]{certificateKey}, pagingInfo
        );
    }

    @Override
    public CertificateFile downloadCertificateFile(StringIdKey userKey, LongIdKey certificateFileKey)
            throws ServiceException {
        return certificateFileOperateService.downloadCertificateFile(userKey, certificateFileKey);
    }

    @Override
    public CertificateThumbnail downloadCertificateThumbnail(StringIdKey userKey, LongIdKey certificateFileKey)
            throws ServiceException {
        return certificateFileOperateService.downloadCertificateThumbnail(userKey, certificateFileKey);
    }

    @Override
    public void uploadCertificateFile(StringIdKey userKey, CertificateFileUploadInfo certificateFileUploadInfo)
            throws ServiceException {
        certificateFileOperateService.uploadCertificateFile(userKey, certificateFileUploadInfo);
    }

    @Override
    public void removeCertificateFile(StringIdKey userKey, LongIdKey certificateFileKey) throws ServiceException {
        certificateFileOperateService.removeCertificateFile(userKey, certificateFileKey);
    }
}
