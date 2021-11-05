package com.dwarfeng.familyhelper.webapi.node.controller.v1.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfile;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.WebInputProfile;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.FastJsonDispProfile;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.ProfileResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
@RestController
@RequestMapping("/api/v1/clannad")
public class ProfileController {

    private final ProfileResponseService service;
    private final ServiceExceptionMapper sem;

    public ProfileController(
            ProfileResponseService service, ServiceExceptionMapper sem
    ) {
        this.service = service;
        this.sem = sem;
    }

    @GetMapping("/profile/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/profile/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonProfile> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            Profile profile = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonProfile.of(profile)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonProfile.class, e, sem));
        }
    }

    @GetMapping("/profile/{id}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<FastJsonDispProfile> getDisp(
            HttpServletRequest request, @PathVariable("id") String id
    ) {
        try {
            DispProfile dispProfile = service.getDisp(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDispProfile.of(dispProfile)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonDispProfile.class, e, sem));
        }
    }

    @PatchMapping("/profile")
    @BehaviorAnalyse
    @BindingCheck
    @LoginRequired
    public FastJsonResponseData<Object> updateProfile(
            HttpServletRequest request,
            @RequestBody @Validated WebInputProfile webInputProfile, BindingResult bindingResult
    ) {
        try {
            service.updateProfile(WebInputProfile.toStackBean(webInputProfile));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
