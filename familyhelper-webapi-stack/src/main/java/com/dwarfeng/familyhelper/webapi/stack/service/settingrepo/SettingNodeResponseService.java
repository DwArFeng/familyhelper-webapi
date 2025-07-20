package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicSettingNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeInitInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeRemoveInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.SettingNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 设置节点响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface SettingNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    SettingNode get(StringIdKey key) throws ServiceException;

    PagedData<SettingNode> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<SettingNode> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    /**
     * @since 1.3.0
     */
    PagedData<SettingNode> reachable(PagingInfo pagingInfo) throws ServiceException;

    /**
     * @since 1.3.0
     */
    PagedData<SettingNode> idLikeReachable(String pattern, PagingInfo pagingInfo) throws ServiceException;

    /**
     * @since 1.3.0
     */
    SettingNodeInspectResult inspect(SettingNodeInspectInfo settingNodeInspectInfo) throws ServiceException;

    /**
     * @since 1.3.0
     */
    void init(SettingNodeInitInfo info) throws ServiceException;

    void remove(SettingNodeRemoveInfo settingNodeRemoveInfo) throws ServiceException;

    /**
     * 查看指定的公共设置节点。
     *
     * @param info 公共查看信息。
     * @return 指定的设置节点查看结果
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    SettingNodeInspectResult inspectForPublic(PublicSettingNodeInspectInfo info) throws ServiceException;
}
