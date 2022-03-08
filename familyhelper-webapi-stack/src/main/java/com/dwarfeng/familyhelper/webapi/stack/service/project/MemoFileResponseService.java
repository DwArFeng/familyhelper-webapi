package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoFile;
import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoFileUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.MemoFileUploadInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.MemoFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 备忘录文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
public interface MemoFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    MemoFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<MemoFileInfo> childForMemo(LongIdKey memoKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<MemoFileInfo> childForMemoInspectedDateDesc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<MemoFileInfo> childForMemoModifiedDateDesc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<MemoFileInfo> childForMemoOriginNameAsc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<MemoFileInfo> childForMemoCreatedDateAsc(LongIdKey memoKey, PagingInfo pagingInfo)
            throws ServiceException;

    MemoFile downloadMemoFile(StringIdKey userKey, LongIdKey memoFileKey) throws ServiceException;

    void uploadMemoFile(StringIdKey userKey, MemoFileUploadInfo memoFileUploadInfo) throws ServiceException;

    void updateMemoFile(StringIdKey userKey, MemoFileUpdateInfo memoFileUpdateInfo) throws ServiceException;

    void removeMemoFile(StringIdKey userKey, LongIdKey memoFileKey) throws ServiceException;
}
