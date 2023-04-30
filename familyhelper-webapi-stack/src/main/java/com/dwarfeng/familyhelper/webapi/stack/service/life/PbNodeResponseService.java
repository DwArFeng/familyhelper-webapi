package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.PbNodeCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispPbNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 个人最佳节点响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface PbNodeResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    PbNode get(LongIdKey key) throws ServiceException;

    PagedData<PbNode> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PbNode> childForPbSet(LongIdKey pbSetKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PbNode> childForPbSetRoot(LongIdKey pbSetKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PbNode> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PbNode> childForPbSetNameLike(LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    DispPbNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispPbNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPbNode> childForPbSetDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPbNode> childForPbSetRootDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPbNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPbNode> childForPbSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey pbSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createPbNode(StringIdKey userKey, PbNodeCreateInfo pbNodeCreateInfo)
            throws ServiceException;

    void updatePbNode(StringIdKey userKey, PbNodeUpdateInfo pbNodeUpdateInfo) throws ServiceException;

    void removePbNode(StringIdKey userKey, LongIdKey pbNodeKey) throws ServiceException;

    /**
     * 获取指定的个人最佳节点从根节点到该节点所属父节点的路径。
     *
     * @param key 指定的个人最佳节点的主键。
     * @return 指定的个人最佳节点从根节点到该节点所属父节点的路径。
     * @throws ServiceException 服务异常。
     */
    List<LongIdKey> pathFromRoot(LongIdKey key) throws ServiceException;
}
