package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.BankCardCreateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BankCardUpdateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCard;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardTypeIndicator;
import com.dwarfeng.familyhelper.finance.stack.service.BalanceOperateService;
import com.dwarfeng.familyhelper.finance.stack.service.BankCardMaintainService;
import com.dwarfeng.familyhelper.finance.stack.service.BankCardOperateService;
import com.dwarfeng.familyhelper.finance.stack.service.BankCardTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispBankCard;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.finance.BalanceRecordInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.AccountBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BankCardResponseService;
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

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BankCardResponseServiceImpl implements BankCardResponseService {

    private final BankCardMaintainService bankCardMaintainService;
    private final BankCardOperateService bankCardOperateService;
    private final BankCardTypeIndicatorMaintainService bankCardTypeIndicatorMaintainService;
    private final BalanceOperateService balanceOperateService;

    private final AccountBookResponseService accountBookResponseService;

    public BankCardResponseServiceImpl(
            @Qualifier("familyhelperFinanceBankCardMaintainService")
                    BankCardMaintainService bankCardMaintainService,
            @Qualifier("familyhelperFinanceBankCardOperateService")
                    BankCardOperateService bankCardOperateService,
            @Qualifier("familyhelperFinanceBankCardTypeIndicatorMaintainService")
                    BankCardTypeIndicatorMaintainService bankCardTypeIndicatorMaintainService,
            @Qualifier("familyhelperFinanceBalanceOperateService")
                    BalanceOperateService balanceOperateService,
            AccountBookResponseService accountBookResponseService
    ) {
        this.bankCardMaintainService = bankCardMaintainService;
        this.bankCardOperateService = bankCardOperateService;
        this.bankCardTypeIndicatorMaintainService = bankCardTypeIndicatorMaintainService;
        this.balanceOperateService = balanceOperateService;
        this.accountBookResponseService = accountBookResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return bankCardMaintainService.exists(key);
    }

    @Override
    public BankCard get(LongIdKey key) throws ServiceException {
        return bankCardMaintainService.get(key);
    }

    @Override
    public PagedData<BankCard> all(PagingInfo pagingInfo) throws ServiceException {
        return bankCardMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<BankCard> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return bankCardMaintainService.lookup(
                BankCardMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}
        );
    }

    @Override
    public DispBankCard getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        BankCard bankCard = bankCardMaintainService.get(key);
        return dispBankCardFromBankCard(bankCard, inspectAccountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispBankCard> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<BankCard> lookup = bankCardMaintainService.lookup(pagingInfo);
        List<DispBankCard> dispBankCards = new ArrayList<>();
        for (BankCard bankCard : lookup.getData()) {
            dispBankCards.add(dispBankCardFromBankCard(bankCard, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispBankCards
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispBankCard> childForAccountBookDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<BankCard> lookup = bankCardMaintainService.lookup(
                BankCardMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}
        );
        List<DispBankCard> dispBankCards = new ArrayList<>();
        for (BankCard bankCard : lookup.getData()) {
            dispBankCards.add(dispBankCardFromBankCard(bankCard, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispBankCards
        );
    }

    private DispBankCard dispBankCardFromBankCard(BankCard bankCard, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispAccountBook accountBook = null;
        if (Objects.nonNull(bankCard.getAccountBookKey())) {
            accountBook = accountBookResponseService.getDisp(bankCard.getAccountBookKey(), inspectAccountKey);
        }

        BankCardTypeIndicator typeIndicator = null;
        if (Objects.nonNull(bankCard.getCardType())) {
            typeIndicator = bankCardTypeIndicatorMaintainService.getIfExists(new StringIdKey(bankCard.getCardType()));
        }
        return DispBankCard.of(bankCard, accountBook, typeIndicator);
    }

    @Override
    public LongIdKey createBankCard(
            StringIdKey userKey, LongIdKey accountBookKey, BankCardCreateInfo bankCardCreateInfo
    ) throws ServiceException {
        return bankCardOperateService.createBankCard(userKey, accountBookKey, bankCardCreateInfo);
    }

    @Override
    public void updateBankCard(
            StringIdKey userKey, LongIdKey bankCardKey, BankCardUpdateInfo bankCardUpdateInfo
    ) throws ServiceException {
        bankCardOperateService.updateBankCard(userKey, bankCardKey, bankCardUpdateInfo);
    }

    @Override
    public void removeBankCard(StringIdKey userKey, LongIdKey bankCardKey) throws ServiceException {
        bankCardOperateService.removeBankCard(userKey, bankCardKey);
    }

    @Override
    public void recordBalance(StringIdKey userKey, LongIdKey bankCardKey, BalanceRecordInfo balanceRecordInfo)
            throws ServiceException {
        balanceOperateService.recordBankCardBalance(userKey, bankCardKey, balanceRecordInfo.getBalance());
    }

    @Override
    public void rollbackBalance(StringIdKey userKey, LongIdKey bankCardKey) throws ServiceException {
        balanceOperateService.rollbackBankCard(userKey, bankCardKey);
    }
}
