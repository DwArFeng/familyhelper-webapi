package com.dwarfeng.familyhelper.webapi.node.controller.v1.assets;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.assets.sdk.bean.dto.WebInputItemCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.assets.sdk.bean.entity.JSFixedFastJsonItemCoverInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCover;
import com.dwarfeng.familyhelper.assets.stack.bean.dto.ItemCoverUploadInfo;
import com.dwarfeng.familyhelper.assets.stack.bean.entity.ItemCoverInfo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.assets.ItemCoverResponseService;
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
 * ????????????????????????
 *
 * @author DwArFeng
 * @since 1.0.2
 */
@RestController
@RequestMapping("/api/v1/assets")
public class ItemCoverController {

    private final ItemCoverResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<ItemCoverInfo, JSFixedFastJsonItemCoverInfo> beanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public ItemCoverController(
            ItemCoverResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<ItemCoverInfo, JSFixedFastJsonItemCoverInfo> beanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/item-cover/{longId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("longId") Long longId) {
        try {
            boolean exists = service.exists(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item-cover/{longId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonItemCoverInfo> get(
            HttpServletRequest request, @PathVariable("longId") Long longId
    ) {
        try {
            ItemCoverInfo itemCoverInfo = service.get(new LongIdKey(longId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonItemCoverInfo.of(itemCoverInfo)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("item/{itemId}/item-cover")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonItemCoverInfo>> childForItem(
            HttpServletRequest request,
            @PathVariable("itemId") long itemId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<ItemCoverInfo> childForItem = service.childForItem(
                    new LongIdKey(itemId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonItemCoverInfo> transform = PagingUtil.transform(
                    childForItem, beanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/item-cover/{itemCoverId}/download")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadItemCover(
            HttpServletRequest request, @PathVariable("itemCoverId") Long itemCoverId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            ItemCover itemCover = service.downloadItemCover(accountKey, new LongIdKey(itemCoverId));
            // ????????????????????? HTTP ????????????????????????????????????
            String fileName = adjustFileNameEncoding(itemCover.getOriginName());
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = itemCover.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/item/{itemId}/item-cover/upload")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadItemCover(
            HttpServletRequest request, @PathVariable("itemId") Long itemId
    ) {
        try {
            // ???????????????????????????
            StringIdKey accountKey = tokenHandler.getAccountKey(request);

            // ?????????????????????
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("???????????????????????????????????????");
            }

            //?????? multiRequest ???????????????
            MultipartHttpServletRequest multipartHttpServletRequest = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("?????????????????? file ??????");
            }

            // ?????????????????????
            String originFileName = file.getOriginalFilename();
            byte[] content;
            try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                IOUtil.trans(in, bout, 4096);
                bout.flush();
                content = bout.toByteArray();
            }

            // ????????????????????????????????????????????????????????????
            service.uploadItemCover(
                    accountKey, new ItemCoverUploadInfo(new LongIdKey(itemId), originFileName, content)
            );

            // ?????????????????????
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/item-cover/remove")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> removeItemCover(
            HttpServletRequest request, @RequestBody WebInputLongIdKey itemCoverKey
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeItemCover(accountKey, WebInputLongIdKey.toStackBean(itemCoverKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }

    @PostMapping("/item-cover/update-order")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateItemCoverOrder(
            HttpServletRequest request, @RequestBody WebInputItemCoverOrderUpdateInfo itemCoverOrderUpdateInfo
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateItemCoverOrder(
                    accountKey, WebInputItemCoverOrderUpdateInfo.toStackBean(itemCoverOrderUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
