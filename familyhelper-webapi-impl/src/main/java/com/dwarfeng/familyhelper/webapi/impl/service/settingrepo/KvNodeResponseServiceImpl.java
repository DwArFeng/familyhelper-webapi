package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicKvNodeCountInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicKvNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicKvNodeItemInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.KvNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.KvNode;
import com.dwarfeng.settingrepo.stack.service.KvNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.KvNodeOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KvNodeResponseServiceImpl implements KvNodeResponseService {

    private final KvNodeMaintainService kvNodeMaintainService;
    private final KvNodeOperateService kvNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public KvNodeResponseServiceImpl(
            @Qualifier("settingrepoKvNodeMaintainService") KvNodeMaintainService kvNodeMaintainService,
            @Qualifier("settingrepoKvNodeOperateService") KvNodeOperateService kvNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.kvNodeMaintainService = kvNodeMaintainService;
        this.kvNodeOperateService = kvNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return kvNodeMaintainService.exists(key);
    }

    @Override
    public KvNode get(StringIdKey key) throws ServiceException {
        return kvNodeMaintainService.get(key);
    }

    @Override
    public PagedData<KvNode> all(PagingInfo pagingInfo) throws ServiceException {
        return kvNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public KvNodeCountResult count(KvNodeCountInfo info) throws ServiceException {
        return kvNodeOperateService.count(info);
    }

    @Override
    public KvNodeInspectResult inspect(KvNodeInspectInfo info) throws ServiceException {
        return kvNodeOperateService.inspect(info);
    }

    @Override
    public KvNodeItemInspectResult inspectItem(KvNodeItemInspectInfo info) throws ServiceException {
        return kvNodeOperateService.inspectItem(info);
    }

    @Override
    public void putItem(KvNodeItemPutInfo info) throws ServiceException {
        kvNodeOperateService.putItem(info);
    }

    @Override
    public void removeItem(KvNodeItemRemoveInfo info) throws ServiceException {
        kvNodeOperateService.removeItem(info);
    }

    @Override
    public void clear(KvNodeClearInfo info) throws ServiceException {
        kvNodeOperateService.clear(info);
    }

    @Override
    public KvNodeCountResult countForPublic(PublicKvNodeCountInfo info) throws ServiceException {
        try {
            KvNodeCountInfo originalInfo = new KvNodeCountInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return kvNodeOperateService.count(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查询公共键值对节点数量时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public KvNodeInspectResult inspectForPublic(PublicKvNodeInspectInfo info) throws ServiceException {
        try {
            KvNodeInspectInfo originalInfo = new KvNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return kvNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看公共键值对节点时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public KvNodeItemInspectResult inspectItemForPublic(PublicKvNodeItemInspectInfo info) throws ServiceException {
        try {
            KvNodeItemInspectInfo originalInfo = new KvNodeItemInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(),
                    info.getItemStringId()
            );
            return kvNodeOperateService.inspectItem(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看公共键值对节点条目时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
