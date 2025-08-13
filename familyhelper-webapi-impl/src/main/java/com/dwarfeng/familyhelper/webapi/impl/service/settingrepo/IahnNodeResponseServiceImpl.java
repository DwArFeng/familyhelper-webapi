package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.*;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNode;
import com.dwarfeng.settingrepo.stack.service.IahnNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.IahnNodeOperateService;
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
public class IahnNodeResponseServiceImpl implements IahnNodeResponseService {

    private final IahnNodeMaintainService iahnNodeMaintainService;
    private final IahnNodeOperateService iahnNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public IahnNodeResponseServiceImpl(
            @Qualifier("settingrepoIahnNodeMaintainService") IahnNodeMaintainService iahnNodeMaintainService,
            @Qualifier("settingrepoIahnNodeOperateService") IahnNodeOperateService iahnNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.iahnNodeMaintainService = iahnNodeMaintainService;
        this.iahnNodeOperateService = iahnNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return iahnNodeMaintainService.exists(key);
    }

    @Override
    public IahnNode get(StringIdKey key) throws ServiceException {
        return iahnNodeMaintainService.get(key);
    }

    @Override
    public PagedData<IahnNode> all(PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public IahnNodeMessageInspectResult inspectMessage(IahnNodeMessageInspectInfo info) throws ServiceException {
        return iahnNodeOperateService.inspectMessage(info);
    }

    @Override
    public IahnNodeMessageInspectByLocaleResult batchInspectMessageByLocale(IahnNodeMessageInspectByLocaleInfo info)
            throws ServiceException {
        return iahnNodeOperateService.batchInspectMessageByLocale(info);
    }

    @Override
    public IahnNodeLocaleListInspectResult inspectLocaleList(IahnNodeLocaleListInspectInfo info)
            throws ServiceException {
        return iahnNodeOperateService.inspectLocaleList(info);
    }

    @Override
    public IahnNodeMekListInspectResult inspectMekList(IahnNodeMekListInspectInfo info) throws ServiceException {
        return iahnNodeOperateService.inspectMekList(info);
    }

    @Override
    public IahnNodeMessageTableInspectResult inspectMessageTable(IahnNodeMessageTableInspectInfo info)
            throws ServiceException {
        return iahnNodeOperateService.inspectMessageTable(info);
    }

    @Override
    public void putLocale(IahnNodeLocalePutInfo info) throws ServiceException {
        iahnNodeOperateService.putLocale(info);
    }

    @Override
    public void removeLocale(IahnNodeLocaleRemoveInfo info) throws ServiceException {
        iahnNodeOperateService.removeLocale(info);
    }

    @Override
    public void putMek(IahnNodeMekPutInfo info) throws ServiceException {
        iahnNodeOperateService.putMek(info);
    }

    @Override
    public void removeMek(IahnNodeMekRemoveInfo info) throws ServiceException {
        iahnNodeOperateService.removeMek(info);
    }

    @Override
    public void upsertMessage(IahnNodeMessageUpsertInfo info) throws ServiceException {
        iahnNodeOperateService.upsertMessage(info);
    }

    @Override
    public void batchUpsertMessageByLocale(IahnNodeMessageUpsertByLocaleInfo info) throws ServiceException {
        iahnNodeOperateService.batchUpsertMessageByLocale(info);
    }

    @Override
    public void batchUpsertMessageByMek(IahnNodeMessageUpsertByMekInfo info) throws ServiceException {
        iahnNodeOperateService.batchUpsertMessageByMek(info);
    }

    @Override
    public IahnNodeMessageInspectResult inspectMessageForPublic(PublicIahnNodeMessageInspectInfo info)
            throws ServiceException {
        try {
            IahnNodeMessageInspectInfo originalInfo = new IahnNodeMessageInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(),
                    info.getLanguage(), info.getCountry(), info.getVariant(), info.getMekId()
            );
            return iahnNodeOperateService.inspectMessage(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("检查公共国际化节点的消息时发生异常", LogLevel.WARN, e, sem);

        }
    }

    @Override
    public IahnNodeMessageInspectByLocaleResult batchInspectMessageByLocaleForPublic(
            PublicIahnNodeMessageInspectByLocaleInfo info
    ) throws ServiceException {
        try {
            IahnNodeMessageInspectByLocaleInfo originalInfo = new IahnNodeMessageInspectByLocaleInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs(),
                    info.getLanguage(), info.getCountry(), info.getVariant()
            );
            return iahnNodeOperateService.batchInspectMessageByLocale(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse(
                    "批量检查指定本地化的公共国际化节点消息时发生异常", LogLevel.WARN, e, sem
            );
        }
    }

    @Override
    public IahnNodeLocaleListInspectResult inspectLocaleListForPublic(PublicIahnNodeLocaleListInspectInfo info)
            throws ServiceException {
        try {
            IahnNodeLocaleListInspectInfo originalInfo = new IahnNodeLocaleListInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return iahnNodeOperateService.inspectLocaleList(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("检查公共国际化节点的本地化列表时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public IahnNodeMekListInspectResult inspectMekListForPublic(PublicIahnNodeMekListInspectInfo info)
            throws ServiceException {
        try {
            IahnNodeMekListInspectInfo originalInfo = new IahnNodeMekListInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return iahnNodeOperateService.inspectMekList(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("检查公共国际化节点的 Mek 列表时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public IahnNodeMessageTableInspectResult inspectMessageTableForPublic(PublicIahnNodeMessageTableInspectInfo info)
            throws ServiceException {
        try {
            IahnNodeMessageTableInspectInfo originalInfo = new IahnNodeMessageTableInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return iahnNodeOperateService.inspectMessageTable(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("检查公共国际化节点的消息表时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
