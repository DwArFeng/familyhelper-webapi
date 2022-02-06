package com.dwarfeng.familyhelper.webapi.node.controller.v1.project;

import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputPermissionRemoveInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputPermissionUpsertInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputProjectCreateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.dto.WebInputProjectUpdateInfo;
import com.dwarfeng.familyhelper.project.sdk.bean.entity.JSFixedFastJsonProject;
import com.dwarfeng.familyhelper.project.stack.bean.entity.Project;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.project.ProjectResponseService;
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

/**
 * 资产目录控制器。
 *
 * @author DwArFeng
 * @since 1.0.3
 */
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Project, JSFixedFastJsonProject> projectBeanTransformer;
    private final BeanTransformer<DispProject, JSFixedFastJsonDispProject> dispProjectBeanTransformer;

    private final TokenHandler tokenHandler;

    public ProjectController(
            ProjectResponseService service, ServiceExceptionMapper sem,
            BeanTransformer<Project, JSFixedFastJsonProject> projectBeanTransformer,
            BeanTransformer<DispProject, JSFixedFastJsonDispProject> dispProjectBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.projectBeanTransformer = projectBeanTransformer;
        this.dispProjectBeanTransformer = dispProjectBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/project/{id}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/project/{id}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonProject> get(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Project project = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonProject.of(project)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonProject.class, e, sem));
        }
    }

    @GetMapping("/project/all")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonProject>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Project> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonProject> transform = PagingUtil.transform(all, projectBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/project/{id}/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispProject> getDisp(
            HttpServletRequest request, @PathVariable("id") Long id
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispProject dispProject = service.getDisp(new LongIdKey(id), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispProject.of(dispProject)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonDispProject.class, e, sem));
        }
    }

    @GetMapping("/project/all-permitted/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispProject>> allPermittedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispProject> allPermittedDisp = service.allPermittedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispProject> transform = PagingUtil.transform(
                    allPermittedDisp, dispProjectBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/project/all-owned/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispProject>> allOwnedDisp(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            PagedData<DispProject> allOwnedDisp = service.allOwnedDisp(
                    accountKey, new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDispProject> transform = PagingUtil.transform(
                    allOwnedDisp, dispProjectBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @PostMapping("/project/create")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> createProject(
            HttpServletRequest request,
            @RequestBody @Validated WebInputProjectCreateInfo projectCreateInfo, BindingResult bindingResult) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            LongIdKey result = service.createProject(
                    accountKey, WebInputProjectCreateInfo.toStackBean(projectCreateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(result)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/project/update")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> updateProject(
            HttpServletRequest request,
            @RequestBody @Validated WebInputProjectUpdateInfo webInputProjectUpdateInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.updateProject(
                    accountKey, WebInputProjectUpdateInfo.toStackBean(webInputProjectUpdateInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @PostMapping("/project/remove")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removeProject(
            HttpServletRequest request,
            @RequestBody @Validated WebInputLongIdKey projectKey, BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removeProject(accountKey, WebInputLongIdKey.toStackBean(projectKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/project/upsert-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> upsertPermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionUpsertInfo webInputPermissionUpsertInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.upsertPermission(
                    accountKey, WebInputPermissionUpsertInfo.toStackBean(webInputPermissionUpsertInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping("/project/remove-permission")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> removePermission(
            HttpServletRequest request,
            @RequestBody @Validated WebInputPermissionRemoveInfo webInputPermissionRemoveInfo,
            BindingResult bindingResult
    ) {
        try {
            StringIdKey accountKey = tokenHandler.getAccountKey(request);
            service.removePermission(
                    accountKey, WebInputPermissionRemoveInfo.toStackBean(webInputPermissionRemoveInfo)
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }
}
