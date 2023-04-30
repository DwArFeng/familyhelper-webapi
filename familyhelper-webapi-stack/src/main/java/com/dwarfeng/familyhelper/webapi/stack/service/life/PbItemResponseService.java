package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbItemCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbItemUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbItem;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 个人最佳项目响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PbItemResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    PbItem get(LongIdKey key) throws ServiceException;

    PagedData<PbItem> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PbItem> childForPbNode(LongIdKey pbNodeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PbItem> childForPbSetRoot(LongIdKey pbSetKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PbItem> childForPbSetNameLike(LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    DispPbItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispPbItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPbItem> childForPbNodeDisp(
            StringIdKey accountKey, LongIdKey pbNodeKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPbItem> childForPbSetRootDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPbItem> childForPbSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createPbItem(StringIdKey userKey, PbItemCreateInfo pbItemCreateInfo)
            throws ServiceException;

    void updatePbItem(StringIdKey userKey, PbItemUpdateInfo pbItemUpdateInfo) throws ServiceException;

    void removePbItem(StringIdKey userKey, LongIdKey pbItemKey) throws ServiceException;

    /**
     * 获取指定的个人最佳项目从根节点到该个人最佳所属父节点的路径。
     *
     * @param key 指定的个人最佳项目的主键。
     * @return 指定的个人最佳项目从根节点到该个人最佳所属父节点的路径。
     * @throws ServiceException 服务异常。
     */
    List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException;
}
