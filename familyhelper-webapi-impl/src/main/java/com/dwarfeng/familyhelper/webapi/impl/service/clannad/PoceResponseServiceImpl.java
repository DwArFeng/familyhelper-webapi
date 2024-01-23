package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Poce;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.PoceKey;
import com.dwarfeng.familyhelper.clannad.stack.service.PoceMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.CertificateResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.PoceResponseService;
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

@Service
public class PoceResponseServiceImpl implements PoceResponseService {

    private final PoceMaintainService poceMaintainService;

    private final CertificateResponseService certificateResponseService;
    private final AccountResponseService accountResponseService;

    public PoceResponseServiceImpl(
            @Qualifier("familyhelperClannadPoceMaintainService") PoceMaintainService poceMaintainService,
            CertificateResponseService certificateResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poceMaintainService = poceMaintainService;
        this.certificateResponseService = certificateResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoceKey key) throws ServiceException {
        return poceMaintainService.exists(key);
    }

    @Override
    public Poce get(PoceKey key) throws ServiceException {
        return poceMaintainService.get(key);
    }

    @Override
    public DispPoce getDisp(PoceKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poce poce = poceMaintainService.get(key);
        return dispPoceFromPoce(poce, inspectAccountKey);
    }

    @Override
    public PagedData<Poce> childForCertificate(LongIdKey certificateKey, PagingInfo pagingInfo)
            throws ServiceException {
        return poceMaintainService.lookup(
                PoceMaintainService.CHILD_FOR_CERTIFICATE, new Object[]{certificateKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPoce> childForCertificateDisp(
            LongIdKey certificateKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poce> lookup = poceMaintainService.lookup(
                PoceMaintainService.CHILD_FOR_CERTIFICATE, new Object[]{certificateKey}, pagingInfo
        );
        List<DispPoce> dispPoces = new ArrayList<>();
        for (Poce poce : lookup.getData()) {
            dispPoces.add(dispPoceFromPoce(poce, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoces
        );
    }

    private DispPoce dispPoceFromPoce(Poce poce, StringIdKey inspectAccountKey) throws ServiceException {
        DispCertificate certificate = certificateResponseService.getDisp(
                new LongIdKey(poce.getKey().getCertificateId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poce.getKey().getUserId()), inspectAccountKey
        );
        return DispPoce.of(poce, certificate, account);
    }
}
