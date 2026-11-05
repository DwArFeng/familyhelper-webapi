package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.dto.PublicLongTextNodeTextDownloadInfo;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.LongTextNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 长文本节点响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LongTextNodeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    LongTextNode get(StringIdKey key) throws ServiceException;

    PagedData<LongTextNode> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 查看长文本节点。
     *
     * @param info 长文本节点查看信息。
     * @return 长文本节点查看结果。
     * @throws ServiceException 服务异常。
     */
    LongTextNodeInspectResult inspect(LongTextNodeInspectInfo info) throws ServiceException;

    /**
     * 下载长文本节点文本。
     *
     * @param info 长文本节点文本下载信息。
     * @return 长文本节点文本。
     * @throws ServiceException 服务异常。
     */
    LongTextNodeText downloadText(LongTextNodeTextDownloadInfo info) throws ServiceException;

    LongIdKey requestTextStreamVoucher(LongTextNodeTextDownloadInfo info) throws ServiceException;

    LongTextNodeTextStream downloadTextStreamByVoucher(LongIdKey voucherKey) throws ServiceException;

    /**
     * 上传长文本节点文本。
     *
     * @param info 长文本节点文本上传信息。
     * @throws ServiceException 服务异常。
     */
    void uploadText(LongTextNodeTextUploadInfo info) throws ServiceException;

    /**
     * 上传长文本节点文本流。
     *
     * <p>
     * 调用者有义务关闭 <code>LongTextNodeTextStream</code> 中的输入流，
     * 即其中的 <code>LongTextNodeTextStream.content</code>。
     *
     * @param info 长文本节点文本流上传信息。
     * @throws ServiceException 服务异常。
     */
    void uploadTextStream(LongTextNodeTextStreamUploadInfo info) throws ServiceException;

    /**
     * 请求下载公共长文本节点文本流凭证。
     *
     * @param info 公共长文本节点文本下载信息。
     * @return 下载长文本节点文本流凭证主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey requestTextStreamVoucherForPublic(PublicLongTextNodeTextDownloadInfo info) throws ServiceException;
}
