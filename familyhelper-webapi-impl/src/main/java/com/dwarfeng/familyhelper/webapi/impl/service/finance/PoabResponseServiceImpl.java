package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.Poab;
import com.dwarfeng.familyhelper.finance.stack.bean.key.PoabKey;
import com.dwarfeng.familyhelper.finance.stack.service.PoabMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispPoab;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.AccountBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.PoabResponseService;
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
public class PoabResponseServiceImpl implements PoabResponseService {

    private final PoabMaintainService poabMaintainService;

    private final AccountBookResponseService accountBookResponseService;
    private final AccountResponseService accountResponseService;

    public PoabResponseServiceImpl(
            @Qualifier("familyhelperFinancePoabMaintainService") PoabMaintainService poabMaintainService,
            AccountBookResponseService accountBookResponseService,
            AccountResponseService accountResponseService
    ) {
        this.poabMaintainService = poabMaintainService;
        this.accountBookResponseService = accountBookResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PoabKey key) throws ServiceException {
        return poabMaintainService.exists(key);
    }

    @Override
    public Poab get(PoabKey key) throws ServiceException {
        return poabMaintainService.get(key);
    }

    @Override
    public DispPoab getDisp(PoabKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Poab poab = poabMaintainService.get(key);
        return dispPoabFromPoab(poab, inspectAccountKey);
    }

    @Override
    public PagedData<Poab> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return poabMaintainService.lookup(
                PoabMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPoab> childForAccountBookDisp(
            LongIdKey accountBookKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Poab> lookup = poabMaintainService.lookup(
                PoabMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}, pagingInfo
        );
        List<DispPoab> dispPoabs = new ArrayList<>();
        for (Poab poab : lookup.getData()) {
            dispPoabs.add(dispPoabFromPoab(poab, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPoabs
        );
    }

    private DispPoab dispPoabFromPoab(Poab poab, StringIdKey inspectAccountKey) throws ServiceException {
        DispAccountBook accountBook = accountBookResponseService.getDisp(
                new LongIdKey(poab.getKey().getLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(poab.getKey().getStringId()), inspectAccountKey
        );
        return DispPoab.of(poab, accountBook, account);
    }
}
