package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.SettingNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodePutInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.SettingNodeRemoveInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.SettingNode;
import com.dwarfeng.settingrepo.stack.service.SettingNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.SettingNodeOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SettingNodeResponseServiceImpl implements SettingNodeResponseService {

    private final SettingNodeMaintainService settingNodeMaintainService;
    private final SettingNodeOperateService settingNodeOperateService;

    public SettingNodeResponseServiceImpl(
            @Qualifier("settingrepoSettingNodeMaintainService") SettingNodeMaintainService settingNodeMaintainService,
            @Qualifier("settingrepoSettingNodeOperateService") SettingNodeOperateService settingNodeOperateService
    ) {
        this.settingNodeMaintainService = settingNodeMaintainService;
        this.settingNodeOperateService = settingNodeOperateService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return settingNodeMaintainService.exists(key);
    }

    @Override
    public SettingNode get(StringIdKey key) throws ServiceException {
        return settingNodeMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(SettingNode settingNode) throws ServiceException {
        return settingNodeMaintainService.insert(settingNode);
    }

    @Override
    public void update(SettingNode settingNode) throws ServiceException {
        settingNodeMaintainService.update(settingNode);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        settingNodeMaintainService.delete(key);
    }

    @Override
    public PagedData<SettingNode> all(PagingInfo pagingInfo) throws ServiceException {
        return settingNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<SettingNode> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return settingNodeMaintainService.lookup(
                SettingNodeMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }

    @Override
    public SettingNode inspect(SettingNodeInspectInfo settingNodeInspectInfo) throws ServiceException {
        return settingNodeOperateService.inspect(settingNodeInspectInfo);
    }

    @Override
    public void put(SettingNodePutInfo settingNodePutInfo) throws ServiceException {
        settingNodeOperateService.put(settingNodePutInfo);
    }

    @Override
    public void remove(SettingNodeRemoveInfo settingNodeRemoveInfo) throws ServiceException {
        settingNodeOperateService.remove(settingNodeRemoveInfo);
    }
}
