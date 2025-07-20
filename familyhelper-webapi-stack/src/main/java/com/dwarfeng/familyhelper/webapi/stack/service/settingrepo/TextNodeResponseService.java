package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicTextNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodePutInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.TextNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 文本节点响应服务。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
public interface TextNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    TextNode get(StringIdKey key) throws ServiceException;

    PagedData<TextNode> all(PagingInfo pagingInfo) throws ServiceException;

    TextNodeInspectResult inspect(TextNodeInspectInfo info) throws ServiceException;

    void put(TextNodePutInfo info) throws ServiceException;

    /**
     * 查看指定的公共文本节点。
     *
     * @param info 公共查看信息。
     * @return 指定的文本节点查看结果
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    TextNodeInspectResult inspectForPublic(PublicTextNodeInspectInfo info) throws ServiceException;
}
