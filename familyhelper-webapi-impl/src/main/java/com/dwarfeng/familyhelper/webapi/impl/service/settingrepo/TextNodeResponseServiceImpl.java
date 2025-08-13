package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicTextNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo.PublicSettingCategoryHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.TextNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodePutInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.TextNode;
import com.dwarfeng.settingrepo.stack.service.TextNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.TextNodeOperateService;
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
public class TextNodeResponseServiceImpl implements TextNodeResponseService {

    private final TextNodeMaintainService textNodeMaintainService;
    private final TextNodeOperateService textNodeOperateService;

    private final PublicSettingCategoryHandler publicSettingCategoryHandler;

    private final ServiceExceptionMapper sem;

    public TextNodeResponseServiceImpl(
            @Qualifier("settingrepoTextNodeMaintainService") TextNodeMaintainService textNodeMaintainService,
            @Qualifier("settingrepoTextNodeOperateService") TextNodeOperateService textNodeOperateService,
            PublicSettingCategoryHandler publicSettingCategoryHandler,
            ServiceExceptionMapper sem
    ) {
        this.textNodeMaintainService = textNodeMaintainService;
        this.textNodeOperateService = textNodeOperateService;
        this.publicSettingCategoryHandler = publicSettingCategoryHandler;
        this.sem = sem;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return textNodeMaintainService.exists(key);
    }

    @Override
    public TextNode get(StringIdKey key) throws ServiceException {
        return textNodeMaintainService.get(key);
    }

    @Override
    public PagedData<TextNode> all(PagingInfo pagingInfo) throws ServiceException {
        return textNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public TextNodeInspectResult inspect(TextNodeInspectInfo info) throws ServiceException {
        return textNodeOperateService.inspect(info);
    }

    @Override
    public void put(TextNodePutInfo info) throws ServiceException {
        textNodeOperateService.put(info);
    }

    @Override
    public TextNodeInspectResult inspectForPublic(PublicTextNodeInspectInfo info) throws ServiceException {
        try {
            TextNodeInspectInfo originalInfo = new TextNodeInspectInfo(
                    publicSettingCategoryHandler.parsePublicSettingCategory(info.getCategory()),
                    info.getArgs()
            );
            return textNodeOperateService.inspect(originalInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("查看指定的公共文本节点时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
