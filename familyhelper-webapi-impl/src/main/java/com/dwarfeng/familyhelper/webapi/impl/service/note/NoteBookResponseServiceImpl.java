package com.dwarfeng.familyhelper.webapi.impl.service.note;

import com.dwarfeng.familyhelper.note.sdk.util.Constants;
import com.dwarfeng.familyhelper.note.stack.bean.dto.*;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteBook;
import com.dwarfeng.familyhelper.note.stack.bean.entity.Ponb;
import com.dwarfeng.familyhelper.note.stack.bean.key.FavoriteKey;
import com.dwarfeng.familyhelper.note.stack.service.FavoriteMaintainService;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteBookResponseServiceImpl implements NoteBookResponseService {

    private final NoteBookMaintainService noteBookMaintainService;
    private final PonbMaintainService ponbMaintainService;
    private final NoteBookOperateService noteBookOperateService;
    private final FavoriteMaintainService favoriteMaintainService;

    private final AccountResponseService accountResponseService;

    public NoteBookResponseServiceImpl(
            @Qualifier("familyhelperNoteNoteBookMaintainService") NoteBookMaintainService noteBookMaintainService,
            @Qualifier("familyhelperNotePonbMaintainService") PonbMaintainService ponbMaintainService,
            @Qualifier("familyhelperNoteNoteBookOperateService") NoteBookOperateService noteBookOperateService,
            @Qualifier("familyhelperNoteFavoriteMaintainService") FavoriteMaintainService favoriteMaintainService,
            AccountResponseService accountResponseService
    ) {
        this.noteBookMaintainService = noteBookMaintainService;
        this.ponbMaintainService = ponbMaintainService;
        this.noteBookOperateService = noteBookOperateService;
        this.favoriteMaintainService = favoriteMaintainService;
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
    public PagedData<NoteBook> userOwned(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        return noteBookMaintainService.lookup(
                NoteBookMaintainService.USER_OWNED, new Object[]{accountKey}, pagingInfo
        );
    }

    @Override
    public PagedData<NoteBook> userPermittedWithConditionDisplay(
            StringIdKey accountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException {
        return noteBookMaintainService.lookup(
                NoteBookMaintainService.USER_PERMITTED_WITH_CONDITION_DISPLAY,
                new Object[]{accountKey, pattern, onlyFavored},
                pagingInfo
        );
    }

    @Override
    public PagedData<NoteBook> userOwnedWithConditionDisplay(
            StringIdKey accountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException {
        return noteBookMaintainService.lookup(
                NoteBookMaintainService.USER_OWNED_WITH_CONDITION_DISPLAY,
                new Object[]{accountKey, pattern, onlyFavored},
                pagingInfo
        );
    }

    @Override
    public DispNoteBook getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        NoteBook noteBook = get(key);
        return toDisp(noteBook, inspectAccountKey);
    }

    @Override
    public PagedData<DispNoteBook> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<NoteBook> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispNoteBook> userOwnedDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<NoteBook> lookup = userOwned(inspectAccountKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispNoteBook> userPermittedWithConditionDisplayDisp(
            StringIdKey inspectAccountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteBook> lookup = userPermittedWithConditionDisplay(
                inspectAccountKey, pattern, onlyFavored, pagingInfo
        );
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispNoteBook> userOwnedWithConditionDisplayDisp(
            StringIdKey inspectAccountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<NoteBook> lookup = userOwnedWithConditionDisplay(
                inspectAccountKey, pattern, onlyFavored, pagingInfo
        );
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispNoteBook toDisp(NoteBook noteBook, StringIdKey inspectAccountKey) throws ServiceException {
        List<Ponb> relatedPonbs = ponbMaintainService.lookupAsList(
                PonbMaintainService.CHILD_FOR_NOTE_BOOK, new Object[]{noteBook.getKey()}
        );
        Ponb ownerPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Ponb myPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPonb)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPonb.getKey().getUserStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPonb).map(Ponb::getPermissionLevel).orElse(null);
        boolean favorite = favoriteMaintainService.exists(
                new FavoriteKey(noteBook.getKey().getLongId(), inspectAccountKey.getStringId())
        );
        return DispNoteBook.of(noteBook, ownerAccount, permissionLevel, favorite);
    }

    private PagedData<DispNoteBook> toDispPagedData(PagedData<NoteBook> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispNoteBook> dispNoteBooks = new ArrayList<>();
        Map<StringIdKey, DispAccount> cachedDispAccountMap = new HashMap<>();
        Set<LongIdKey> cachedFavoriteNoteBookKeySet = favoriteMaintainService.lookupAsList(
                FavoriteMaintainService.CHILD_FOR_USER, new Object[]{inspectAccountKey}
        ).stream().map(
                (f) -> new LongIdKey(f.getKey().getNoteBookLongId())
        ).collect(Collectors.toSet());
        for (NoteBook noteBook : lookup.getData()) {
            dispNoteBooks.add(toDispWithCache(
                    noteBook, inspectAccountKey, cachedDispAccountMap, cachedFavoriteNoteBookKeySet
            ));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispNoteBooks
        );
    }

    @SuppressWarnings("DuplicatedCode")
    private DispNoteBook toDispWithCache(
            NoteBook noteBook, StringIdKey inspectAccountKey, Map<StringIdKey, DispAccount> cachedDispAccountMap,
            Set<LongIdKey> cachedFavoriteNoteBookKeySet
    ) throws ServiceException {
        List<Ponb> relatedPonbs = ponbMaintainService.lookupAsList(
                PonbMaintainService.CHILD_FOR_NOTE_BOOK, new Object[]{noteBook.getKey()}
        );
        Ponb ownerPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Ponb myPonb = relatedPonbs.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        StringIdKey ownerAccountKey = Optional.ofNullable(ownerPonb)
                .map((p) -> new StringIdKey(p.getKey().getUserStringId())).orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerAccountKey)) {
            ownerAccount = cachedDispAccountMap.get(ownerAccountKey);
            if (Objects.isNull(ownerAccount)) {
                ownerAccount = accountResponseService.getDisp(ownerAccountKey, inspectAccountKey);
                cachedDispAccountMap.put(ownerAccountKey, ownerAccount);
            }
        }
        Integer permissionLevel = Optional.ofNullable(myPonb).map(Ponb::getPermissionLevel).orElse(null);
        boolean favorite = cachedFavoriteNoteBookKeySet.contains(noteBook.getKey());
        return DispNoteBook.of(noteBook, ownerAccount, permissionLevel, favorite);
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

    @Override
    public void changeFavored(StringIdKey operateUserKey, NoteBookFavoredChangeInfo info) throws ServiceException {
        noteBookOperateService.changeFavored(operateUserKey, info);
    }
}
