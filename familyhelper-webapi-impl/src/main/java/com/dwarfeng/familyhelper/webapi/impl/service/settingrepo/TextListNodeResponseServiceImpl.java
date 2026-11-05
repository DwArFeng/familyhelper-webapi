package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.TextListNodeResponseService;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.TextListNode;
import com.dwarfeng.settingrepo.stack.service.TextListNodeMaintainService;
import com.dwarfeng.settingrepo.stack.service.TextListNodeOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TextListNodeResponseServiceImpl implements TextListNodeResponseService {

    private final TextListNodeMaintainService textListNodeMaintainService;
    private final TextListNodeOperateService textListNodeOperateService;

    public TextListNodeResponseServiceImpl(
            @Qualifier("settingrepoTextListNodeMaintainService")
            TextListNodeMaintainService textListNodeMaintainService,
            @Qualifier("settingrepoTextListNodeOperateService")
            TextListNodeOperateService textListNodeOperateService
    ) {
        this.textListNodeMaintainService = textListNodeMaintainService;
        this.textListNodeOperateService = textListNodeOperateService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return textListNodeMaintainService.exists(key);
    }

    @Override
    public TextListNode get(StringIdKey key) throws ServiceException {
        return textListNodeMaintainService.get(key);
    }

    @Override
    public PagedData<TextListNode> all(PagingInfo pagingInfo) throws ServiceException {
        return textListNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public TextListNodeSizeResult size(TextListNodeSizeInfo info) throws ServiceException {
        return textListNodeOperateService.size(info);
    }

    @Override
    public TextListNodeInspectResult inspect(TextListNodeInspectInfo info) throws ServiceException {
        return textListNodeOperateService.inspect(info);
    }

    @Override
    public TextListNodeTextFetchResult fetchText(TextListNodeTextFetchInfo info) throws ServiceException {
        return textListNodeOperateService.fetchText(info);
    }

    @Override
    public void insertText(TextListNodeTextInsertInfo info) throws ServiceException {
        textListNodeOperateService.insertText(info);
    }

    @Override
    public void updateText(TextListNodeTextUpdateInfo info) throws ServiceException {
        textListNodeOperateService.updateText(info);
    }

    @Override
    public void changeOrder(TextListNodeChangeOrderInfo info) throws ServiceException {
        textListNodeOperateService.changeOrder(info);
    }

    @Override
    public void remove(TextListNodeRemoveInfo info) throws ServiceException {
        textListNodeOperateService.remove(info);
    }
}
