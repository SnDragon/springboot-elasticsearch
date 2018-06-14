package com.scut.longerwu.graduation.dao;

import com.scut.longerwu.graduation.models.*;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.*;

@Mapper
public interface StaffDao {
    Staff findStaff(@Param("account") String account,
                    @Param("password") String password);
}
