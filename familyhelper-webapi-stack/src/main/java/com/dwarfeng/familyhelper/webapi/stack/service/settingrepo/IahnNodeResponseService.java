package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.*;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 国际化节点响应服务。
 *
 * <p>
 * Iahn 是 I18n 的别名，Iahn 的作用是国际化。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public interface IahnNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    IahnNode get(StringIdKey key) throws ServiceException;

    PagedData<IahnNode> all(PagingInfo pagingInfo) throws ServiceException;

    IahnNodeMessageInspectResult inspectMessage(IahnNodeMessageInspectInfo info) throws ServiceException;

    IahnNodeMessageInspectByLocaleResult batchInspectMessageByLocale(IahnNodeMessageInspectByLocaleInfo info)
            throws ServiceException;

    IahnNodeLocaleListInspectResult inspectLocaleList(IahnNodeLocaleListInspectInfo info) throws ServiceException;

    IahnNodeMekListInspectResult inspectMekList(IahnNodeMekListInspectInfo info) throws ServiceException;

    IahnNodeMessageTableInspectResult inspectMessageTable(IahnNodeMessageTableInspectInfo info) throws ServiceException;

    void putLocale(IahnNodeLocalePutInfo info) throws ServiceException;

    void removeLocale(IahnNodeLocaleRemoveInfo info) throws ServiceException;

    void putMek(IahnNodeMekPutInfo info) throws ServiceException;

    void removeMek(IahnNodeMekRemoveInfo info) throws ServiceException;

    void upsertMessage(IahnNodeMessageUpsertInfo info) throws ServiceException;

    void batchUpsertMessageByLocale(IahnNodeMessageUpsertByLocaleInfo info) throws ServiceException;

    void batchUpsertMessageByMek(IahnNodeMessageUpsertByMekInfo info) throws ServiceException;

    /**
     * 检查公共国际化节点的消息。
     *
     * @param info 公共国际化节点消息检查信息。
     * @return 国际化节点消息检查结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    IahnNodeMessageInspectResult inspectMessageForPublic(PublicIahnNodeMessageInspectInfo info)
            throws ServiceException;

    /**
     * 批量检查指定本地化的公共国际化节点消息。
     *
     * @param info 公共国际化节点按本地化批量消息检查信息。
     * @return 国际化节点按本地化批量消息检查结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    IahnNodeMessageInspectByLocaleResult batchInspectMessageByLocaleForPublic(
            PublicIahnNodeMessageInspectByLocaleInfo info
    ) throws ServiceException;

    /**
     * 检查公共国际化节点的本地化列表。
     *
     * @param info 公共国际化节点本地化列表检查信息。
     * @return 国际化节点本地化列表检查结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    IahnNodeLocaleListInspectResult inspectLocaleListForPublic(PublicIahnNodeLocaleListInspectInfo info)
            throws ServiceException;

    /**
     * 检查公共国际化节点的 Mek 列表。
     *
     * @param info 公共国际化节点 Mek 列表检查信息。
     * @return 国际化节点 Mek 列表检查结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    IahnNodeMekListInspectResult inspectMekListForPublic(PublicIahnNodeMekListInspectInfo info)
            throws ServiceException;

    /**
     * 检查公共国际化节点的消息表。
     *
     * @param info 公共国际化节点消息表检查信息。
     * @return 国际化节点消息表检查结果。
     * @throws ServiceException 服务异常。
     * @since 1.7.0
     */
    IahnNodeMessageTableInspectResult inspectMessageTableForPublic(PublicIahnNodeMessageTableInspectInfo info)
            throws ServiceException;
}
