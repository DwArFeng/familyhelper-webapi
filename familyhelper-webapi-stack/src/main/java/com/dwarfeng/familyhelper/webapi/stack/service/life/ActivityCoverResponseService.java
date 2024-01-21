package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCover;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCoverUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityCoverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动封面响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityCoverResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityCoverInfo get(LongIdKey key) throws ServiceException;

    PagedData<ActivityCoverInfo> childForActivity(LongIdKey activityKey, PagingInfo pagingInfo) throws ServiceException;

    ActivityCover download(StringIdKey userKey, LongIdKey coverKey) throws ServiceException;

    void upload(StringIdKey userKey, ActivityCoverUploadInfo coverUploadInfo) throws ServiceException;

    void remove(StringIdKey userKey, LongIdKey coverKey) throws ServiceException;

    void updateOrder(StringIdKey userKey, ActivityCoverOrderUpdateInfo coverUpdateInfo) throws ServiceException;
}
