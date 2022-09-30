package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.entity.Ponb;
import com.dwarfeng.familyhelper.note.stack.bean.key.PonbKey;
import com.dwarfeng.familyhelper.note.stack.service.PonbMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispPonb;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.note.PonbResponseService;
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
public class PonbResponseServiceImpl implements PonbResponseService {

    private final PonbMaintainService ponbMaintainService;

    private final NoteBookResponseService noteBookResponseService;
    private final AccountResponseService accountResponseService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public PonbResponseServiceImpl(
            @Qualifier("familyhelperNotePonbMaintainService") PonbMaintainService ponbMaintainService,
            NoteBookResponseService noteBookResponseService,
            AccountResponseService accountResponseService
    ) {
        this.ponbMaintainService = ponbMaintainService;
        this.noteBookResponseService = noteBookResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(PonbKey key) throws ServiceException {
        return ponbMaintainService.exists(key);
    }

    @Override
    public Ponb get(PonbKey key) throws ServiceException {
        return ponbMaintainService.get(key);
    }

    @Override
    public DispPonb getDisp(PonbKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Ponb ponb = ponbMaintainService.get(key);
        return dispPonbFromPonb(ponb, inspectAccountKey);
    }

    @Override
    public PagedData<Ponb> childForNoteBook(LongIdKey noteBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return ponbMaintainService.lookup(
                PonbMaintainService.CHILD_FOR_NOTE_BOOK, new Object[]{noteBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<DispPonb> childForNoteBookDisp(
            LongIdKey noteBookKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        PagedData<Ponb> lookup = ponbMaintainService.lookup(
                PonbMaintainService.CHILD_FOR_NOTE_BOOK, new Object[]{noteBookKey}, pagingInfo
        );
        List<DispPonb> dispPonbs = new ArrayList<>();
        for (Ponb ponb : lookup.getData()) {
            dispPonbs.add(dispPonbFromPonb(ponb, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPonbs
        );
    }

    private DispPonb dispPonbFromPonb(Ponb ponb, StringIdKey inspectAccountKey) throws ServiceException {
        DispNoteBook noteBook = noteBookResponseService.getDisp(
                new LongIdKey(ponb.getKey().getNoteBookLongId()), inspectAccountKey
        );
        DispAccount account = accountResponseService.getDisp(
                new StringIdKey(ponb.getKey().getUserStringId()), inspectAccountKey
        );
        return DispPonb.of(ponb, noteBook, account);
    }
}
