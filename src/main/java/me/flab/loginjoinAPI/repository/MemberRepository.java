package me.flab.loginjoinAPI.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

    @Insert("")
    int insert();
}
