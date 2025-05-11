package com.dwarfeng.familyhelper.webapi.node.controller.v1.note;

import com.dwarfeng.familyhelper.note.sdk.bean.dto.*;
import com.dwarfeng.familyhelper.note.sdk.bean.entity.JSFixedFastJsonNoteBook;
import com.dwarfeng.familyhelper.note.stack.bean.entity.NoteBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.note.NoteBookResponseService;
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

/**
 * 笔记本控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/note")
public class NoteBookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteBookController.class);

    private final NoteBookResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<NoteBook, JSFixedFastJsonNoteBook> noteBookBeanTransformer;
    private final BeanTransformer<DispNoteBook, JSFixedFastJsonDispNoteBook> dispNoteBookBeanTransformer;

    private final TokenHandler tokenHandler;

    public NoteBookController(
            NoteBookResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<NoteBook, JSFixedFastJsonNoteBook> noteBookBeanTransformer,
            BeanTransformer<DispNoteBook, JSFixedFastJsonDispNoteBook> dispNoteBookBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.noteBookBeanTransformer = noteBookBeanTransformer;
        this.dispNoteBookBeanTransformer = dispNoteBookBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/note-book/{id}/exists")
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

    @GetMapping("/note-book/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonNoteBook> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            NoteBook noteBook = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonNoteBook.of(noteBook)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteBook>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<NoteBook> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteBook> transform = PagingUtil.transform(all, noteBookBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/user-owned")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteBook>> userOwned(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<NoteBook> userOwned = service.userOwned(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteBook> transform = PagingUtil.transform(userOwned, noteBookBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/user-permitted-with-condition-display")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteBook>> userPermittedWithConditionDisplay(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("only-favored") boolean onlyFavored,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<NoteBook> userPermittedWithConditionDisplay = service.userPermittedWithConditionDisplay(
                    accountKey, pattern, onlyFavored, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteBook> transform = PagingUtil.transform(
                    userPermittedWithConditionDisplay, noteBookBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/user-owned-with-condition-display")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonNoteBook>> userOwnedWithConditionDisplay(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("only-favored") boolean onlyFavored,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<NoteBook> userOwnedWithConditionDisplay = service.userOwnedWithConditionDisplay(
                    accountKey, pattern, onlyFavored, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonNoteBook> transform = PagingUtil.transform(
                    userOwnedWithConditionDisplay, noteBookBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispNoteBook> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispNoteBook dispNoteBook = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispNoteBook.of(dispNoteBook)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/all/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteBook>> allDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteBook> allDisp = service.allDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteBook> transform = PagingUtil.transform(
                    allDisp, dispNoteBookBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/user-owned/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteBook>> userOwnedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteBook> userOwnedDisp = service.userOwnedDisp(accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteBook> transform = PagingUtil.transform(
                    userOwnedDisp, dispNoteBookBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/user-permitted-with-condition-display/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteBook>>
    userPermittedWithConditionDisplayDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("only-favored") boolean onlyFavored,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteBook> userPermittedWithConditionDisplayDisp =
                    service.userPermittedWithConditionDisplayDisp(
                            accountKey, pattern, onlyFavored, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteBook> transform = PagingUtil.transform(
                    userPermittedWithConditionDisplayDisp, dispNoteBookBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/user-owned-with-condition-display/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispNoteBook>>
    userOwnedWithConditionDisplayDisp(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("only-favored") boolean onlyFavored,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispNoteBook> userOwnedWithConditionDisplayDisp = service.userOwnedWithConditionDisplayDisp(
                    accountKey, pattern, onlyFavored, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispNoteBook> transform = PagingUtil.transform(
                    userOwnedWithConditionDisplayDisp, dispNoteBookBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-book/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createNoteBook(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteBookCreateInfo noteBookCreateInfo, BindingResult bindingResult) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createNoteBook(
                    accountKey, WebInputNoteBookCreateInfo.toStackBean(noteBookCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-book/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateNoteBook(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteBookUpdateInfo webInputNoteBookUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateNoteBook(
                    accountKey, WebInputNoteBookUpdateInfo.toStackBean(webInputNoteBookUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-book/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeNoteBook(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey noteBookKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeNoteBook(accountKey, WebInputLongIdKey.toStackBean(noteBookKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-book/upsert-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> upsertPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteBookPermissionUpsertInfo webInputPermissionUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.upsertPermission(
                    accountKey, WebInputNoteBookPermissionUpsertInfo.toStackBean(webInputPermissionUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-book/remove-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteBookPermissionRemoveInfo webInputPermissionRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePermission(
                    accountKey, WebInputNoteBookPermissionRemoveInfo.toStackBean(webInputPermissionRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/note-book/change-favored")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> changeFavored(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNoteBookFavoredChangeInfo webInputNoteBookFavoredChangeInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.changeFavored(
                    accountKey, WebInputNoteBookFavoredChangeInfo.toStackBean(webInputNoteBookFavoredChangeInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
