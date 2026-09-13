package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispExportTask;
import com.dwarfeng.fileio.stack.bean.entity.ExportTask;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导出任务响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ExportTaskResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    /**
     * 获取导出任务。
     *
     * @param key 导出任务主键。
     * @return 导出任务。
     * @throws ServiceException 服务异常。
     */
    ExportTask get(LongIdKey key) throws ServiceException;

    LongIdKey insert(ExportTask exportTask) throws ServiceException;

    void update(ExportTask exportTask) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    /**
     * 查询所有导出任务。
     *
     * @param pagingInfo 分页信息.
     * @return 导出任务分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<ExportTask> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 根据导出任务设置查询导出任务。
     *
     * @param taskSettingKey 导出任务设置主键。
     * @param pagingInfo     分页信息。
     * @return 导出任务分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<ExportTask> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    /**
     * 根据创建日期降序查询导出任务。
     *
     * @param pagingInfo 分页信息。
     * @return 导出任务分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<ExportTask> createDateDesc(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 根据用户查询导出任务。
     *
     * @param userKey    用户主键。
     * @param pagingInfo 分页信息。
     * @return 导出任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<ExportTask> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    /**
     * 获取可展示导出任务。
     *
     * @param key 导出任务主键。
     * @return 可展示导出任务。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    DispExportTask getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    /**
     * 查询所有可展示导出任务。
     *
     * @param pagingInfo 分页信息。
     * @return 可展示导出任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispExportTask> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey) throws ServiceException;

    /**
     * 根据导出任务设置查询可展示导出任务。
     *
     * @param taskSettingKey 导出任务设置主键。
     * @param pagingInfo     分页信息。
     * @return 可展示导出任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispExportTask> childForTaskSettingDisp(
            LongIdKey taskSettingKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    )
            throws ServiceException;

    /**
     * 根据创建日期降序查询可展示导出任务。
     *
     * @param pagingInfo 分页信息。
     * @return 可展示导出任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispExportTask> createDateDescDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException;

    /**
     * 根据用户查询可展示导出任务。
     *
     * @param userKey    用户主键。
     * @param pagingInfo 分页信息。
     * @return 可展示导出任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispExportTask> childForUserDisp(
            StringIdKey userKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    /**
     * 根据当前账号查询可展示导出任务。
     *
     * @param pagingInfo        分页信息。
     * @param inspectAccountKey inspection 账号主键。
     * @return 可展示导出任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispExportTask> childForMeDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException;
}
