package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.TextListNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 文本列表节点响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface TextListNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    TextListNode get(StringIdKey key) throws ServiceException;

    PagedData<TextListNode> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 文本列表节点的大小。
     *
     * @param info 文本列表节点大小信息。
     * @return 文本列表节点大小结果。
     * @throws ServiceException 服务异常。
     */
    TextListNodeSizeResult size(TextListNodeSizeInfo info) throws ServiceException;

    /**
     * 查看文本列表节点。
     *
     * @param info 文本列表节点查看信息。
     * @return 文本列表节点查看结果。
     * @throws ServiceException 服务异常。
     */
    TextListNodeInspectResult inspect(TextListNodeInspectInfo info) throws ServiceException;

    /**
     * 拉取文本列表节点中的文本。
     *
     * @param info 文本列表节点文本拉取信息。
     * @return 文本列表节点文本，不存在时返回 {@code null}。
     * @throws ServiceException 服务异常。
     */
    TextListNodeTextFetchResult fetchText(TextListNodeTextFetchInfo info) throws ServiceException;

    /**
     * 插入文本列表节点文本。
     *
     * <p>
     * 插入逻辑如下：
     * <ul>
     *     <li>
     *         插入文本列表节点文本时，会对文本列表节点做插入操作。
     *     </li>
     *     <li>
     *         如果插入信息中的索引小于当前文本列表节点的大小，会自动将当前索引后的元素后移一位。
     *     </li>
     *     <li>
     *         如果插入信息中的索引大于当前文本列表节点的大小，会自动填充中间的空缺，
     *         填充的空缺元素的 <code>nullFlag</code> 属性为 <code>true</code>。
     *     </li>
     * </ul>
     *
     * @param info 文本列表节点文本插入信息。
     * @throws ServiceException 服务异常。
     */
    void insertText(TextListNodeTextInsertInfo info) throws ServiceException;

    /**
     * 更新文本列表节点文本。
     *
     * <p>
     * 更新逻辑如下：
     * <ul>
     *     <li>
     *         更新文本列表节点文本时，会对文本列表节点做设置操作。
     *     </li>
     *     <li>
     *         如果更新信息中的索引小于当前文本列表节点的大小，会替换当前索引的元素。
     *     </li>
     *     <li>
     *         如果更新信息中的索引大于当前文本列表节点的大小，会自动填充中间的空缺，
     *         填充的空缺元素的 <code>nullFlag</code> 属性为 <code>true</code>。
     *     </li>
     * </ul>
     *
     * @param info 文本列表节点文本更新信息。
     * @throws ServiceException 服务异常。
     */
    void updateText(TextListNodeTextUpdateInfo info) throws ServiceException;

    /**
     * 变更文本列表节点顺序。
     *
     * @param info 文本列表节点变更顺序信息。
     * @throws ServiceException 服务异常。
     */
    void changeOrder(TextListNodeChangeOrderInfo info) throws ServiceException;

    /**
     * 移除文本列表节点。
     *
     * @param info 文本列表节点移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(TextListNodeRemoveInfo info) throws ServiceException;
}
