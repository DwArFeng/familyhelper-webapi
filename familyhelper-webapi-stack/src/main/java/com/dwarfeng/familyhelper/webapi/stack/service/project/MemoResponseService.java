package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Memo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 备忘录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public interface MemoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Memo get(LongIdKey key) throws ServiceException;

    PagedData<Memo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Memo> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Memo> childForUserInProgress(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Memo> childForUserFinished(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Memo> childForUserDefaultOrder(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Memo> childForUserInProgressDefaultOrder(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<Memo> childForUserFinishedDefaultOrder(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<Memo> childForUserProfileLikeDefaultOrder(StringIdKey userKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<Memo> childForUserInProgressProfileLikeDefaultOrder(
            StringIdKey userKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<Memo> childForUserFinishedProfileLikeDefaultOrder(
            StringIdKey userKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createMemo(StringIdKey userKey, MemoCreateInfo memoCreateInfo) throws ServiceException;

    void updateMemo(StringIdKey userKey, MemoUpdateInfo memoUpdateInfo) throws ServiceException;

    void removeMemo(StringIdKey userKey, LongIdKey memoKey) throws ServiceException;

    void finishMemo(StringIdKey userKey, LongIdKey memoKey) throws ServiceException;

    void removeFinishedMemos(StringIdKey userKey) throws ServiceException;
}
