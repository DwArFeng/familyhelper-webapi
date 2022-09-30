package com.dwarfeng.familyhelper.webapi.node.controller.v1.note;

import com.dwarfeng.familyhelper.note.sdk.bean.dto.WebInputNoteItemCreateInfo;
import com.dwarfeng.familyhelper.note.sdk.bean.dto.WebInputNoteItemUpdateInfo;
import com.dwarfeng.familyhelper.note.sdk.bean.entity.JSFixedFastJsonNoteItem;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 笔记项目控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/note")
public class NoteItemController {

    private final NoteItemResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer;
    private final BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer;

    private final TokenHandler tokenHandler;

    public NoteItemController(
            NoteItemResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer,
            BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.noteItemBeanTransformer = noteItemBeanTransformer;
        this.dispNoteItemBeanTransformer = dispNoteItemBeanTransformer;
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

    @GetMapping("/note-set/{noteSetId}/note-item/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteItem>> childForNoteSetRoot(
            HttpServletRequest request,
            @PathVariable("noteSetId") long noteSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteItem> childForNoteSetRoot = service.childForNoteSetRoot(
                    new LongIdKey(noteSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteItem> transform = PagingUtil.transform(
                    childForNoteSetRoot, noteItemBeanTransformer
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

    @GetMapping("/note-set/{noteSetId}/note-item/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteItem>> childForNoteSetRootDisp(
            HttpServletRequest request,
            @PathVariable("noteSetId") long noteSetId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteItem> childForNoteSetRoot = service.childForNoteSetRootDisp(
                    accountKey, new LongIdKey(noteSetId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNoteItem> transform = PagingUtil.transform(
                    childForNoteSetRoot, dispNoteItemBeanTransformer
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
}
