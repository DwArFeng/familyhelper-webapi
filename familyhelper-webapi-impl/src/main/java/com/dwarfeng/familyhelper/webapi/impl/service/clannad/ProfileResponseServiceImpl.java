package com.dwarfeng.familyhelper.webapi.impl.service.clannad;

import com.dwarfeng.familyhelper.clannad.sdk.enumeration.ProfileTypeCategory;
import com.dwarfeng.familyhelper.clannad.stack.bean.dto.ProfileUpdateInfo;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Popr;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.ProfileTypeIndicatorKey;
import com.dwarfeng.familyhelper.clannad.stack.service.PoprMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileOperateService;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp.DispProfile;
import com.dwarfeng.familyhelper.webapi.stack.service.clannad.ProfileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class ProfileResponseServiceImpl implements ProfileResponseService {

    private final ProfileMaintainService profileMaintainService;
    private final ProfileTypeIndicatorMaintainService profileTypeIndicatorMaintainService;
    private final ProfileOperateService profileOperateService;
    private final PoprMaintainService poprMaintainService;

    public ProfileResponseServiceImpl(
            @Qualifier("familyhelperClannadProfileMaintainService") ProfileMaintainService profileMaintainService,
            @Qualifier("familyhelperClannadProfileTypeIndicatorMaintainService")
            ProfileTypeIndicatorMaintainService profileTypeIndicatorMaintainService,
            @Qualifier("familyhelperClannadProfileOperateService") ProfileOperateService profileOperateService,
            @Qualifier("familyhelperClannadPoprMaintainService") PoprMaintainService poprMaintainService
    ) {
        this.profileMaintainService = profileMaintainService;
        this.profileTypeIndicatorMaintainService = profileTypeIndicatorMaintainService;
        this.profileOperateService = profileOperateService;
        this.poprMaintainService = poprMaintainService;
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
    public PagedData<Profile> childForPermittedAccount(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Popr> lookup = poprMaintainService.lookup(
                PoprMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<Profile> profiles = new ArrayList<>();
        for (Popr popr : lookup.getData()) {
            StringIdKey profileKey = new StringIdKey(popr.getKey().getProfileId());
            profiles.add(get(profileKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCurrentPage(), profiles
        );
    }

    @Override
    public DispProfile getDisp(StringIdKey key) throws ServiceException {
        Profile profile = profileMaintainService.get(key);
        return dispProfileFromProfile(profile);
    }

    @Override
    public PagedData<DispProfile> childForPermittedAccountDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Popr> lookup = poprMaintainService.lookup(
                PoprMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispProfile> dispProfiles = new ArrayList<>();
        for (Popr popr : lookup.getData()) {
            StringIdKey profileKey = new StringIdKey(popr.getKey().getProfileId());
            dispProfiles.add(getDisp(profileKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCurrentPage(), dispProfiles
        );
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
    public void updateProfile(StringIdKey userKey, ProfileUpdateInfo profileUpdateInfo) throws ServiceException {
        profileOperateService.updateProfile(userKey, profileUpdateInfo);
    }

    @Override
    public void addGuestPermission(StringIdKey ownerUserKey, StringIdKey guestUserKey) throws ServiceException {
        profileOperateService.addGuestPermission(ownerUserKey, guestUserKey);
    }

    @Override
    public void removeGuestPermission(StringIdKey ownerUserKey, StringIdKey guestUserKey) throws ServiceException {
        profileOperateService.removeGuestPermission(ownerUserKey, guestUserKey);
    }

    @Override
    public void resetGuestPermission(StringIdKey ownerUserKey, Collection<StringIdKey> guestUserKeys) throws ServiceException {
        profileOperateService.resetGuestPermission(ownerUserKey, guestUserKeys);
    }
}
