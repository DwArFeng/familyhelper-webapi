package com.dwarfeng.familyhelper.webapi.node.controller.v1.note;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.familyhelper.note.sdk.bean.dto.WebInputNoteItemCreateInfo;
import com.dwarfeng.familyhelper.note.sdk.bean.dto.WebInputNoteItemUpdateInfo;
import com.dwarfeng.familyhelper.note.sdk.bean.entity.JSFixedFastJsonNoteItem;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFile;
import com.dwarfeng.familyhelper.note.stack.bean.dto.NoteFileUploadInfo;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispNoteItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteItem;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteItemResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 笔记项目控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/note")
public class NoteItemController {

    /**
     * IO 传输设定的缓冲容量。
     */
    private static final int IO_TRANS_BUFFER_SIZE = 4096;

    private final NoteItemResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer;
    private final BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer;

    private final CommonsMultipartResolver commonsMultipartResolver;

    private final TokenHandler tokenHandler;

    public NoteItemController(
            NoteItemResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer,
            BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer,
            CommonsMultipartResolver commonsMultipartResolver,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.noteItemBeanTransformer = noteItemBeanTransformer;
        this.dispNoteItemBeanTransformer = dispNoteItemBeanTransformer;
        this.commonsMultipartResolver = commonsMultipartResolver;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/note-item/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNoteItem> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            NoteItem noteItem = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonNoteItem.of(noteItem)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteItem>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteItem> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteItem> transform = PagingUtil.transform(all, noteItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/note-node/{noteNodeId}/note-item", "/note-node//note-item"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteItem>> childForNoteNode(
            @PathVariable(required = false, value = "noteNodeId") Long noteNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey noteNodeKey = null;
            if (Objects.nonNull(noteNodeId)) {
                noteNodeKey = new LongIdKey(noteNodeId);
            }
            PagedData<NoteItem> childForNoteNode = service.childForNoteNode(
                    noteNodeKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteItem> transform = PagingUtil.transform(
                    childForNoteNode, noteItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/note-item/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteItem>> childForNoteBookRoot(
            HttpServletRequest request,
            @PathVariable("noteBookId") long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteItem> childForNoteBookRoot = service.childForNoteBookRoot(
                    new LongIdKey(noteBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteItem> transform = PagingUtil.transform(
                    childForNoteBookRoot, noteItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/note-item/name-like")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteItem>> childForNoteBookNameLike(
            HttpServletRequest request,
            @PathVariable("noteBookId") long noteBookId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteItem> childForNoteBookRoot = service.childForNoteBookNameLike(
                    new LongIdKey(noteBookId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteItem> transform = PagingUtil.transform(
                    childForNoteBookRoot, noteItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispNoteItem> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispNoteItem dispNoteItem = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispNoteItem.of(dispNoteItem)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteItem>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteItem> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteItem> transform = PagingUtil.transform(
                    allDisp, dispNoteItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/note-node/{noteNodeId}/note-item/disp", "/note-node//note-item/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteItem>> childForNoteNodeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "noteNodeId") Long noteNodeId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey noteNodeKey = null;
            if (Objects.nonNull(noteNodeId)) {
                noteNodeKey = new LongIdKey(noteNodeId);
            }
            PagedData<DispNoteItem> childForNoteNodeDisp = service.childForNoteNodeDisp(
                    accountKey, noteNodeKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteItem> transform = PagingUtil.transform(
                    childForNoteNodeDisp, dispNoteItemBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/note-item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteItem>> childForNoteBookRootDisp(
            HttpServletRequest request,
            @PathVariable("noteBookId") long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteItem> childForNoteBookRoot = service.childForNoteBookRootDisp(
                    accountKey, new LongIdKey(noteBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNoteItem> transform = PagingUtil.transform(
                    childForNoteBookRoot, dispNoteItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/note-item/name-like/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteItem>> childForNoteBookNameLikeDisp(
            HttpServletRequest request,
            @PathVariable("noteBookId") long noteBookId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteItem> childForNoteBookRoot = service.childForNoteBookNameLikeDisp(
                    accountKey, new LongIdKey(noteBookId), pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNoteItem> transform = PagingUtil.transform(
                    childForNoteBookRoot, dispNoteItemBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-item/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createNoteItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteItemCreateInfo noteItemCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createNoteItem(
                    accountKey, WebInputNoteItemCreateInfo.toStackBean(noteItemCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-item/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateNoteItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteItemUpdateInfo webInputNoteItemUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateNoteItem(
                    accountKey, WebInputNoteItemUpdateInfo.toStackBean(webInputNoteItemUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-item/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeNoteItem(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey noteItemKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeNoteItem(accountKey, WebInputLongIdKey.toStackBean(noteItemKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/{noteItemId}/download-note-file")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public ResponseEntity<Object> downloadNoteFile(
            HttpServletRequest request, @PathVariable("noteItemId") Long noteItemId
    ) {
        HttpHeaders headers = new HttpHeaders();
        Object body;
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            NoteFile noteFile = service.downloadNoteFile(accountKey, new LongIdKey(noteItemId));
            // 将文件名转换成 HTTP 标准文件名编码下的格式。
            String fileName = adjustFileNameEncoding(Long.toString(noteItemId));
            headers.add("Content-Disposition", "attachment;filename=" + fileName);
            body = noteFile.getContent();
        } catch (Exception e) {
            body = FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/note-item/{noteItemId}/upload-note-file")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> uploadNoteFile(
            HttpServletRequest request, @PathVariable("noteItemId") Long noteItemId
    ) {
        try {
            // 通过请求解析用户。
            StringIdKey accountKey = tokenHandler.getAccountKey(request);

            // 确认请求合法。
            if (!commonsMultipartResolver.isMultipart(request)) {
                throw new IllegalStateException("请求不是标准的文件上传请求");
            }

            //获取 multiRequest 中的文件。
            MultipartHttpServletRequest multipartHttpServletRequest
                    = commonsMultipartResolver.resolveMultipart(request);
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            if (Objects.isNull(file)) {
                throw new IllegalStateException("请求体中缺少 file 属性");
            }

            // 解析文件内容。
            byte[] content;
            try (InputStream in = file.getInputStream(); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                IOUtil.trans(in, bout, IO_TRANS_BUFFER_SIZE);
                bout.flush();
                content = bout.toByteArray();
            }

            // 将文件内容转换为接口需要的格式，并上传。
            service.uploadNoteFile(
                    accountKey, new NoteFileUploadInfo(new LongIdKey(noteItemId), content)
            );

            // 返回响应结果。
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    private String adjustFileNameEncoding(String fileName) {
        return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
    }

    @SuppressWarnings("DuplicatedCode")
    @GetMapping("/note-item/{id}/path-from-root")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<List<JSFixedFastJsonLongIdKey>> pathFromRoot(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            List<LongIdKey> longIdKeys = service.pathFromRoot(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(
                    longIdKeys.stream().map(JSFixedFastJsonLongIdKey::of).collect(Collectors.toList())
            ));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
