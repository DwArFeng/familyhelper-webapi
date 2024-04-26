package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.dto.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.CertificateFileInfo;
import com.dwarfeng.familyhelper.clannad.stack.service.CertificateFileInfoMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.CertificateFileOperateService;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestCertificateFileStream;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestCertificateFileStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.clannad.bean.dto.DubboRestCertificateFileStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.clannad.service.DubboRestCertificateFileOperateService;
import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
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
    private final DubboRestCertificateFileOperateService dubboRestCertificateFileOperateService;

    public CertificateFileResponseServiceImpl(
            @Qualifier("familyhelperClannadCertificateFileInfoMaintainService")
            CertificateFileInfoMaintainService certificateFileInfoMaintainService,
            @Qualifier("familyhelperClannadCertificateFileOperateService")
            CertificateFileOperateService certificateFileOperateService,
            @Qualifier("familyhelperPluginClannadDubboRestCertificateFileOperateService")
            DubboRestCertificateFileOperateService dubboRestCertificateFileOperateService
    ) {
        this.certificateFileInfoMaintainService = certificateFileInfoMaintainService;
        this.certificateFileOperateService = certificateFileOperateService;
        this.dubboRestCertificateFileOperateService = dubboRestCertificateFileOperateService;
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
    public LongIdKey requestCertificateFileStreamVoucher(StringIdKey userKey, LongIdKey certificateFileKey)
            throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestCertificateFileOperateService.requestCertificateFileStreamVoucher(
                new DubboRestCertificateFileStreamDownloadInfo(userKey.getStringId(), certificateFileKey.getLongId())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public CertificateFileStream downloadCertificateFileStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestCertificateFileStream dubboRestCertificateFileStream =
                dubboRestCertificateFileOperateService.downloadCertificateFileStreamByVoucher(voucherIdWrapper);
        return new CertificateFileStream(
                dubboRestCertificateFileStream.getOriginName(), dubboRestCertificateFileStream.getLength(),
                dubboRestCertificateFileStream.getContent()
        );
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
    public void uploadCertificateFileStream(
            StringIdKey userKey, CertificateFileStreamUploadInfo certificateFileStreamUploadInfo
    ) throws ServiceException {
        dubboRestCertificateFileOperateService.uploadCertificateFileStream(new DubboRestCertificateFileStreamUploadInfo(
                userKey.getStringId(), certificateFileStreamUploadInfo.getCertificateKey().getLongId(),
                certificateFileStreamUploadInfo.getOriginName(), certificateFileStreamUploadInfo.getLength(),
                certificateFileStreamUploadInfo.getContent()
        ));
    }

    @Override
    public void removeCertificateFile(StringIdKey userKey, LongIdKey certificateFileKey) throws ServiceException {
        certificateFileOperateService.removeCertificateFile(userKey, certificateFileKey);
    }
}
