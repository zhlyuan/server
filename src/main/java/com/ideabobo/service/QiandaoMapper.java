package com.ideabobo.service;

import com.ideabobo.model.Qiandao;

public interface QiandaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qiandao record);

    int insertSelective(Qiandao record);

    Qiandao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qiandao record);

    int updateByPrimaryKey(Qiandao record);
}