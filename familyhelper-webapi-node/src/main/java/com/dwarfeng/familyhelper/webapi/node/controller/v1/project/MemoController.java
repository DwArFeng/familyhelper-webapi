package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputMemoCreateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputMemoUpdateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonMemo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Memo;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.project.MemoResponseService;
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
 * 备忘录控制器。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
@RestController
@RequestMapping("/api/v1/project")
public class MemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoController.class);

    private final MemoResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Memo, JSFixedFastJsonMemo> memoBeanTransformer;

    private final TokenHandler tokenHandler;

    public MemoController(
            MemoResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Memo, JSFixedFastJsonMemo> memoBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.memoBeanTransformer = memoBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/memo/{id}/exists")
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

    @GetMapping("/memo/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonMemo> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Memo memo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonMemo.of(memo)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/memo/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Memo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(all, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/memo", "/user//memo"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUser(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUser(userKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/memo/in-progress", "/user//memo/in-progress"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUserInProgress(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserInProgress(userKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/memo/finished", "/user//memo/finished"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUserFinished(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserFinished(userKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/memo/default-order", "/user//memo/default-order"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUserDefaultOrder(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserDefaultOrder(userKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/user/{userId}/memo/in-progress/default-order",
            "/user//memo/in-progress/default-order"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUserInProgressDefaultOrder(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserInProgressDefaultOrder(
                    userKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {"/user/{userId}/memo/finished/default-order", "/user//memo/finished/default-order"})
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUserFinishedDefaultOrder(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserFinishedDefaultOrder(
                    userKey, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/user/{userId}/memo/profile-like/default-order",
            "/user//memo/profile-like/default-order"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>> childForUserProfileLikeDefaultOrder(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserProfileLikeDefaultOrder(
                    userKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/user/{userId}/memo/in-progress/profile-like/default-order",
            "/user//memo/in-progress/profile-like/default-order"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>>
    childForUserInProgressProfileLikeDefaultOrder(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserInProgressProfileLikeDefaultOrder(
                    userKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping(value = {
            "/user/{userId}/memo/finished/profile-like/default-order",
            "/user//memo/finished/profile-like/default-order"
    })
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonMemo>>
    childForUserFinishedProfileLikeDefaultOrder(
            HttpServletRequest request,
            @PathVariable(required = false, value = "userId") String userId, @RequestParam("pattern") String pattern,
            @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            StringIdKey userKey = null;
            if (Objects.nonNull(userId)) {
                userKey = new StringIdKey(userId);
            }
            PagedData<Memo> childForUser = service.childForUserFinishedProfileLikeDefaultOrder(
                    userKey, pattern, new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonMemo> transform = PagingUtil.transform(childForUser, memoBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/memo/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createMemo(
            HttpServletRequest request,
            @RequestBody @Validated WebInputMemoCreateInfo memoCreateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            LongIdKey result = service.createMemo(accountKey, WebInputMemoCreateInfo.toStackBean(memoCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/memo/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateMemo(
            HttpServletRequest request,
            @RequestBody @Validated WebInputMemoUpdateInfo webInputMemoUpdateInfo, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.updateMemo(accountKey, WebInputMemoUpdateInfo.toStackBean(webInputMemoUpdateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/memo/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeMemo(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey memoKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeMemo(accountKey, WebInputLongIdKey.toStackBean(memoKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/memo/finish")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> finishMemo(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey memoKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.finishMemo(accountKey, WebInputLongIdKey.toStackBean(memoKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/memo/remove-finished-memos")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> removeFinishedMemos(HttpServletRequest request) {
        try {
            StringIdKey accountKey = tokenHandler.getUserKey(request);
            service.removeFinishedMemos(accountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
