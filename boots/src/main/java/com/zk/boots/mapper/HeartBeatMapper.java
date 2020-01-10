package com.zk.boots.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HeartBeatMapper {

    public Integer dbHit();
}
