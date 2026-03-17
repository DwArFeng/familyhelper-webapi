package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicNavigationNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicNavigationNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.NavigationNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.NavigationNode;
import com.dwarfeng.settingrepo.stack.service.NavigationNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.NavigationNodeOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 导航节点响应服务实现。
 *
 * @author DwArFeng
 * @since 1.8.2
 */
@Service
public class NavigationNodeResponseImpl implements NavigationNodeResponseService {

    private final NavigationNodeMaintainService navigationNodeMaintainService;
    private final NavigationNodeOperateService navigationNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public NavigationNodeResponseImpl(
            @Qualifier("settingrepoNavigationNodeMaintainService")
            NavigationNodeMaintainService navigationNodeMaintainService,
            @Qualifier("settingrepoNavigationNodeOperateService")
            NavigationNodeOperateService navigationNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.navigationNodeMaintainService = navigationNodeMaintainService;
        this.navigationNodeOperateService = navigationNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return navigationNodeMaintainService.exists(key);
    }

    @Override
    public NavigationNode get(StringIdKey key) throws ServiceException {
        return navigationNodeMaintainService.get(key);
    }

    @Override
    public PagedData<NavigationNode> all(PagingInfo pagingInfo) throws ServiceException {
        return navigationNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public NavigationNodeSizeResult size(NavigationNodeSizeInfo info) throws ServiceException {
        return navigationNodeOperateService.size(info);
    }

    @Override
    public NavigationNodeInspectResult inspect(NavigationNodeInspectInfo info) throws ServiceException {
        return navigationNodeOperateService.inspect(info);
    }

    @Override
    public void updateNode(NavigationNodeUpdateInfo info) throws ServiceException {
        navigationNodeOperateService.updateNode(info);
    }

    @Override
    public NavigationNodeItemInsertResult insertItem(NavigationNodeItemInsertInfo info) throws ServiceException {
        return navigationNodeOperateService.insertItem(info);
    }

    @Override
    public void updateItem(NavigationNodeItemUpdateInfo info) throws ServiceException {
        navigationNodeOperateService.updateItem(info);
    }

    @Override
    public void removeItem(NavigationNodeItemRemoveInfo info) throws ServiceException {
        navigationNodeOperateService.removeItem(info);
    }

    @Override
    public void formatIndex(NavigationNodeFormatIndexInfo info) throws ServiceException {
        navigationNodeOperateService.formatIndex(info);
    }

    @Override
    public NavigationNodeSizeResult sizeForPublic(PublicNavigationNodeSizeInfo info) throws ServiceException {
        try {
            NavigationNodeSizeInfo originalInfo = new NavigationNodeSizeInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return navigationNodeOperateService.size(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("公共导航节点的大小时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public NavigationNodeInspectResult inspectForPublic(PublicNavigationNodeInspectInfo info) throws ServiceException {
        try {
            NavigationNodeInspectInfo originalInfo = new NavigationNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return navigationNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看指定的公共导航节点时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
