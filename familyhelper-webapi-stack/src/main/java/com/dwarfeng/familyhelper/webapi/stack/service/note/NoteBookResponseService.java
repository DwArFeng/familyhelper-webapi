package com.dwarfeng.familyhelper.webapi.stack.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.dto.*;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 笔记本响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface NoteBookResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    NoteBook get(LongIdKey key) throws ServiceException;

    PagedData<NoteBook> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<NoteBook> userOwned(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<NoteBook> userPermittedWithConditionDisplay(
            StringIdKey accountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<NoteBook> userOwnedWithConditionDisplay(
            StringIdKey accountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException;

    DispNoteBook getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispNoteBook> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispNoteBook> userOwnedDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispNoteBook> userPermittedWithConditionDisplayDisp(
            StringIdKey inspectAccountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispNoteBook> userOwnedWithConditionDisplayDisp(
            StringIdKey inspectAccountKey, String pattern, boolean onlyFavored, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createNoteBook(StringIdKey userKey, NoteBookCreateInfo noteBookCreateInfo)
            throws ServiceException;

    void updateNoteBook(StringIdKey userKey, NoteBookUpdateInfo noteBookUpdateInfo) throws ServiceException;

    void removeNoteBook(StringIdKey userKey, LongIdKey noteBookKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, NoteBookPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException;

    void removePermission(StringIdKey userKey, NoteBookPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException;

    void changeFavored(StringIdKey operateUserKey, NoteBookFavoredChangeInfo info) throws ServiceException;
}
