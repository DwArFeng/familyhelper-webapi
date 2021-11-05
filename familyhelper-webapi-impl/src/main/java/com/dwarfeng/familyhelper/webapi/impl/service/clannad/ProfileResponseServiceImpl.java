package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.enumeration.ProfileTypeCategory;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.ProfileTypeIndicatorKey;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.ProfileResponseService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ProfileResponseServiceImpl implements ProfileResponseService {

    private final ProfileMaintainService profileMaintainService;
    private final ProfileTypeIndicatorMaintainService profileTypeIndicatorMaintainService;

    public ProfileResponseServiceImpl(
            @Qualifier("familyhelperClannadProfileMaintainService")
                    ProfileMaintainService profileMaintainService,
            @Qualifier("familyhelperClannadProfileTypeIndicatorMaintainService")
                    ProfileTypeIndicatorMaintainService profileTypeIndicatorMaintainService
    ) {
        this.profileMaintainService = profileMaintainService;
        this.profileTypeIndicatorMaintainService = profileTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return profileMaintainService.exists(key);
    }

    @Override
    public Profile get(StringIdKey key) throws ServiceException {
        return profileMaintainService.get(key);
    }

    @Override
    public DispProfile getDisp(StringIdKey key) throws ServiceException {
        Profile profile = profileMaintainService.get(key);
        return dispProfileFromProfile(profile);
    }

    private DispProfile dispProfileFromProfile(Profile profile) throws ServiceException {
        ProfileTypeIndicator idTypeIndicator = null;
        ProfileTypeIndicator genderIndicator = null;
        ProfileTypeIndicator bloodTypeIndicator = null;
        ProfileTypeIndicator nationalityIndicator = null;
        ProfileTypeIndicator educationalLevelIndicator = null;
        ProfileTypeIndicator educationalBackgroundIndicator = null;
        ProfileTypeIndicator politicalStatusIndicator = null;
        ProfileTypeIndicator maritalStatusIndicator = null;
        if (Objects.nonNull(profile.getIdType())) {
            idTypeIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.ID_TYPE.getCategory(), profile.getIdType()
            ));
        }
        if (Objects.nonNull(profile.getGender())) {
            genderIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.GENDER.getCategory(), profile.getGender()
            ));
        }
        if (Objects.nonNull(profile.getBloodType())) {
            bloodTypeIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.BLOOD_TYPE.getCategory(), profile.getBloodType()
            ));
        }
        if (Objects.nonNull(profile.getNationality())) {
            nationalityIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.NATIONALITY.getCategory(), profile.getNationality()
            ));
        }
        if (Objects.nonNull(profile.getEducationalLevel())) {
            educationalLevelIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.EDUCATIONAL_LEVEL.getCategory(), profile.getEducationalLevel()
            ));
        }
        if (Objects.nonNull(profile.getEducationalBackground())) {
            educationalBackgroundIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.EDUCATIONAL_BACKGROUND.getCategory(), profile.getEducationalBackground()
            ));
        }
        if (Objects.nonNull(profile.getPoliticalStatus())) {
            politicalStatusIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.POLITICAL_STATUS.getCategory(), profile.getPoliticalStatus()
            ));
        }
        if (Objects.nonNull(profile.getMaritalStatus())) {
            maritalStatusIndicator = profileTypeIndicatorMaintainService.getIfExists(new ProfileTypeIndicatorKey(
                    ProfileTypeCategory.MARITAL_STATUS.getCategory(), profile.getMaritalStatus()
            ));
        }
        return DispProfile.of(
                profile, idTypeIndicator, genderIndicator, bloodTypeIndicator, nationalityIndicator,
                educationalLevelIndicator, educationalBackgroundIndicator, politicalStatusIndicator,
                maritalStatusIndicator
        );
    }

    @Override
    public void updateProfile(Profile profile) throws ServiceException {
        profileMaintainService.insertOrUpdate(profile);
    }
}
