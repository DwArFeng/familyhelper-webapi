package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.util.Constants;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateCreateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificatePermissionRemoveInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificatePermissionUpsertInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.CertificateUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Certificate;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Poce;
import com.dwarfeng.familyhelper.clannad.stack.service.CertificateMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.CertificateOperateService;
import com.dwarfeng.familyhelper.clannad.stack.service.PoceMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.CertificateResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CertificateResponseServiceImpl implements CertificateResponseService {

    private final CertificateMaintainService certificateMaintainService;
    private final PoceMaintainService poceMaintainService;
    private final CertificateOperateService certificateOperateService;

    private final AccountResponseService accountResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public CertificateResponseServiceImpl(
            @Qualifier("familyhelperClannadCertificateMaintainService")
            CertificateMaintainService certificateMaintainService,
            @Qualifier("familyhelperClannadPoceMaintainService")
            PoceMaintainService poceMaintainService,
            @Qualifier("familyhelperClannadCertificateOperateService")
            CertificateOperateService certificateOperateService,
            AccountResponseService accountResponseService
    ) {
        this.certificateMaintainService = certificateMaintainService;
        this.poceMaintainService = poceMaintainService;
        this.certificateOperateService = certificateOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return certificateMaintainService.exists(key);
    }

    @Override
    public Certificate get(LongIdKey key) throws ServiceException {
        return certificateMaintainService.get(key);
    }

    @Override
    public PagedData<Certificate> all(PagingInfo pagingInfo) throws ServiceException {
        return certificateMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispCertificate getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Certificate certificate = certificateMaintainService.get(key);
        return dispCertificateFromCertificate(certificate, inspectAccountKey);
    }

    private DispCertificate dispCertificateFromCertificate(Certificate certificate, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<Poce> relatedPoces = poceMaintainService.lookup(
                PoceMaintainService.CHILD_FOR_CERTIFICATE, new Object[]{certificate.getKey()}
        ).getData();
        Poce ownerPoce = relatedPoces.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Poce myPoce = relatedPoces.stream().filter(
                p -> Objects.equals(p.getKey().getUserId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoce)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPoce.getKey().getUserId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPoce).map(Poce::getPermissionLevel).orElse(null);
        return DispCertificate.of(certificate, ownerAccount, permissionLevel);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispCertificate> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poce> lookup = poceMaintainService.lookup(
                PoceMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispCertificate> dispCertificates = new ArrayList<>();
        for (Poce poce : lookup.getData()) {
            dispCertificates.add(dispCertificateFromPoce(poce, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispCertificates
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispCertificate> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poce> lookup = poceMaintainService.lookup(
                PoceMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<DispCertificate> dispCertificates = new ArrayList<>();
        for (Poce poce : lookup.getData()) {
            dispCertificates.add(dispCertificateFromPoce(poce, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispCertificates
        );
    }

    private DispCertificate dispCertificateFromPoce(Poce poce, StringIdKey inspectAccountKey) throws ServiceException {
        Certificate certificate = certificateMaintainService.get(new LongIdKey(poce.getKey().getCertificateId()));
        List<Poce> relatedPoces = poceMaintainService.lookup(
                PoceMaintainService.CHILD_FOR_CERTIFICATE, new Object[]{certificate.getKey()}
        ).getData();
        Poce ownerPoce = relatedPoces.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoce)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPoce.getKey().getUserId()), inspectAccountKey
            );
        }
        Integer permissionLevel = poce.getPermissionLevel();
        return DispCertificate.of(certificate, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createCertificate(StringIdKey userKey, CertificateCreateInfo certificateCreateInfo)
            throws ServiceException {
        return certificateOperateService.createCertificate(userKey, certificateCreateInfo);
    }

    @Override
    public void updateCertificate(StringIdKey userKey, CertificateUpdateInfo certificateUpdateInfo)
            throws ServiceException {
        certificateOperateService.updateCertificate(userKey, certificateUpdateInfo);
    }

    @Override
    public void removeCertificate(StringIdKey userKey, LongIdKey certificateKey) throws ServiceException {
        certificateOperateService.removeCertificate(userKey, certificateKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, CertificatePermissionUpsertInfo certificatePermissionUpsertInfo)
            throws ServiceException {
        certificateOperateService.upsertPermission(userKey, certificatePermissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, CertificatePermissionRemoveInfo certificatePermissionRemoveInfo)
            throws ServiceException {
        certificateOperateService.removePermission(userKey, certificatePermissionRemoveInfo);
    }
}
