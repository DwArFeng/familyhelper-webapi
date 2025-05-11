package com.dwarfeng.familyhelper.webapi.node.configuration.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.clannad.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.*;
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

    JSFixedFastJsonMessage messageToJsFixedFastJson(Message message);

    @InheritInverseConfiguration
    Message messageFromJsFixedFastJson(JSFixedFastJsonMessage jsFixedFastJsonMessage);

    JSFixedFastJsonDispMessage dispMessageToJsFixedFastJson(DispMessage dispMessage);

    @InheritInverseConfiguration
    DispMessage dispMessageFromJsFixedFastJson(JSFixedFastJsonDispMessage jsFixedFastJsonDispMessage);

    FastJsonMessageAuthorization messageAuthorizationToFastJson(MessageAuthorization messageAuthorization);

    @InheritInverseConfiguration
    MessageAuthorization messageAuthorizationFromFastJson(FastJsonMessageAuthorization fastJsonMessageAuthorization);

    FastJsonDispMessageAuthorization dispMessageAuthorizationToFastJson(
            DispMessageAuthorization dispMessageAuthorization
    );

    @InheritInverseConfiguration
    DispMessageAuthorization dispMessageAuthorizationFromFastJson(
            FastJsonDispMessageAuthorization fastJsonDispMessageAuthorization
    );

    JSFixedFastJsonMessageBodyInfo messageBodyInfoToJsFixedFastJson(MessageBodyInfo messageBodyInfo);

    @InheritInverseConfiguration
    MessageBodyInfo messageBodyInfoFromJsFixedFastJson(JSFixedFastJsonMessageBodyInfo jsFixedFastJsonMessageBodyInfo);

    JSFixedFastJsonMessageAttachmentInfo messageAttachmentInfoToJsFixedFastJson(
            MessageAttachmentInfo messageAttachmentInfo
    );

    @InheritInverseConfiguration
    MessageAttachmentInfo messageAttachmentInfoFromJsFixedFastJson(
            JSFixedFastJsonMessageAttachmentInfo jsFixedFastJsonMessageAttachmentInfo
    );
}
