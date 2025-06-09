package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.alibaba.fastjson.JSONArray;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.node.webmvc.Base64RequestParam;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.ImageListNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.*;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonImageListNode;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.ImageListNode;
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
 * 图片列表节点控制器。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class ImageListNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageListNodeController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ImageListNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImageListNode, FastJsonImageListNode> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    public ImageListNodeController(
            ImageListNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ImageListNode, FastJsonImageListNode> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
    }

    @GetMapping("/image-list-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/image-list-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.get")
    public FastJsonResponseData<FastJsonImageListNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            ImageListNode imageListNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonImageListNode.of(imageListNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/image-list-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonImageListNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImageListNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonImageListNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/image-list-node/size")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.size")
    public FastJsonResponseData<FastJsonImageListNodeSizeResult> size(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageListNodeSizeInfo webInputImageListNodeSizeInfo,
            BindingResult bindingResult
    ) {
        try {
            ImageListNodeSizeResult size = service.size(
                    WebInputImageListNodeSizeInfo.toStackBean(webInputImageListNodeSizeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonImageListNodeSizeResult.of(size)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/image-list-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.inspect")
    public FastJsonResponseData<FastJsonImageListNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageListNodeInspectInfo webInputImageListNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            ImageListNodeInspectResult inspect = service.inspect(
                    WebInputImageListNodeInspectInfo.toStackBean(webInputImageListNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonImageListNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * @see #downloadFile(HttpServletRequest, WebInputImageListNodeFileDownloadInfo)
     * @deprecated 由于文件下载一般使用 GET 请求，且该方法应该与下载文件缩略图方法保持一致，故废弃。
     */
    @SuppressWarnings("DuplicatedCode")
    @Deprecated
    @PostMapping("/image-list-node/download-file")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.download_file")
    public ResponseEntity<Object> legacyDownloadFile(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageListNodeFileDownloadInfo webInputImageListNodeFileDownloadInfo,
            BindingResult bindingResult
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            ImageListNodeFile imageListNodeFile = service.downloadFile(
                    WebInputImageListNodeFileDownloadInfo.toStackBean(webInputImageListNodeFileDownloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageListNodeFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = imageListNodeFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/image-list-node/download-file")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.download_file")
    public ResponseEntity<Object> downloadFile(
            HttpServletRequest request,
            @Base64RequestParam("download-info")
            WebInputImageListNodeFileDownloadInfo webInputImageListNodeFileDownloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            ImageListNodeFile imageListNodeFile = service.downloadFile(
                    WebInputImageListNodeFileDownloadInfo.toStackBean(webInputImageListNodeFileDownloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageListNodeFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = imageListNodeFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/image-list-node/request-file-stream-voucher")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.request_file_stream_voucher")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestFileStreamVoucher(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageListNodeFileDownloadInfo downloadInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey voucherKey = service.requestFileStreamVoucher(
                    WebInputImageListNodeFileDownloadInfo.toStackBean(downloadInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/image-list-node/download-file-by-voucher")
    @BehaviorAnalyse
    public void downloadFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            ImageListNodeFileStream imageListNodeFileStream = service.downloadFileStreamByVoucher(new LongIdKey(voucherId));

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageListNodeFileStream.getOriginName());
            long fileLength = imageListNodeFileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 fileStream 的输入流以及 response 的输出流，传输数据。
            try (InputStream in = imageListNodeFileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @GetMapping("/image-list-node/download-thumbnail")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.download_thumbnail")
    public ResponseEntity<Object> downloadThumbnail(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputImageListNodeThumbnailDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            ImageListNodeThumbnail imageListNodeThumbnail = service.downloadThumbnail(
                    WebInputImageListNodeThumbnailDownloadInfo.toStackBean(downloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageListNodeThumbnail.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = imageListNodeThumbnail.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/image-list-node/upload")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.upload_file")
    public FastJsonResponseData<Object> uploadFile(HttpServletRequest request) {
        try {
            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容。
            String originFileName = file.getOriginalFilename();
            byte[] content;
            try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                IOUtil.trans(in, bout, IO_TRANS_BUFFER_SIZE);
                bout.flush();
                content = bout.toByteArray();
            }

            // 获取 category, args, index。
            String category = multipartHttpServletRequest.getParameter("category");
            String argsAsString = multipartHttpServletRequest.getParameter("args");
            String indexAsString = multipartHttpServletRequest.getParameter("index");
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);
            Integer index;
            if (StringUtils.isBlank(indexAsString)) {
                index = null;
            } else {
                index = Integer.parseInt(indexAsString);
            }

            // 将文件内容转换为接口需要的格式，并上传。
            service.uploadFile(
                    new ImageListNodeFileUploadInfo(category, args, index, originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/image-list-node/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.upload_file_stream")
    public FastJsonResponseData<Object> uploadFileStream(HttpServletRequest request) {
        try {
            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest =
                    commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 获取 category 和 args。
            String category = multipartHttpServletRequest.getParameter("category");
            String argsAsString = multipartHttpServletRequest.getParameter("args");
            String indexAsString = multipartHttpServletRequest.getParameter("index");
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);
            Integer index;
            if (StringUtils.isBlank(indexAsString)) {
                index = null;
            } else {
                index = Integer.parseInt(indexAsString);
            }

            // 解析文件内容，并上传。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.uploadFileStream(
                        new ImageListNodeFileStreamUploadInfo(category, args, index, originFileName, contentLength, fin)
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/image-list-node/update")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.update_file")
    public FastJsonResponseData<Object> updateFile(HttpServletRequest request) {
        try {
            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容。
            String originFileName = file.getOriginalFilename();
            byte[] content;
            try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                IOUtil.trans(in, bout, IO_TRANS_BUFFER_SIZE);
                bout.flush();
                content = bout.toByteArray();
            }

            // 获取 category, args, index。
            String category = multipartHttpServletRequest.getParameter("category");
            String argsAsString = multipartHttpServletRequest.getParameter("args");
            String indexAsString = multipartHttpServletRequest.getParameter("index");
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);
            int index;
            if (StringUtils.isBlank(indexAsString)) {
                throw new IllegalStateException("请求体中缺少 index 属性, 或 index 属性为空");
            } else {
                index = Integer.parseInt(indexAsString);
            }

            // 将文件内容转换为接口需要的格式，并上传。
            service.updateFile(
                    new ImageListNodeFileUpdateInfo(category, args, index, originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/image-list-node/update-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.update_file_stream")
    public FastJsonResponseData<Object> updateFileStream(HttpServletRequest request) {
        try {
            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest =
                    commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 获取 category 和 args。
            String category = multipartHttpServletRequest.getParameter("category");
            String argsAsString = multipartHttpServletRequest.getParameter("args");
            String indexAsString = multipartHttpServletRequest.getParameter("index");
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);
            int index;
            if (StringUtils.isBlank(indexAsString)) {
                throw new IllegalStateException("请求体中缺少 index 属性, 或 index 属性为空");
            } else {
                index = Integer.parseInt(indexAsString);
            }

            // 解析文件内容，并上传。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.updateFileStream(
                        new ImageListNodeFileStreamUpdateInfo(category, args, index, originFileName, contentLength, fin)
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/image-list-node/change-order")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.change_order")
    public FastJsonResponseData<Object> changeOrder(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageListNodeChangeOrderInfo webInputImageListNodeChangeOrderInfo,
            BindingResult bindingResult
    ) {
        try {
            service.changeOrder(WebInputImageListNodeChangeOrderInfo.toStackBean(webInputImageListNodeChangeOrderInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/image-list-node/remove")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.image_list_node.remove")
    public FastJsonResponseData<Object> remove(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageListNodeRemoveInfo webInputImageListNodeRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            service.remove(WebInputImageListNodeRemoveInfo.toStackBean(webInputImageListNodeRemoveInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }
}
