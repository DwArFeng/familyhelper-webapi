package com.dwarfeng.familyhelper.webapi.node.controller.v1.note;

import com.dwarfeng.familyhelper.note.sdk.bean.dto.WebInputNoteNodeCreateInfo;
import com.dwarfeng.familyhelper.note.sdk.bean.entity.JSFixedFastJsonNoteNode;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispNoteNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.dto.note.WebInputCompatibleNoteNodeUpdateInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteNodeResponseService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 笔记节点控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/note")
public class NoteNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteNodeController.class);

    private final NoteNodeResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NoteNode, JSFixedFastJsonNoteNode> noteNodeBeanTransformer;
    private final BeanTransformer<DispNoteNode, JSFixedFastJsonDispNoteNode> dispNoteNodeBeanTransformer;

    private final TokenHandler tokenHandler;

    public NoteNodeController(
            NoteNodeResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<NoteNode, JSFixedFastJsonNoteNode> noteNodeBeanTransformer,
            BeanTransformer<DispNoteNode, JSFixedFastJsonDispNoteNode> dispNoteNodeBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.noteNodeBeanTransformer = noteNodeBeanTransformer;
        this.dispNoteNodeBeanTransformer = dispNoteNodeBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/note-node/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNoteNode> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            NoteNode noteNode = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonNoteNode.of(noteNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<NoteNode> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(all, noteNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/note-book/{noteBookId}/note-node", "/note-book//note-node"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> childForNoteBook(
            @PathVariable(required = false, value = "noteBookId") Long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey noteBookKey = null;
            if (Objects.nonNull(noteBookId)) {
                noteBookKey = new LongIdKey(noteBookId);
            }
            PagedData<NoteNode> childForNoteBook = service.childForNoteBook(
                    noteBookKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(
                    childForNoteBook, noteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/note-node/root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> childForNoteBookRoot(
            HttpServletRequest request,
            @PathVariable("noteBookId") long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteNode> childForNoteBookRoot = service.childForNoteBookRoot(
                    new LongIdKey(noteBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(
                    childForNoteBookRoot, noteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/{parentId}/child")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> childForParent(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteNode> childForParent = service.childForParent(
                    new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(
                    childForParent, noteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/note-book/{noteBookId}/note-node/name-like", "/note-book//note-node/name-like"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> childForNoteBookNameLike(
            @PathVariable(required = false, value = "noteBookId") Long noteBookId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            LongIdKey noteBookKey = null;
            if (Objects.nonNull(noteBookId)) {
                noteBookKey = new LongIdKey(noteBookId);
            }
            PagedData<NoteNode> childForNoteBook = service.childForNoteBookNameLike(
                    noteBookKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(
                    childForNoteBook, noteNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispNoteNode> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispNoteNode dispNoteNode = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispNoteNode.of(dispNoteNode)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteNode> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    allDisp, dispNoteNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/note-book/{noteBookId}/note-node/disp", "/note-book//note-node/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> childForNoteBookDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "noteBookId") Long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey noteBookKey = null;
            if (Objects.nonNull(noteBookId)) {
                noteBookKey = new LongIdKey(noteBookId);
            }
            PagedData<DispNoteNode> childForNoteBookDisp = service.childForNoteBookDisp(
                    accountKey, noteBookKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    childForNoteBookDisp, dispNoteNodeBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/note-node/root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> childForNoteBookRootDisp(
            HttpServletRequest request,
            @PathVariable("noteBookId") long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteNode> childForNoteBookRoot = service.childForNoteBookRootDisp(
                    accountKey, new LongIdKey(noteBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    childForNoteBookRoot, dispNoteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/{parentId}/child/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> childForParentDisp(
            HttpServletRequest request,
            @PathVariable("parentId") long parentId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteNode> childForParent = service.childForParentDisp(
                    accountKey, new LongIdKey(parentId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    childForParent, dispNoteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/note-book/{noteBookId}/note-node/name-like/disp", "/note-book//note-node/name-like/disp"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> childForNoteBookNameLikeDisp(
            HttpServletRequest request,
            @PathVariable(required = false, value = "noteBookId") Long noteBookId,
            @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey noteBookKey = null;
            if (Objects.nonNull(noteBookId)) {
                noteBookKey = new LongIdKey(noteBookId);
            }
            PagedData<DispNoteNode> childForNoteBook = service.childForNoteBookNameLikeDisp(
                    accountKey, noteBookKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    childForNoteBook, dispNoteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/{id}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> nodePathFromRoot(
            HttpServletRequest request, @PathVariable(value = "id") Long id
    ) {
        try {
            PagedData<NoteNode> pathFromRoot = service.nodePathFromRoot(new LongIdKey(id));
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(
                    pathFromRoot, noteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-node/{id}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> nodePathFromRootDisp(
            HttpServletRequest request, @PathVariable(value = "id") Long id
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteNode> pathFromRoot = service.nodePathFromRootDisp(accountKey, new LongIdKey(id));
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    pathFromRoot, dispNoteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/{itemId}/path-from-root")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteNode>> itemPathFromRoot(
            HttpServletRequest request, @PathVariable(value = "itemId") Long itemId
    ) {
        try {
            PagedData<NoteNode> pathFromRoot = service.itemPathFromRoot(new LongIdKey(itemId));
            PagedData<JSFixedFastJsonNoteNode> transform = PagingUtil.transform(
                    pathFromRoot, noteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-item/{itemId}/path-from-root/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteNode>> itemPathFromRootDisp(
            HttpServletRequest request, @PathVariable(value = "itemId") Long itemId
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteNode> pathFromRoot = service.itemPathFromRootDisp(accountKey, new LongIdKey(itemId));
            PagedData<JSFixedFastJsonDispNoteNode> transform = PagingUtil.transform(
                    pathFromRoot, dispNoteNodeBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-node/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createNoteNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteNodeCreateInfo noteNodeCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createNoteNode(
                    accountKey, WebInputNoteNodeCreateInfo.toStackBean(noteNodeCreateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-node/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateNoteNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputCompatibleNoteNodeUpdateInfo webInputCompatibleNoteNodeUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateNoteNode(
                    accountKey, WebInputCompatibleNoteNodeUpdateInfo.toStackBean(webInputCompatibleNoteNodeUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-node/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeNoteNode(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey noteNodeKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeNoteNode(accountKey, WebInputLongIdKey.toStackBean(noteNodeKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
