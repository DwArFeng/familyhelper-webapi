package com.dwarfeng.familyhelper.webapi.stack.service.fileio;

import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispImportTask;
import com.dwarfeng.fileio.stack.bean.entity.ImportTask;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 导入任务响应服务。
 *
 * @author zhaofz
 * @since 2.1.0
 */
public interface ImportTaskResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    /**
     * 获取导入任务。
     *
     * @param key 导入任务主键。
     * @return 导入任务。
     * @throws ServiceException 服务异常。
     */
    ImportTask get(LongIdKey key) throws ServiceException;

    LongIdKey insert(ImportTask importTask) throws ServiceException;

    void update(ImportTask importTask) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    /**
     * 查询所有导入任务。
     *
     * @param pagingInfo 分页信息.
     * @return 导入任务分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<ImportTask> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 根据导入任务设置查询导入任务。
     *
     * @param taskSettingKey 导入任务设置主键。
     * @param pagingInfo     分页信息。
     * @return 导入任务分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<ImportTask> childForTaskSetting(LongIdKey taskSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    /**
     * 根据创建日期降序查询导入任务。
     *
     * @param pagingInfo 分页信息。
     * @return 导入任务分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<ImportTask> createDateDesc(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 根据用户查询导入任务。
     *
     * @param userKey    用户主键。
     * @param pagingInfo 分页信息。
     * @return 导入任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<ImportTask> childForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    /**
     * 获取可展示导入任务。
     *
     * @param key 导入任务主键。
     * @return 可展示导入任务。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    DispImportTask getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    /**
     * 查询所有可展示导入任务。
     *
     * @param pagingInfo 分页信息。
     * @return 可展示导入任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispImportTask> allDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey) throws ServiceException;

    /**
     * 根据导入任务设置查询可展示导入任务。
     *
     * @param taskSettingKey 导入任务设置主键。
     * @param pagingInfo     分页信息。
     * @return 可展示导入任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispImportTask> childForTaskSettingDisp(
            LongIdKey taskSettingKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    )
            throws ServiceException;

    /**
     * 根据创建日期降序查询可展示导入任务。
     *
     * @param pagingInfo 分页信息。
     * @return 可展示导入任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispImportTask> createDateDescDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException;

    /**
     * 根据用户查询可展示导入任务。
     *
     * @param userKey    用户主键。
     * @param pagingInfo 分页信息。
     * @return 可展示导入任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispImportTask> childForUserDisp(
            StringIdKey userKey, PagingInfo pagingInfo, StringIdKey inspectAccountKey
    ) throws ServiceException;

    /**
     * 根据当前账号查询可展示导入任务。
     *
     * @param pagingInfo        分页信息。
     * @param inspectAccountKey inspection 账号主键。
     * @return 可展示导入任务分页数据。
     * @throws ServiceException 服务异常。
     * @since 2.1.0
     */
    PagedData<DispImportTask> childForMeDisp(PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException;
}
