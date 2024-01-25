package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonNickname;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.WebInputNickname;
import com.dwarfeng.familyhelper.clannad.sdk.bean.key.FastJsonNicknameKey;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Nickname;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.NicknameKey;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.NicknameResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 昵称控制器。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class NicknameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NicknameController.class);

    private final NicknameResponseService service;
    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Nickname, FastJsonNickname> beanTransformer;

    public NicknameController(
            NicknameResponseService service, ServiceExceptionMapper sem, BeanTransformer<Nickname,
            FastJsonNickname> beanTransformer
    ) {
        this.service = service;
        this.sem = sem;
        this.beanTransformer = beanTransformer;
    }

    @GetMapping("/nickname/{subjectUserId}&{objectUserId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("subjectUserId") String subjectUserId, @PathVariable("objectUserId") String objectUserId
    ) {
        try {
            boolean exists = service.exists(new NicknameKey(subjectUserId, objectUserId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/nickname/{subjectUserId}&{objectUserId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonNickname> get(
            HttpServletRequest request,
            @PathVariable("subjectUserId") String subjectUserId, @PathVariable("objectUserId") String objectUserId
    ) {
        try {
            Nickname nickname = service.get(new NicknameKey(subjectUserId, objectUserId));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonNickname.of(nickname)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PostMapping("/nickname")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<FastJsonNicknameKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputNickname webInputNickname, BindingResult bindingResult) {
        try {
            Nickname nickname = WebInputNickname.toStackBean(webInputNickname);
            NicknameKey insert = service.insert(nickname);
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonNicknameKey.of(insert)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @PatchMapping("/nickname")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputNickname webInputNickname, BindingResult bindingResult
    ) {
        try {
            service.update(WebInputNickname.toStackBean(webInputNickname));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @DeleteMapping("/nickname/{subjectUserId}&{objectUserId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("subjectUserId") String subjectUserId, @PathVariable("objectUserId") String objectUserId
    ) {
        try {
            service.delete(new NicknameKey(subjectUserId, objectUserId));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/user/subject/{subjectUserId}/nickname")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonNickname>> childForSubjectUser(
            HttpServletRequest request,
            @PathVariable("subjectUserId") String subjectUserId, @RequestParam("page") int page, @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Nickname> all = service.childForSubjectUser(
                    new StringIdKey(subjectUserId), new PagingInfo(page, rows)
            );
            PagedData<FastJsonNickname> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
