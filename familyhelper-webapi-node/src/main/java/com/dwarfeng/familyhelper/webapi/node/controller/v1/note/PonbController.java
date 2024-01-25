package com.dwarfeng.familyhelper.webapi.node.controller.v1.note;

import com.dwarfeng.familyhelper.note.sdk.bean.entity.JSFixedFastJsonPonb;
import com.dwarfeng.familyhelper.note.stack.bean.entity.Ponb;
import com.dwarfeng.familyhelper.note.stack.bean.key.PonbKey;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispPonb;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispPonb;
import com.dwarfeng.familyhelper.webapi.stack.handler.system.TokenHandler;
import com.dwarfeng.familyhelper.webapi.stack.service.note.PonbResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.sdk.interceptor.login.LoginRequired;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 笔记本权限控制器。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
@RestController
@RequestMapping("/api/v1/note")
public class PonbController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PonbController.class);

    private final PonbResponseService service;

    private final ServiceExceptionMapper sem;

    private final BeanTransformer<Ponb, JSFixedFastJsonPonb> ponbBeanTransformer;
    private final BeanTransformer<DispPonb, JSFixedFastJsonDispPonb> dispPonbBeanTransformer;

    private final TokenHandler tokenHandler;

    public PonbController(
            PonbResponseService service,
            ServiceExceptionMapper sem,
            BeanTransformer<Ponb, JSFixedFastJsonPonb> ponbBeanTransformer,
            BeanTransformer<DispPonb, JSFixedFastJsonDispPonb> dispPonbBeanTransformer,
            TokenHandler tokenHandler
    ) {
        this.service = service;
        this.sem = sem;
        this.ponbBeanTransformer = ponbBeanTransformer;
        this.dispPonbBeanTransformer = dispPonbBeanTransformer;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/ponb/{longId}&{stringId}/exists")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            boolean exists = service.exists(new PonbKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/ponb/{longId}&{stringId}")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPonb> get(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            Ponb ponb = service.get(new PonbKey(longId, stringId));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPonb.of(ponb)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/ponb/{longId}&{stringId}/disp")
    @BehaviorAnalyse
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonDispPonb> getDisp(
            HttpServletRequest request, @PathVariable("longId") Long longId, @PathVariable("stringId") String stringId
    ) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            DispPonb dispPonb = service.getDisp(new PonbKey(longId, stringId), inspectAccountKey);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDispPonb.of(dispPonb)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/ponb")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPonb>> childForNoteBook(
            HttpServletRequest request, @PathVariable("noteBookId") Long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<Ponb> childForNoteBook = service.childForNoteBook(
                    new LongIdKey(noteBookId), new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPonb> transform = PagingUtil.transform(childForNoteBook, ponbBeanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }

    @GetMapping("/note-book/{noteBookId}/ponb/disp")
    @BehaviorAnalyse
    @SkipRecord
    @LoginRequired
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDispPonb>> childForNoteBookDisp(
            HttpServletRequest request, @PathVariable("noteBookId") Long noteBookId,
            @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            StringIdKey inspectAccountKey = tokenHandler.getAccountKey(request);
            PagedData<DispPonb> childForNoteBookDisp = service.childForNoteBookDisp(
                    new LongIdKey(noteBookId), new PagingInfo(page, rows), inspectAccountKey
            );
            PagedData<JSFixedFastJsonDispPonb> transform = PagingUtil.transform(
                    childForNoteBookDisp, dispPonbBeanTransformer
            );
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            LOGGER.warn("Controller 异常, 信息如下: ", e);
            return FastJsonResponseData.of(ResponseDataUtil.bad(e, sem));
        }
    }
}
