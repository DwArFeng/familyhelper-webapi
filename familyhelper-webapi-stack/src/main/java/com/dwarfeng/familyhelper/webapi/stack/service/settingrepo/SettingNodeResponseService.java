package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodePutInfo;
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

    StringIdKey insert(SettingNode settingNode) throws ServiceException;

    void update(SettingNode settingNode) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<SettingNode> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<SettingNode> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    SettingNode inspect(SettingNodeInspectInfo settingNodeInspectInfo) throws ServiceException;

    void put(SettingNodePutInfo settingNodePutInfo) throws ServiceException;

    void remove(SettingNodeRemoveInfo settingNodeRemoveInfo) throws ServiceException;
}
