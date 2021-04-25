package com.gyh.fleacampus.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName fc_user
 */
public class FcUser implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 头像url地址
     */
    private String photo;

    /**
     * 手机
     */
    private String phone;

    /**
     * 性别：0：未知，1：男，2：女
     */
    private Short sex;

    /**
     * 经验
     */
    private Integer exp;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 星座
     */
    private String horoscope;

    /**
     * 学校区域id
     */
    private Integer schoolAreaId;

    /**
     * 年纪
     */
    private String grade;

    /**
     * 专业
     */
    private String specialty;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 头像url地址
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 头像url地址
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 性别：0：未知，1：男，2：女
     */
    public Short getSex() {
        return sex;
    }

    /**
     * 性别：0：未知，1：男，2：女
     */
    public void setSex(Short sex) {
        this.sex = sex;
    }

    /**
     * 经验
     */
    public Integer getExp() {
        return exp;
    }

    /**
     * 经验
     */
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    /**
     * 积分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 积分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 星座
     */
    public String getHoroscope() {
        return horoscope;
    }

    /**
     * 星座
     */
    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    /**
     * 学校区域id
     */
    public Integer getSchoolAreaId() {
        return schoolAreaId;
    }

    /**
     * 学校区域id
     */
    public void setSchoolAreaId(Integer schoolAreaId) {
        this.schoolAreaId = schoolAreaId;
    }

    /**
     * 年纪
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 年纪
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 专业
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * 专业
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FcUser other = (FcUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getExp() == null ? other.getExp() == null : this.getExp().equals(other.getExp()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getHoroscope() == null ? other.getHoroscope() == null : this.getHoroscope().equals(other.getHoroscope()))
            && (this.getSchoolAreaId() == null ? other.getSchoolAreaId() == null : this.getSchoolAreaId().equals(other.getSchoolAreaId()))
            && (this.getGrade() == null ? other.getGrade() == null : this.getGrade().equals(other.getGrade()))
            && (this.getSpecialty() == null ? other.getSpecialty() == null : this.getSpecialty().equals(other.getSpecialty()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getExp() == null) ? 0 : getExp().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getHoroscope() == null) ? 0 : getHoroscope().hashCode());
        result = prime * result + ((getSchoolAreaId() == null) ? 0 : getSchoolAreaId().hashCode());
        result = prime * result + ((getGrade() == null) ? 0 : getGrade().hashCode());
        result = prime * result + ((getSpecialty() == null) ? 0 : getSpecialty().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", signature=").append(signature);
        sb.append(", photo=").append(photo);
        sb.append(", phone=").append(phone);
        sb.append(", sex=").append(sex);
        sb.append(", exp=").append(exp);
        sb.append(", score=").append(score);
        sb.append(", horoscope=").append(horoscope);
        sb.append(", schoolAreaId=").append(schoolAreaId);
        sb.append(", grade=").append(grade);
        sb.append(", specialty=").append(specialty);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}