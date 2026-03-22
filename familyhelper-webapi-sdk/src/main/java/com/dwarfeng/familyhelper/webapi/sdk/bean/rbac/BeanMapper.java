package com.dwarfeng.familyhelper.webapi.sdk.bean.rbac;

import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPermission;
import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispPexp;
import com.dwarfeng.familyhelper.webapi.sdk.bean.rbac.disp.FastJsonDispRoleUserRelation;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermission;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPexp;
import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispRoleUserRelation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Mapper
public interface BeanMapper {

    // region Familyhelper-webapi Disp

    FastJsonDispPermission dispPermissionToFastJson(DispPermission dispPermission);

    @InheritInverseConfiguration
    DispPermission dispPermissionFromFastJson(FastJsonDispPermission fastJsonDispPermission);

    FastJsonDispPermissionGroup dispPermissionGroupToFastJson(DispPermissionGroup dispPermissionGroup);

    @InheritInverseConfiguration
    DispPermissionGroup dispPermissionGroupFromFastJson(FastJsonDispPermissionGroup fastJsonDispPermissionGroup);

    FastJsonDispPexp dispPexpToFastJson(DispPexp dispPexp);

    @InheritInverseConfiguration
    DispPexp dispPexpFromFastJson(FastJsonDispPexp fastJsonDispPexp);

    FastJsonDispRoleUserRelation dispRoleUserRelationToFastJson(DispRoleUserRelation dispRoleUserRelation);

    @InheritInverseConfiguration
    DispRoleUserRelation dispRoleUserRelationFromFastJson(FastJsonDispRoleUserRelation fastJsonDispRoleUserRelation);

    // endregion
}
