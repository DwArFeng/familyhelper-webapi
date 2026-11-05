package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.plugin.commons.dto.VoucherIdWrapper;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestLongTextNodeTextStream;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestLongTextNodeTextStreamDownloadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.bean.dto.DubboRestLongTextNodeTextStreamUploadInfo;
import com.dwarfeng.familyhelper.plugin.settingrepo.service.DubboRestLongTextNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicLongTextNodeTextDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.LongTextNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.LongTextNode;
import com.dwarfeng.settingrepo.stack.service.LongTextNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.LongTextNodeOperateService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LongTextNodeResponseServiceImpl implements LongTextNodeResponseService {

    private final LongTextNodeMaintainService longTextNodeMaintainService;
    private final LongTextNodeOperateService longTextNodeOperateService;
    private final DubboRestLongTextNodeOperateService dubboRestLongTextNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public LongTextNodeResponseServiceImpl(
            @Qualifier("settingrepoLongTextNodeMaintainService")
            LongTextNodeMaintainService longTextNodeMaintainService,
            @Qualifier("settingrepoLongTextNodeOperateService")
            LongTextNodeOperateService longTextNodeOperateService,
            @Qualifier("familyhelperPluginSettingRepoDubboRestLongTextNodeOperateService")
            DubboRestLongTextNodeOperateService dubboRestLongTextNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.longTextNodeMaintainService = longTextNodeMaintainService;
        this.longTextNodeOperateService = longTextNodeOperateService;
        this.dubboRestLongTextNodeOperateService = dubboRestLongTextNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return longTextNodeMaintainService.exists(key);
    }

    @Override
    public LongTextNode get(StringIdKey key) throws ServiceException {
        return longTextNodeMaintainService.get(key);
    }

    @Override
    public PagedData<LongTextNode> all(PagingInfo pagingInfo) throws ServiceException {
        return longTextNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public LongTextNodeInspectResult inspect(LongTextNodeInspectInfo info) throws ServiceException {
        return longTextNodeOperateService.inspect(info);
    }

    @Override
    public LongTextNodeText downloadText(LongTextNodeTextDownloadInfo info) throws ServiceException {
        return longTextNodeOperateService.downloadText(info);
    }

    @Override
    public LongIdKey requestTextStreamVoucher(LongTextNodeTextDownloadInfo info) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = dubboRestLongTextNodeOperateService.requestTextStreamVoucher(
                new DubboRestLongTextNodeTextStreamDownloadInfo(info.getCategory(), info.getArgs())
        );
        return new LongIdKey(voucherIdWrapper.getVoucherId());
    }

    @Override
    public LongTextNodeTextStream downloadTextStreamByVoucher(LongIdKey voucherKey) throws ServiceException {
        VoucherIdWrapper voucherIdWrapper = new VoucherIdWrapper(voucherKey.getLongId());
        DubboRestLongTextNodeTextStream dubboRestLongTextNodeTextStream =
                dubboRestLongTextNodeOperateService.downloadTextStreamByVoucher(voucherIdWrapper);
        if (Objects.isNull(dubboRestLongTextNodeTextStream)) {
            return null;
        }
        return new LongTextNodeTextStream(
                dubboRestLongTextNodeTextStream.getLength(), dubboRestLongTextNodeTextStream.getContent()
        );
    }

    @Override
    public void uploadText(LongTextNodeTextUploadInfo info) throws ServiceException {
        longTextNodeOperateService.uploadText(info);
    }

    @Override
    public void uploadTextStream(LongTextNodeTextStreamUploadInfo info) throws ServiceException {
        dubboRestLongTextNodeOperateService.uploadTextStream(
                new DubboRestLongTextNodeTextStreamUploadInfo(
                        info.getCategory(), info.getArgs(), info.getLength(), info.getContent()
                )
        );
    }

    @Override
    public LongIdKey requestTextStreamVoucherForPublic(PublicLongTextNodeTextDownloadInfo info)
            throws ServiceException {
        try {
            VoucherIdWrapper voucherIdWrapper = dubboRestLongTextNodeOperateService.requestTextStreamVoucher(
                    new DubboRestLongTextNodeTextStreamDownloadInfo(
                            publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                            info.getArgs()
                    )
            );
            return new LongIdKey(voucherIdWrapper.getVoucherId());
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("请求下载公共长文本节点文本流凭证时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
