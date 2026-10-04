package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.logicengine.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 部件响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface SectionResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Section get(LongIdKey key) throws ServiceException;

    LongIdKey insert(Section section) throws ServiceException;

    void update(Section section) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<Section> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Section> enabled(PagingInfo pagingInfo) throws ServiceException;
}
