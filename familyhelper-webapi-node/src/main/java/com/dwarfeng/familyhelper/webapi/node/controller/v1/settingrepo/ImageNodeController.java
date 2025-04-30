package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.alibaba.fastjson.JSONArray;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.node.webmvc.Base64RequestParam;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.ImageNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.FastJsonImageNodeInspectResult;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputImageNodeFileDownloadInfo;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputImageNodeInspectInfo;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputImageNodeThumbnailDownloadInfo;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonImageNode;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.ImageNode;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
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
 * 图片节点控制器。
 *
 * @author DwArFeng
 * @since 1.3.0
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class ImageNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageNodeController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final ImageNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ImageNode, FastJsonImageNode> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    public ImageNodeController(
            ImageNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<ImageNode, FastJsonImageNode> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
    }

    @GetMapping("/image-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/image-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonImageNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            ImageNode imageNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonImageNode.of(imageNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/image-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonImageNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ImageNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonImageNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/image-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonImageNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageNodeInspectInfo webInputImageNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            ImageNodeInspectResult inspect = service.inspect(
                    WebInputImageNodeInspectInfo.toStackBean(webInputImageNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonImageNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    /**
     * @see #downloadFile(HttpServletRequest, WebInputImageNodeFileDownloadInfo)
     * @deprecated 由于文件下载一般使用 GET 请求，且该方法应该与下载文件缩略图方法保持一致，故废弃。
     */
    @SuppressWarnings("DuplicatedCode")
    @Deprecated
    @PostMapping("/image-node/download-file")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> legacyDownloadFile(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageNodeFileDownloadInfo webInputImageNodeFileDownloadInfo,
            BindingResult bindingResult
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            ImageNodeFile imageNodeFile = service.downloadFile(
                    WebInputImageNodeFileDownloadInfo.toStackBean(webInputImageNodeFileDownloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageNodeFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = imageNodeFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/image-node/download-file")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public ResponseEntity<Object> downloadFile(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputImageNodeFileDownloadInfo webInputImageNodeFileDownloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            ImageNodeFile imageNodeFile = service.downloadFile(
                    WebInputImageNodeFileDownloadInfo.toStackBean(webInputImageNodeFileDownloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageNodeFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = imageNodeFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/image-node/request-file-stream-voucher")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestFileStreamVoucher(
            HttpServletRequest request,
            @RequestBody @Validated WebInputImageNodeFileDownloadInfo downloadInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey voucherKey = service.requestFileStreamVoucher(
                    WebInputImageNodeFileDownloadInfo.toStackBean(downloadInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/image-node/download-file-by-voucher")
    @BehaviorAnalyse
    public void downloadFileStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            ImageNodeFileStream imageNodeFileStream = service.downloadFileStreamByVoucher(new LongIdKey(voucherId));

            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageNodeFileStream.getOriginName());
            long fileLength = imageNodeFileStream.getLength();

            // 设置响应头，包括文件大小、指示浏览器下载文件以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 获取 fileStream 的输入流以及 response 的输出流，传输数据。
            try (InputStream in = imageNodeFileStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @GetMapping("/image-node/download-thumbnail")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public ResponseEntity<Object> downloadThumbnail(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputImageNodeThumbnailDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            ImageNodeThumbnail imageNodeThumbnail = service.downloadThumbnail(
                    WebInputImageNodeThumbnailDownloadInfo.toStackBean(downloadInfo)
            );
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(imageNodeThumbnail.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = imageNodeThumbnail.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/image-node/upload")
    @BehaviorAnalyse
    @LoginRequired
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

            // 获取 category 和 args。
            String category = multipartHttpServletRequest.getParameter("category");
            String argsAsString = multipartHttpServletRequest.getParameter("args");
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);

            // 将文件内容转换为接口需要的格式，并上传。
            service.uploadFile(
                    new ImageNodeFileUploadInfo(category, args, originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/image-node/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
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
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);

            // 解析文件内容，并上传。
            String originFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.uploadFileStream(
                        new ImageNodeFileStreamUploadInfo(category, args, originFileName, contentLength, fin)
                );
            }

            // 返回响应结果。
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
