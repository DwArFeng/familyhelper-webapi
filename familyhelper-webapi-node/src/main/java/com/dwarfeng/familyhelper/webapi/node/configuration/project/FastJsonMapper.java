package com.dwarfeng.familyhelper.webapi.node.configuration.project;

import com.dwarfeng.familyhelper.project.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.project.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispPop;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispProject;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.project.JSFixedFastJsonDispTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispPop;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispProject;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.project.DispTask;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Mapper
public interface FastJsonMapper {

    JSFixedFastJsonProject projectToJsFixedFastJson(Project project);

    @InheritInverseConfiguration
    Project projectFromJsFixedFastJson(JSFixedFastJsonProject jsFixedFastJsonProject);

    JSFixedFastJsonDispProject dispProjectToJsFixedFastJson(DispProject dispProject);

    @InheritInverseConfiguration
    DispProject dispProjectFromJsFixedFastJson(JSFixedFastJsonDispProject jsFixedFastJsonDispProject);

    JSFixedFastJsonPop popToJsFixedFastJson(Pop pop);

    @InheritInverseConfiguration
    Pop popFromJsFixedFastJson(JSFixedFastJsonPop jsFixedFastJsonPop);

    JSFixedFastJsonDispPop dispPopToJsFixedFastJson(DispPop dispPop);

    @InheritInverseConfiguration
    DispPop dispPopFromJsFixedFastJson(JSFixedFastJsonDispPop jsFixedFastJsonDispPop);

    FastJsonTaskTypeIndicator taskTypeIndicatorToFastJson(TaskTypeIndicator taskTypeIndicator);

    @InheritInverseConfiguration
    TaskTypeIndicator taskTypeIndicatorFromFastJson(FastJsonTaskTypeIndicator fastJsonTaskTypeIndicator);

    JSFixedFastJsonTask taskToJsFixedFastJson(Task task);

    @InheritInverseConfiguration
    Task taskFromJsFixedFastJson(JSFixedFastJsonTask jsFixedFastJsonTask);

    JSFixedFastJsonDispTask dispTaskToJsFixedFastJson(DispTask dispTask);

    @InheritInverseConfiguration
    DispTask dispTaskFromJsFixedFastJson(JSFixedFastJsonDispTask jsFixedFastJsonDispTask);

    JSFixedFastJsonMemo memoToJsFixedFastJson(Memo memo);

    @InheritInverseConfiguration
    Memo memoFromJsFixedFastJson(JSFixedFastJsonMemo jsFixedFastJsonMemo);

    JSFixedFastJsonMemoFileInfo memoFileInfoToJsFixedFastJson(MemoFileInfo memoFileInfo);

    @InheritInverseConfiguration
    MemoFileInfo memoFileInfoFromJsFixedFastJson(JSFixedFastJsonMemoFileInfo jsFixedFastJsonMemoFileInfo);

    JSFixedFastJsonMemoRemindDriverInfo memoRemindDriverInfoToJsFixedFastJson(
            MemoRemindDriverInfo memoRemindDriverInfo
    );

    @InheritInverseConfiguration
    MemoRemindDriverInfo memoRemindDriverInfoFromJsFixedFastJson(
            JSFixedFastJsonMemoRemindDriverInfo jsFixedFastJsonMemoRemindDriverInfo
    );

    FastJsonMemoRemindDriverSupport memoRemindDriverSupportToFastJson(MemoRemindDriverSupport memoRemindDriverSupport);

    @InheritInverseConfiguration
    MemoRemindDriverSupport memoRemindDriverSupportFromFastJson(
            FastJsonMemoRemindDriverSupport fastJsonMemoRemindDriverSupport
    );
}
