package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.TextNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodeInspectResult;
import com.dwarfeng.settingrepo.stack.bean.dto.TextNodePutInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.TextNode;
import com.dwarfeng.settingrepo.stack.service.TextNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.TextNodeOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TextNodeResponseServiceImpl implements TextNodeResponseService {

    private final TextNodeMaintainService textNodeMaintainService;
    private final TextNodeOperateService textNodeOperateService;

    public TextNodeResponseServiceImpl(
            @Qualifier("settingrepoTextNodeMaintainService") TextNodeMaintainService textNodeMaintainService,
            @Qualifier("settingrepoTextNodeOperateService") TextNodeOperateService textNodeOperateService
    ) {
        this.textNodeMaintainService = textNodeMaintainService;
        this.textNodeOperateService = textNodeOperateService;
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
}
