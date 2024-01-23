package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.clannad;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.clannad.sdk.bean.entity.FastJsonProfileTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.clannad.DispProfile;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Objects;

/**
 * 可展示个人信息。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public class FastJsonDispProfile implements Dto {

    private static final long serialVersionUID = 7134317109783451945L;

    public static FastJsonDispProfile of(DispProfile dispProfile) {
        if (Objects.isNull(dispProfile)) {
            return null;
        } else {
            return new FastJsonDispProfile(
                    FastJsonStringIdKey.of(dispProfile.getKey()), dispProfile.getName(), dispProfile.getIdNumber(),
                    dispProfile.getIdType(), dispProfile.getBirthday(), dispProfile.getGender(),
                    dispProfile.getBloodType(), dispProfile.getNationality(), dispProfile.getFamilyAddress(),
                    dispProfile.getPhoneNumber(), dispProfile.getEmailAddress(), dispProfile.getEmployer(),
                    dispProfile.getJobPosition(), dispProfile.getEmployerAddress(), dispProfile.getJobTitle(),
                    dispProfile.getLatestSchoolName(), dispProfile.getEducationalLevel(),
                    dispProfile.getEducationalBackground(), dispProfile.getPoliticalStatus(),
                    dispProfile.getMaritalStatus(), dispProfile.getRemark(),
                    FastJsonProfileTypeIndicator.of(dispProfile.getIdTypeIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getGenderIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getBloodTypeIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getNationalityIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getEducationalLevelIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getEducationalBackgroundIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getPoliticalStatusIndicator()),
                    FastJsonProfileTypeIndicator.of(dispProfile.getMaritalStatusIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "id_number", ordinal = 3)
    private String idNumber;

    @JSONField(name = "id_type", ordinal = 4)
    private String idType;

    @JSONField(name = "birthday", ordinal = 5)
    private String birthday;

    @JSONField(name = "gender", ordinal = 6)
    private String gender;

    @JSONField(name = "blood_type", ordinal = 7)
    private String bloodType;

    @JSONField(name = "nationality", ordinal = 8)
    private String nationality;

    @JSONField(name = "family_address", ordinal = 9)
    private String familyAddress;

    @JSONField(name = "phone_number", ordinal = 10)
    private String phoneNumber;

    @JSONField(name = "email_address", ordinal = 11)
    private String emailAddress;

    @JSONField(name = "employer", ordinal = 12)
    private String employer;

    @JSONField(name = "job_position", ordinal = 13)
    private String jobPosition;

    @JSONField(name = "employer_address", ordinal = 14)
    private String employerAddress;

    @JSONField(name = "job_title", ordinal = 15)
    private String jobTitle;

    @JSONField(name = "latest_school_name", ordinal = 16)
    private String latestSchoolName;

    @JSONField(name = "educational_level", ordinal = 17)
    private String educationalLevel;

    @JSONField(name = "educational_background", ordinal = 18)
    private String educationalBackground;

    @JSONField(name = "political_status", ordinal = 19)
    private String politicalStatus;

    @JSONField(name = "marital_status", ordinal = 20)
    private String maritalStatus;

    @JSONField(name = "remark", ordinal = 21)
    private String remark;

    @JSONField(name = "id_type_indicator", ordinal = 22)
    private FastJsonProfileTypeIndicator idTypeIndicator;

    @JSONField(name = "gender_indicator", ordinal = 23)
    private FastJsonProfileTypeIndicator genderIndicator;

    @JSONField(name = "blood_type_indicator", ordinal = 24)
    private FastJsonProfileTypeIndicator bloodTypeIndicator;

    @JSONField(name = "nationality_indicator", ordinal = 25)
    private FastJsonProfileTypeIndicator nationalityIndicator;

    @JSONField(name = "educational_level_indicator", ordinal = 26)
    private FastJsonProfileTypeIndicator educationalLevelIndicator;

    @JSONField(name = "educational_background_indicator", ordinal = 27)
    private FastJsonProfileTypeIndicator educationalBackgroundIndicator;

    @JSONField(name = "political_status_indicator", ordinal = 28)
    private FastJsonProfileTypeIndicator politicalStatusIndicator;

    @JSONField(name = "marital_status_indicator", ordinal = 29)
    private FastJsonProfileTypeIndicator maritalStatusIndicator;

    public FastJsonDispProfile() {
    }

    public FastJsonDispProfile(
            FastJsonStringIdKey key, String name, String idNumber, String idType, String birthday, String gender,
            String bloodType, String nationality, String familyAddress, String phoneNumber, String emailAddress,
            String employer, String jobPosition, String employerAddress, String jobTitle, String latestSchoolName,
            String educationalLevel, String educationalBackground, String politicalStatus, String maritalStatus,
            String remark, FastJsonProfileTypeIndicator idTypeIndicator, FastJsonProfileTypeIndicator genderIndicator,
            FastJsonProfileTypeIndicator bloodTypeIndicator, FastJsonProfileTypeIndicator nationalityIndicator,
            FastJsonProfileTypeIndicator educationalLevelIndicator,
            FastJsonProfileTypeIndicator educationalBackgroundIndicator,
            FastJsonProfileTypeIndicator politicalStatusIndicator, FastJsonProfileTypeIndicator maritalStatusIndicator
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

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
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

    public FastJsonProfileTypeIndicator getIdTypeIndicator() {
        return idTypeIndicator;
    }

    public void setIdTypeIndicator(FastJsonProfileTypeIndicator idTypeIndicator) {
        this.idTypeIndicator = idTypeIndicator;
    }

    public FastJsonProfileTypeIndicator getGenderIndicator() {
        return genderIndicator;
    }

    public void setGenderIndicator(FastJsonProfileTypeIndicator genderIndicator) {
        this.genderIndicator = genderIndicator;
    }

    public FastJsonProfileTypeIndicator getBloodTypeIndicator() {
        return bloodTypeIndicator;
    }

    public void setBloodTypeIndicator(FastJsonProfileTypeIndicator bloodTypeIndicator) {
        this.bloodTypeIndicator = bloodTypeIndicator;
    }

    public FastJsonProfileTypeIndicator getNationalityIndicator() {
        return nationalityIndicator;
    }

    public void setNationalityIndicator(FastJsonProfileTypeIndicator nationalityIndicator) {
        this.nationalityIndicator = nationalityIndicator;
    }

    public FastJsonProfileTypeIndicator getEducationalLevelIndicator() {
        return educationalLevelIndicator;
    }

    public void setEducationalLevelIndicator(FastJsonProfileTypeIndicator educationalLevelIndicator) {
        this.educationalLevelIndicator = educationalLevelIndicator;
    }

    public FastJsonProfileTypeIndicator getEducationalBackgroundIndicator() {
        return educationalBackgroundIndicator;
    }

    public void setEducationalBackgroundIndicator(FastJsonProfileTypeIndicator educationalBackgroundIndicator) {
        this.educationalBackgroundIndicator = educationalBackgroundIndicator;
    }

    public FastJsonProfileTypeIndicator getPoliticalStatusIndicator() {
        return politicalStatusIndicator;
    }

    public void setPoliticalStatusIndicator(FastJsonProfileTypeIndicator politicalStatusIndicator) {
        this.politicalStatusIndicator = politicalStatusIndicator;
    }

    public FastJsonProfileTypeIndicator getMaritalStatusIndicator() {
        return maritalStatusIndicator;
    }

    public void setMaritalStatusIndicator(FastJsonProfileTypeIndicator maritalStatusIndicator) {
        this.maritalStatusIndicator = maritalStatusIndicator;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toString() {
        return "FastJsonDispProfile{" +
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
