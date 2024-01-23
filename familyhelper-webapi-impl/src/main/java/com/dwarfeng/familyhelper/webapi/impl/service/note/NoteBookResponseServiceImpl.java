package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.sdk.util.Constants;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteBookCreateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteBookPermissionRemoveInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteBookPermissionUpsertInfo;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteBookUpdateInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteBook;
import com.dwarfeng.familyhelper.note.stack.bean.entity.Ponb;
import com.dwarfeng.familyhelper.note.stack.service.NoteBookMaintainService;
import com.dwarfeng.familyhelper.note.stack.service.NoteBookOperateService;
import com.dwarfeng.familyhelper.note.stack.service.PonbMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteBookResponseService;
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
public class NoteBookResponseServiceImpl implements NoteBookResponseService {

    private final NoteBookMaintainService noteBookMaintainService;
    private final PonbMaintainService ponbMaintainService;
    private final NoteBookOperateService noteBookOperateService;

    private final AccountResponseService accountResponseService;

    public NoteBookResponseServiceImpl(
            @Qualifier("familyhelperNoteNoteBookMaintainService") NoteBookMaintainService noteBookMaintainService,
            @Qualifier("familyhelperNotePonbMaintainService") PonbMaintainService ponbMaintainService,
            @Qualifier("familyhelperNoteNoteBookOperateService") NoteBookOperateService noteBookOperateService,
            AccountResponseService accountResponseService
    ) {
        this.noteBookMaintainService = noteBookMaintainService;
        this.ponbMaintainService = ponbMaintainService;
        this.noteBookOperateService = noteBookOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return noteBookMaintainService.exists(key);
    }

    @Override
    public NoteBook get(LongIdKey key) throws ServiceException {
        return noteBookMaintainService.get(key);
    }

    @Override
    public PagedData<NoteBook> all(PagingInfo pagingInfo) throws ServiceException {
        return noteBookMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispNoteBook getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        NoteBook noteBook = noteBookMaintainService.get(key);
        return dispNoteBookFromNoteBook(noteBook, inspectAccountKey);
    }

    private DispNoteBook dispNoteBookFromNoteBook(NoteBook noteBook, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<Ponb> relatedPonbs = ponbMaintainService.lookup(
                PonbMaintainService.CHILD_FOR_NOTE_BOOK, new Object[]{noteBook.getKey()}
        ).getData();
        Ponb ownerPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Ponb myPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPonb)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPonb.getKey().getUserStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPonb).map(Ponb::getPermissionLevel).orElse(null);
        return DispNoteBook.of(noteBook, ownerAccount, permissionLevel);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispNoteBook> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Ponb> lookup = ponbMaintainService.lookup(
                PonbMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispNoteBook> dispNoteBooks = new ArrayList<>();
        for (Ponb ponb : lookup.getData()) {
            dispNoteBooks.add(dispNoteBookFromPonb(ponb, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteBooks
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispNoteBook> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Ponb> lookup = ponbMaintainService.lookup(
                PonbMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<DispNoteBook> dispNoteBooks = new ArrayList<>();
        for (Ponb ponb : lookup.getData()) {
            dispNoteBooks.add(dispNoteBookFromPonb(ponb, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispNoteBooks
        );
    }

    private DispNoteBook dispNoteBookFromPonb(Ponb ponb, StringIdKey inspectAccountKey) throws ServiceException {
        NoteBook noteBook = noteBookMaintainService.get(new LongIdKey(ponb.getKey().getNoteBookLongId()));
        List<Ponb> relatedPonbs = ponbMaintainService.lookup(
                PonbMaintainService.CHILD_FOR_NOTE_BOOK, new Object[]{noteBook.getKey()}
        ).getData();
        Ponb ownerPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPonb)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPonb.getKey().getUserStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = ponb.getPermissionLevel();
        return DispNoteBook.of(noteBook, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createNoteBook(StringIdKey userKey, NoteBookCreateInfo noteBookCreateInfo)
            throws ServiceException {
        return noteBookOperateService.createNoteBook(userKey, noteBookCreateInfo);
    }

    @Override
    public void updateNoteBook(StringIdKey userKey, NoteBookUpdateInfo noteBookUpdateInfo)
            throws ServiceException {
        noteBookOperateService.updateNoteBook(userKey, noteBookUpdateInfo);
    }

    @Override
    public void removeNoteBook(StringIdKey userKey, LongIdKey noteBookKey) throws ServiceException {
        noteBookOperateService.removeNoteBook(userKey, noteBookKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, NoteBookPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException {
        noteBookOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, NoteBookPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException {
        noteBookOperateService.removePermission(userKey, permissionRemoveInfo);
    }
}
