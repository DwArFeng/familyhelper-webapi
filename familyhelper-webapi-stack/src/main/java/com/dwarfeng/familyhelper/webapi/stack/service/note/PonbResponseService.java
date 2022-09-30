package com.dwarfeng.familyhelper.webapi.stack.service.note;

import com.dwarfeng.familyhelper.note.stack.bean.entity.Ponb;
import com.dwarfeng.familyhelper.note.stack.bean.key.PonbKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispPonb;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 笔记本权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PonbResponseService extends Service {

    boolean exists(PonbKey key) throws ServiceException;

    Ponb get(PonbKey key) throws ServiceException;

    DispPonb getDisp(PonbKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<Ponb> childForNoteBook(LongIdKey noteBookKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPonb> childForNoteBookDisp(
            LongIdKey noteBookKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;
}
