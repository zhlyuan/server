package com.ideabobo.model;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;

    private String username;

    private String passwd;

    private String roletype;

    private String email;

    private String tel;

    private String qq;

    private String wechat;

    private String sex;

    private String birth;

    private String img;

    private String sid;

    private String address;

    private String statecn;

    private String clientid;

    private String nickname;

    private String favs;

    private Integer money;

    private String openid;

    private String imgurl;

    private String tags;

    private String fids;

    private String qids;

    private String xuexiao;

    private String zhuanye;

    private String cuotis;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype == null ? null : roletype.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getStatecn() {
        return statecn;
    }

    public void setStatecn(String statecn) {
        this.statecn = statecn == null ? null : statecn.trim();
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid == null ? null : clientid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getFavs() {
        return favs;
    }

    public void setFavs(String favs) {
        this.favs = favs == null ? null : favs.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getFids() {
        return fids;
    }

    public void setFids(String fids) {
        this.fids = fids == null ? null : fids.trim();
    }

    public String getQids() {
        return qids;
    }

    public void setQids(String qids) {
        this.qids = qids == null ? null : qids.trim();
    }

    public String getXuexiao() {
        return xuexiao;
    }

    public void setXuexiao(String xuexiao) {
        this.xuexiao = xuexiao == null ? null : xuexiao.trim();
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public void setZhuanye(String zhuanye) {
        this.zhuanye = zhuanye == null ? null : zhuanye.trim();
    }

    public String getCuotis() {
        return cuotis;
    }

    public void setCuotis(String cuotis) {
        this.cuotis = cuotis == null ? null : cuotis.trim();
    }
}