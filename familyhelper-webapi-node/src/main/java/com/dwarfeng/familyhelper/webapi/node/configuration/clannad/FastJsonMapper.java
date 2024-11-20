package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.FastJsonDispProfile;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispCertificate;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad.JSFixedFastJsonDispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispCertificate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispPoce;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
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

    FastJsonProfile profileToFastJson(Profile profile);

    @InheritInverseConfiguration
    Profile profileFromFastJson(FastJsonProfile fastJsonProfile);

    FastJsonDispProfile dispProfileToFastJson(DispProfile dispProfile);

    @InheritInverseConfiguration
    DispProfile dispProfileFromFastJson(FastJsonDispProfile fastJsonDispProfile);

    FastJsonProfileTypeIndicator profileTypeIndicatorToFastJson(ProfileTypeIndicator profileTypeIndicator);

    @InheritInverseConfiguration
    ProfileTypeIndicator profileTypeIndicatorFromFastJson(FastJsonProfileTypeIndicator fastJsonProfileTypeIndicator);

    FastJsonNickname nicknameToFastJson(Nickname nickname);

    @InheritInverseConfiguration
    Nickname nicknameFromFastJson(FastJsonNickname fastJsonNickname);

    JSFixedFastJsonNotification notificationToJsFixedFastJson(Notification notification);

    @InheritInverseConfiguration
    Notification notificationFromJsFixedFastJson(JSFixedFastJsonNotification jsFixedFastJsonNotification);

    JSFixedFastJsonCertificate certificateToJsFixedFastJson(Certificate certificate);

    @InheritInverseConfiguration
    Certificate certificateFromJsFixedFastJson(JSFixedFastJsonCertificate jsFixedFastJsonCertificate);

    JSFixedFastJsonDispCertificate dispCertificateToJsFixedFastJson(DispCertificate dispCertificate);

    @InheritInverseConfiguration
    DispCertificate dispCertificateFromJsFixedFastJson(JSFixedFastJsonDispCertificate jsFixedFastJsonDispCertificate);

    JSFixedFastJsonCertificateFileInfo certificateFileInfoToJsFixedFastJson(CertificateFileInfo certificateFileInfo);

    @InheritInverseConfiguration
    CertificateFileInfo certificateFileInfoFromJsFixedFastJson(JSFixedFastJsonCertificateFileInfo jsFixedFastJsonCertificateFileInfo);

    JSFixedFastJsonPoce poceToJsFixedFastJson(Poce poce);

    @InheritInverseConfiguration
    Poce poceFromJsFixedFastJson(JSFixedFastJsonPoce jsFixedFastJsonPoce);

    JSFixedFastJsonDispPoce dispPoceToJsFixedFastJson(DispPoce dispPoce);

    @InheritInverseConfiguration
    DispPoce dispPoceFromJsFixedFastJson(JSFixedFastJsonDispPoce jsFixedFastJsonDispPoce);
}
