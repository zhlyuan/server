package com.ideabobo.model;

import java.io.Serializable;

public class Zuoyereplay implements Serializable {
    private Integer id;

    private String pid;

    private String note;

    private String uid;

    private String username;

    private String ndate;

    private Integer type;

    private String img;

    private String attach;

    private String rnote;

    private String defen;

    private String attach2;

    private Integer ppid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate == null ? null : ndate.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    public String getRnote() {
        return rnote;
    }

    public void setRnote(String rnote) {
        this.rnote = rnote == null ? null : rnote.trim();
    }

    public String getDefen() {
        return defen;
    }

    public void setDefen(String defen) {
        this.defen = defen == null ? null : defen.trim();
    }

    public String getAttach2() {
        return attach2;
    }

    public void setAttach2(String attach2) {
        this.attach2 = attach2 == null ? null : attach2.trim();
    }

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }
}