package com.xiaolfeng.dormstar.mapper;

import com.xiaolfeng.dormstar.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 筱锋xiao_lfeng
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM dormstar.ds_user")
    UserEntity[] getAllUser();
}
