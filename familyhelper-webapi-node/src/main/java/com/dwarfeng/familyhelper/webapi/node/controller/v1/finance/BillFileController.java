package com.dwarfeng.familyhelper.webapi.node.controller.v1.finance;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.finance.sdk.bean.entity.JSFixedFastJsonBillFileInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BillFile;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BillFileUploadInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BillFileInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BillFileResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 票据文件控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/finance")
public class BillFileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillFileController.class);

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final BillFileResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<BillFileInfo, JSFixedFastJsonBillFileInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public BillFileController(
            BillFileResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<BillFileInfo, JSFixedFastJsonBillFileInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/bill-file/{longId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("longId") Long longId) {
        try {
            boolean exists = service.exists(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bill-file/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonBillFileInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            BillFileInfo billFileInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonBillFileInfo.of(billFileInfo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("fund-change/{fundChangeId}/bill-file")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBillFileInfo>> childForFundChange(
            HttpServletRequest request,
            @PathVariable("fundChangeId") long fundChangeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<BillFileInfo> childForBill = service.childForFundChange(
                    new LongIdKey(fundChangeId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonBillFileInfo> transform = PagingUtil.transform(
                    childForBill, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("fund-change/{fundChangeId}/bill-file/index-asc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBillFileInfo>> childForFundChangeIndexAsc(
            HttpServletRequest request,
            @PathVariable("fundChangeId") long fundChangeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<BillFileInfo> childForBill = service.childForFundChangeIndexAsc(
                    new LongIdKey(fundChangeId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonBillFileInfo> transform = PagingUtil.transform(
                    childForBill, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("fund-change/{fundChangeId}/bill-file/index-desc")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonBillFileInfo>> childForFundChangeIndexDesc(
            HttpServletRequest request,
            @PathVariable("fundChangeId") long fundChangeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<BillFileInfo> childForBill = service.childForFundChangeIndexDesc(
                    new LongIdKey(fundChangeId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonBillFileInfo> transform = PagingUtil.transform(
                    childForBill, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/bill-file/{billFileId}/download")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadBillFile(
            HttpServletRequest request, @PathVariable("billFileId") Long billFileId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            BillFile billFile = service.downloadBillFile(accountKey, new LongIdKey(billFileId));
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(billFile.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = billFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/fund-change/{fundChangeId}/bill-file/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadBillFile(
            HttpServletRequest request, @PathVariable("fundChangeId") Long fundChangeId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getUserKey(request);

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

            // 将文件内容转换为接口需要的格式，并上传。
            service.uploadBillFile(
                    accountKey, new BillFileUploadInfo(new LongIdKey(fundChangeId), originFileName, content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/bill-file/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeBillFile(
            HttpServletRequest request, @RequestBody WebInputLongIdKey billFileKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeBillFile(accountKey, WebInputLongIdKey.toStackBean(billFileKey));
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
