package com.dwarfeng.familyhelper.webapi.stack.bean.clannad.disp;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.ProfileTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

import java.util.Objects;

/**
 * 可展示个人信息。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public class DispProfile implements Dto {

    private static final long serialVersionUID = 5721317384156975168L;

    public static DispProfile of(
            Profile profile, ProfileTypeIndicator idTypeIndicator, ProfileTypeIndicator genderIndicator,
            ProfileTypeIndicator bloodTypeIndicator, ProfileTypeIndicator nationalityIndicator,
            ProfileTypeIndicator educationalLevelIndicator, ProfileTypeIndicator educationalBackgroundIndicator,
            ProfileTypeIndicator politicalStatusIndicator, ProfileTypeIndicator maritalStatusIndicator
    ) {
        if (Objects.isNull(profile)) {
            return null;
        } else {
            return new DispProfile(
                    profile.getKey(), profile.getName(), profile.getIdNumber(), profile.getIdType(),
                    profile.getBirthday(), profile.getGender(), profile.getBloodType(), profile.getNationality(),
                    profile.getFamilyAddress(), profile.getPhoneNumber(), profile.getEmailAddress(),
                    profile.getEmployer(), profile.getJobPosition(), profile.getEmployerAddress(),
                    profile.getJobTitle(), profile.getLatestSchoolName(), profile.getEducationalLevel(),
                    profile.getEducationalBackground(), profile.getPoliticalStatus(), profile.getMaritalStatus(),
                    profile.getRemark(), idTypeIndicator, genderIndicator, bloodTypeIndicator, nationalityIndicator,
                    educationalLevelIndicator, educationalBackgroundIndicator, politicalStatusIndicator,
                    maritalStatusIndicator
            );
        }
    }

    private StringIdKey key;
    private String name;
    private String idNumber;
    private String idType;
    private String birthday;
    private String gender;
    private String bloodType;
    private String nationality;
    private String familyAddress;
    private String phoneNumber;
    private String emailAddress;
    private String employer;
    private String jobPosition;
    private String employerAddress;
    private String jobTitle;
    private String latestSchoolName;
    private String educationalLevel;
    private String educationalBackground;
    private String politicalStatus;
    private String maritalStatus;
    private String remark;
    private ProfileTypeIndicator idTypeIndicator;
    private ProfileTypeIndicator genderIndicator;
    private ProfileTypeIndicator bloodTypeIndicator;
    private ProfileTypeIndicator nationalityIndicator;
    private ProfileTypeIndicator educationalLevelIndicator;
    private ProfileTypeIndicator educationalBackgroundIndicator;
    private ProfileTypeIndicator politicalStatusIndicator;
    private ProfileTypeIndicator maritalStatusIndicator;

    public DispProfile() {
    }

    public DispProfile(
            StringIdKey key, String name, String idNumber, String idType, String birthday, String gender,
            String bloodType, String nationality, String familyAddress, String phoneNumber, String emailAddress,
            String employer, String jobPosition, String employerAddress, String jobTitle, String latestSchoolName,
            String educationalLevel, String educationalBackground, String politicalStatus, String maritalStatus,
            String remark, ProfileTypeIndicator idTypeIndicator, ProfileTypeIndicator genderIndicator,
            ProfileTypeIndicator bloodTypeIndicator, ProfileTypeIndicator nationalityIndicator,
            ProfileTypeIndicator educationalLevelIndicator, ProfileTypeIndicator educationalBackgroundIndicator,
            ProfileTypeIndicator politicalStatusIndicator, ProfileTypeIndicator maritalStatusIndicator
    ) {
        this.key = key;
        this.name = name;
        this.idNumber = idNumber;
        this.idType = idType;
        this.birthday = birthday;
        this.gender = gender;
        this.bloodType = bloodType;
        this.nationality = nationality;
        this.familyAddress = familyAddress;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.employer = employer;
        this.jobPosition = jobPosition;
        this.employerAddress = employerAddress;
        this.jobTitle = jobTitle;
        this.latestSchoolName = latestSchoolName;
        this.educationalLevel = educationalLevel;
        this.educationalBackground = educationalBackground;
        this.politicalStatus = politicalStatus;
        this.maritalStatus = maritalStatus;
        this.remark = remark;
        this.idTypeIndicator = idTypeIndicator;
        this.genderIndicator = genderIndicator;
        this.bloodTypeIndicator = bloodTypeIndicator;
        this.nationalityIndicator = nationalityIndicator;
        this.educationalLevelIndicator = educationalLevelIndicator;
        this.educationalBackgroundIndicator = educationalBackgroundIndicator;
        this.politicalStatusIndicator = politicalStatusIndicator;
        this.maritalStatusIndicator = maritalStatusIndicator;
    }

    public StringIdKey getKey() {
        return key;
    }

    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLatestSchoolName() {
        return latestSchoolName;
    }

    public void setLatestSchoolName(String latestSchoolName) {
        this.latestSchoolName = latestSchoolName;
    }

    public String getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getEducationalBackground() {
        return educationalBackground;
    }

    public void setEducationalBackground(String educationalBackground) {
        this.educationalBackground = educationalBackground;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProfileTypeIndicator getIdTypeIndicator() {
        return idTypeIndicator;
    }

    public void setIdTypeIndicator(ProfileTypeIndicator idTypeIndicator) {
        this.idTypeIndicator = idTypeIndicator;
    }

    public ProfileTypeIndicator getGenderIndicator() {
        return genderIndicator;
    }

    public void setGenderIndicator(ProfileTypeIndicator genderIndicator) {
        this.genderIndicator = genderIndicator;
    }

    public ProfileTypeIndicator getBloodTypeIndicator() {
        return bloodTypeIndicator;
    }

    public void setBloodTypeIndicator(ProfileTypeIndicator bloodTypeIndicator) {
        this.bloodTypeIndicator = bloodTypeIndicator;
    }

    public ProfileTypeIndicator getNationalityIndicator() {
        return nationalityIndicator;
    }

    public void setNationalityIndicator(ProfileTypeIndicator nationalityIndicator) {
        this.nationalityIndicator = nationalityIndicator;
    }

    public ProfileTypeIndicator getEducationalLevelIndicator() {
        return educationalLevelIndicator;
    }

    public void setEducationalLevelIndicator(ProfileTypeIndicator educationalLevelIndicator) {
        this.educationalLevelIndicator = educationalLevelIndicator;
    }

    public ProfileTypeIndicator getEducationalBackgroundIndicator() {
        return educationalBackgroundIndicator;
    }

    public void setEducationalBackgroundIndicator(ProfileTypeIndicator educationalBackgroundIndicator) {
        this.educationalBackgroundIndicator = educationalBackgroundIndicator;
    }

    public ProfileTypeIndicator getPoliticalStatusIndicator() {
        return politicalStatusIndicator;
    }

    public void setPoliticalStatusIndicator(ProfileTypeIndicator politicalStatusIndicator) {
        this.politicalStatusIndicator = politicalStatusIndicator;
    }

    public ProfileTypeIndicator getMaritalStatusIndicator() {
        return maritalStatusIndicator;
    }

    public void setMaritalStatusIndicator(ProfileTypeIndicator maritalStatusIndicator) {
        this.maritalStatusIndicator = maritalStatusIndicator;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toString() {
        return "DispProfile{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", idType='" + idType + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", nationality='" + nationality + '\'' +
                ", familyAddress='" + familyAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", employer='" + employer + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", employerAddress='" + employerAddress + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", latestSchoolName='" + latestSchoolName + '\'' +
                ", educationalLevel='" + educationalLevel + '\'' +
                ", educationalBackground='" + educationalBackground + '\'' +
                ", politicalStatus='" + politicalStatus + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", remark='" + remark + '\'' +
                ", idTypeIndicator=" + idTypeIndicator +
                ", genderIndicator=" + genderIndicator +
                ", bloodTypeIndicator=" + bloodTypeIndicator +
                ", nationalityIndicator=" + nationalityIndicator +
                ", educationalLevelIndicator=" + educationalLevelIndicator +
                ", educationalBackgroundIndicator=" + educationalBackgroundIndicator +
                ", politicalStatusIndicator=" + politicalStatusIndicator +
                ", maritalStatusIndicator=" + maritalStatusIndicator +
                '}';
    }
}
