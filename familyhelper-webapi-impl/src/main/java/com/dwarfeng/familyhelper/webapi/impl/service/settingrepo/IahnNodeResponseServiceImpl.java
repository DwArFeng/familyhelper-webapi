package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.sdk.util.Constants;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.*;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNode;
import com.dwarfeng.settingrepo.stack.service.IahnNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.IahnNodeOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IahnNodeResponseServiceImpl implements IahnNodeResponseService {

    private final IahnNodeMaintainService iahnNodeMaintainService;
    private final IahnNodeOperateService iahnNodeOperateService;

    public IahnNodeResponseServiceImpl(
            @Qualifier("settingrepoIahnNodeMaintainService")
            IahnNodeMaintainService iahnNodeMaintainService,
            @Qualifier("settingrepoIahnNodeOperateService")
            IahnNodeOperateService iahnNodeOperateService
    ) {
        this.iahnNodeMaintainService = iahnNodeMaintainService;
        this.iahnNodeOperateService = iahnNodeOperateService;
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
        IahnNodeMessageInspectInfo originalInfo = new IahnNodeMessageInspectInfo(
                Constants.SETTINGREPO_PUBLIC_SETTING_CATEGORY, info.getArgs(),
                info.getLanguage(), info.getCountry(), info.getVariant(), info.getMekId()
        );
        return iahnNodeOperateService.inspectMessage(originalInfo);
    }

    @Override
    public IahnNodeMessageInspectByLocaleResult batchInspectMessageByLocaleForPublic(
            PublicIahnNodeMessageInspectByLocaleInfo info
    ) throws ServiceException {
        IahnNodeMessageInspectByLocaleInfo originalInfo = new IahnNodeMessageInspectByLocaleInfo(
                Constants.SETTINGREPO_PUBLIC_SETTING_CATEGORY, info.getArgs(),
                info.getLanguage(), info.getCountry(), info.getVariant()
        );
        return iahnNodeOperateService.batchInspectMessageByLocale(originalInfo);
    }

    @Override
    public IahnNodeLocaleListInspectResult inspectLocaleListForPublic(PublicIahnNodeLocaleListInspectInfo info)
            throws ServiceException {
        IahnNodeLocaleListInspectInfo originalInfo = new IahnNodeLocaleListInspectInfo(
                Constants.SETTINGREPO_PUBLIC_SETTING_CATEGORY, info.getArgs()
        );
        return iahnNodeOperateService.inspectLocaleList(originalInfo);
    }

    @Override
    public IahnNodeMekListInspectResult inspectMekListForPublic(PublicIahnNodeMekListInspectInfo info)
            throws ServiceException {
        IahnNodeMekListInspectInfo originalInfo = new IahnNodeMekListInspectInfo(
                Constants.SETTINGREPO_PUBLIC_SETTING_CATEGORY, info.getArgs()
        );
        return iahnNodeOperateService.inspectMekList(originalInfo);
    }

    @Override
    public IahnNodeMessageTableInspectResult inspectMessageTableForPublic(PublicIahnNodeMessageTableInspectInfo info)
            throws ServiceException {
        IahnNodeMessageTableInspectInfo originalInfo = new IahnNodeMessageTableInspectInfo(
                Constants.SETTINGREPO_PUBLIC_SETTING_CATEGORY, info.getArgs()
        );
        return iahnNodeOperateService.inspectMessageTable(originalInfo);
    }
}
