package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.alibaba.fastjson.JSONArray;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.node.webmvc.Base64RequestParam;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicVideoListNodeFileDownloadInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicVideoListNodeInspectInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicVideoListNodeSizeInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicVideoListNodeThumbnailDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.VideoListNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.*;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonVideoListNode;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.VideoListNode;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.interceptor.permission.PermissionRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 视频列表节点控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class VideoListNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoListNodeController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final VideoListNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<VideoListNode, FastJsonVideoListNode> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    public VideoListNodeController(
            VideoListNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<VideoListNode, FastJsonVideoListNode> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
    }

    @GetMapping("/video-list-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/video-list-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.get")
    public FastJsonResponseData<FastJsonVideoListNode> get(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            VideoListNode videoListNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonVideoListNode.of(videoListNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/video-list-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonVideoListNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<VideoListNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonVideoListNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/size")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.size")
    public FastJsonResponseData<FastJsonVideoListNodeSizeResult> size(
            HttpServletRequest request,
            @RequestBody @Validated WebInputVideoListNodeSizeInfo webInputVideoListNodeSizeInfo,
            BindingResult bindingResult
    ) {
        try {
            VideoListNodeSizeResult size = service.size(
                    WebInputVideoListNodeSizeInfo.toStackBean(webInputVideoListNodeSizeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonVideoListNodeSizeResult.of(size)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.inspect")
    public FastJsonResponseData<FastJsonVideoListNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputVideoListNodeInspectInfo webInputVideoListNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            VideoListNodeInspectResult inspect = service.inspect(
                    WebInputVideoListNodeInspectInfo.toStackBean(webInputVideoListNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonVideoListNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/video-list-node/download-file")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.download_file")
    public ResponseEntity<Object> downloadFile(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputVideoListNodeFileDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            VideoListNodeFile videoListNodeFile = service.downloadFile(
                    WebInputVideoListNodeFileDownloadInfo.toStackBean(downloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(videoListNodeFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = videoListNodeFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/video-list-node/request-file-stream-voucher")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.request_file_stream_voucher")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestFileStreamVoucher(
            HttpServletRequest request,
            @RequestBody @Validated WebInputVideoListNodeFileDownloadInfo downloadInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey voucherKey = service.requestFileStreamVoucher(
                    WebInputVideoListNodeFileDownloadInfo.toStackBean(downloadInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/video-list-node/download-file-by-voucher")
    @BehaviorAnalyse
    public void downloadFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            VideoListNodeFileStream videoListNodeFileStream = service.downloadFileStreamByVoucher(
                    new LongIdKey(voucherId)
            );

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(videoListNodeFileStream.getOriginName());
            long fileLength = videoListNodeFileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 fileStream 的输入流以及 response 的输出流，传输数据。
            try (
                    InputStream in = videoListNodeFileStream.getContent();
                    OutputStream out = response.getOutputStream()
            ) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @GetMapping("/video-list-node/download-thumbnail")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.download_thumbnail")
    public ResponseEntity<Object> downloadThumbnail(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputVideoListNodeThumbnailDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            VideoListNodeThumbnail videoListNodeThumbnail = service.downloadThumbnail(
                    WebInputVideoListNodeThumbnailDownloadInfo.toStackBean(downloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(videoListNodeThumbnail.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = videoListNodeThumbnail.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/video-list-node/upload")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.upload_file")
    public FastJsonResponseData<Object> uploadFile(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = resolveMultipart(request);
            MultipartFile file = requireFile(multipartHttpServletRequest);

            String originFileName = file.getOriginalFilename();
            byte[] content = readContent(file);
            byte[] coverContent = readContent(multipartHttpServletRequest.getFile("cover"));
            String category = multipartHttpServletRequest.getParameter("category");
            String[] args = parseArgs(multipartHttpServletRequest.getParameter("args"));
            Integer index = parseOptionalIndex(multipartHttpServletRequest.getParameter("index"));

            service.uploadFile(
                    new VideoListNodeFileUploadInfo(category, args, index, originFileName, content, coverContent)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.upload_file_stream")
    public FastJsonResponseData<Object> uploadFileStream(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = resolveMultipart(request);
            MultipartFile file = requireFile(multipartHttpServletRequest);
            MultipartFile coverFile = multipartHttpServletRequest.getFile("cover");

            String category = multipartHttpServletRequest.getParameter("category");
            String[] args = parseArgs(multipartHttpServletRequest.getParameter("args"));
            Integer index = parseOptionalIndex(multipartHttpServletRequest.getParameter("index"));
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream(); InputStream coverIn = openContent(coverFile)) {
                service.uploadFileStream(
                        new VideoListNodeFileStreamUploadInfo(
                                category, args, index, originFileName, contentLength, fin, coverIn
                        )
                );
            }
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/video-list-node/update")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.update_file")
    public FastJsonResponseData<Object> updateFile(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = resolveMultipart(request);
            MultipartFile file = requireFile(multipartHttpServletRequest);

            String originFileName = file.getOriginalFilename();
            byte[] content = readContent(file);
            byte[] coverContent = readContent(multipartHttpServletRequest.getFile("cover"));
            String category = multipartHttpServletRequest.getParameter("category");
            String[] args = parseArgs(multipartHttpServletRequest.getParameter("args"));
            int index = parseRequiredIndex(multipartHttpServletRequest.getParameter("index"));

            service.updateFile(
                    new VideoListNodeFileUpdateInfo(category, args, index, originFileName, content, coverContent)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/update-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.update_file_stream")
    public FastJsonResponseData<Object> updateFileStream(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = resolveMultipart(request);
            MultipartFile file = requireFile(multipartHttpServletRequest);
            MultipartFile coverFile = multipartHttpServletRequest.getFile("cover");

            String category = multipartHttpServletRequest.getParameter("category");
            String[] args = parseArgs(multipartHttpServletRequest.getParameter("args"));
            int index = parseRequiredIndex(multipartHttpServletRequest.getParameter("index"));
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream(); InputStream coverIn = openContent(coverFile)) {
                service.updateFileStream(
                        new VideoListNodeFileStreamUpdateInfo(
                                category, args, index, originFileName, contentLength, fin, coverIn
                        )
                );
            }
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/change-order")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.change_order")
    public FastJsonResponseData<Object> changeOrder(
            HttpServletRequest request,
            @RequestBody @Validated WebInputVideoListNodeChangeOrderInfo webInputVideoListNodeChangeOrderInfo,
            BindingResult bindingResult
    ) {
        try {
            service.changeOrder(
                    WebInputVideoListNodeChangeOrderInfo.toStackBean(webInputVideoListNodeChangeOrderInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.video_list_node.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputVideoListNodeRemoveInfo webInputVideoListNodeRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputVideoListNodeRemoveInfo.toStackBean(webInputVideoListNodeRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/size-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonVideoListNodeSizeResult> sizeForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicVideoListNodeSizeInfo sizeInfo,
            BindingResult bindingResult
    ) {
        try {
            VideoListNodeSizeResult size = service.sizeForPublic(
                    WebInputPublicVideoListNodeSizeInfo.toStackBean(sizeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonVideoListNodeSizeResult.of(size)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/video-list-node/inspect-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonVideoListNodeInspectResult> inspectForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicVideoListNodeInspectInfo inspectInfo,
            BindingResult bindingResult
    ) {
        try {
            VideoListNodeInspectResult inspect = service.inspectForPublic(
                    WebInputPublicVideoListNodeInspectInfo.toStackBean(inspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonVideoListNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/video-list-node/download-file-for-public")
    @BehaviorAnalyse
    @SkipRecord
    public ResponseEntity<Object> downloadFileForPublic(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputPublicVideoListNodeFileDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            VideoListNodeFile videoListNodeFile = service.downloadFileForPublic(
                    WebInputPublicVideoListNodeFileDownloadInfo.toStackBean(downloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(videoListNodeFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = videoListNodeFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/video-list-node/request-file-stream-voucher-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestFileStreamVoucherForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicVideoListNodeFileDownloadInfo downloadInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey voucherKey = service.requestFileStreamVoucherForPublic(
                    WebInputPublicVideoListNodeFileDownloadInfo.toStackBean(downloadInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/video-list-node/download-thumbnail-for-public")
    @BehaviorAnalyse
    @SkipRecord
    public ResponseEntity<Object> downloadThumbnailForPublic(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputPublicVideoListNodeThumbnailDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            VideoListNodeThumbnail videoListNodeThumbnail = service.downloadThumbnailForPublic(
                    WebInputPublicVideoListNodeThumbnailDownloadInfo.toStackBean(downloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(videoListNodeThumbnail.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = videoListNodeThumbnail.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    private MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) {
        if (!commonsMultipartResolver.isMultipart(request)) {
            throw new IllegalStateException("请求不是标准的文件上传请求");
        }
        return commonsMultipartResolver.resolveMultipart(request);
    }

    private MultipartFile requireFile(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("file");
        if (Objects.isNull(file)) {
            throw new IllegalStateException("请求体中缺少 file 属性");
        }
        return file;
    }

    private String[] parseArgs(String argsAsString) {
        return JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);
    }

    private Integer parseOptionalIndex(String indexAsString) {
        if (StringUtils.isBlank(indexAsString)) {
            return null;
        }
        return Integer.parseInt(indexAsString);
    }

    private int parseRequiredIndex(String indexAsString) {
        if (StringUtils.isBlank(indexAsString)) {
            throw new IllegalStateException("请求体中缺少 index 属性, 或 index 属性为空");
        }
        return Integer.parseInt(indexAsString);
    }

    @SuppressWarnings("DuplicatedCode")
    private byte[] readContent(MultipartFile file) throws Exception {
        if (Objects.isNull(file) || file.isEmpty()) {
            return null;
        }
        try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            IOUtil.trans(in, bout, IO_TRANS_BUFFER_SIZE);
            bout.flush();
            return bout.toByteArray();
        }
    }

    private InputStream openContent(MultipartFile file) throws Exception {
        if (Objects.isNull(file) || file.isEmpty()) {
            return null;
        }
        return file.getInputStream();
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }
}
