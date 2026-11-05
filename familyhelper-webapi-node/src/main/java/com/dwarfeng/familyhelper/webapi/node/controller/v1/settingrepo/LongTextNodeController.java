package com.dwarfeng.familyhelper.webapi.node.controller.v1.settingrepo;

import com.alibaba.fastjson.JSONArray;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.webapi.node.webmvc.Base64RequestParam;
import com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo.dto.WebInputPublicLongTextNodeTextDownloadInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.LongTextNodeResponseService;
import com.dwarfeng.settingrepo.sdk.bean.dto.FastJsonLongTextNodeInspectResult;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputLongTextNodeInspectInfo;
import com.dwarfeng.settingrepo.sdk.bean.dto.WebInputLongTextNodeTextDownloadInfo;
import com.dwarfeng.settingrepo.sdk.bean.entity.FastJsonLongTextNode;
import com.dwarfeng.settingrepo.stack.bean.dto.*;
import com.dwarfeng.settingrepo.stack.bean.entity.LongTextNode;
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
 * 长文本节点控制器。
 *
 * @author DwArFeng
 * @since 2.4.1
 */
@RestController
@RequestMapping("/api/v1/settingrepo")
public class LongTextNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongTextNodeController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final LongTextNodeResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<LongTextNode, FastJsonLongTextNode> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    public LongTextNodeController(
            LongTextNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<LongTextNode, FastJsonLongTextNode> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
    }

    @GetMapping("/long-text-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.exists")
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/long-text-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.get")
    public FastJsonResponseData<FastJsonLongTextNode> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            LongTextNode longTextNode = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongTextNode.of(longTextNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/long-text-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.all")
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonLongTextNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<LongTextNode> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonLongTextNode> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/long-text-node/inspect")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.inspect")
    public FastJsonResponseData<FastJsonLongTextNodeInspectResult> inspect(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongTextNodeInspectInfo webInputLongTextNodeInspectInfo,
            BindingResult bindingResult
    ) {
        try {
            LongTextNodeInspectResult inspect = service.inspect(
                    WebInputLongTextNodeInspectInfo.toStackBean(webInputLongTextNodeInspectInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonLongTextNodeInspectResult.of(inspect)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/long-text-node/download-text")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.download_text")
    public ResponseEntity<Object> downloadText(
            HttpServletRequest request,
            @Base64RequestParam("download-info") WebInputLongTextNodeTextDownloadInfo downloadInfo
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            LongTextNodeText longTextNodeText = service.downloadText(
                    WebInputLongTextNodeTextDownloadInfo.toStackBean(downloadInfo)
            );
            headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
            body = longTextNodeText.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping("/long-text-node/request-text-stream-voucher")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.request_text_stream_voucher")
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestTextStreamVoucher(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongTextNodeTextDownloadInfo downloadInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey voucherKey = service.requestTextStreamVoucher(
                    WebInputLongTextNodeTextDownloadInfo.toStackBean(downloadInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/long-text-node/download-text-by-voucher")
    @BehaviorAnalyse
    public void downloadTextStreamByVoucher(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("voucher-id") Long voucherId
    ) throws Exception {
        try {
            LongTextNodeTextStream longTextNodeTextStream = service.downloadTextStreamByVoucher(
                    new LongIdKey(voucherId)
            );

            long fileLength = longTextNodeTextStream.getLength();

            // 设置响应头，包括文本大小以及内容类型。
            response.setHeader(HttpHeaders.CONTENT_LENGTH, Long.toString(fileLength));
            response.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8).toString());

            // 获取文本流的输入流以及 response 的输出流，传输数据。
            try (InputStream in = longTextNodeTextStream.getContent(); OutputStream out = response.getOutputStream()) {
                IOUtil.trans(in, out, IO_TRANS_BUFFER_SIZE);
                out.flush();
            }
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            throw sem.map(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/long-text-node/upload")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.upload_text")
    public FastJsonResponseData<Object> uploadText(HttpServletRequest request) {
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

            // 解析文本内容。
            byte[] content;
            try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                IOUtil.trans(in, bout, IO_TRANS_BUFFER_SIZE);
                bout.flush();
                content = bout.toByteArray();
            }
            String text = new String(content, StandardCharsets.UTF_8);

            // 获取 category 和 args。
            String category = multipartHttpServletRequest.getParameter("category");
            String argsAsString = multipartHttpServletRequest.getParameter("args");
            String[] args = JSONArray.parseArray(argsAsString, String.class).toArray(new String[0]);

            // 将文本内容转换为接口需要的格式，并上传。
            service.uploadText(new LongTextNodeTextUploadInfo(category, args, text));

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/long-text-node/upload-stream")
    @BehaviorAnalyse
    @LoginRequired
    @PermissionRequired("webapi.controller_permitted.settingrepo.long_text_node.upload_text_stream")
    public FastJsonResponseData<Object> uploadTextStream(HttpServletRequest request) {
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

            // 解析文本流，并上传。
            long contentLength = file.getSize();
            try (InputStream fin = file.getInputStream()) {
                service.uploadTextStream(
                        new LongTextNodeTextStreamUploadInfo(category, args, contentLength, fin)
                );
            }

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/long-text-node/request-text-stream-voucher-for-public")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> requestTextStreamVoucherForPublic(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPublicLongTextNodeTextDownloadInfo downloadInfo,
            BindingResult bindingResult
    ) {
        try {
            LongIdKey voucherKey = service.requestTextStreamVoucherForPublic(
                    WebInputPublicLongTextNodeTextDownloadInfo.toStackBean(downloadInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(voucherKey)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
