package com.scut.longerwu.graduation.service.impl;

import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.util.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

@Service("staffService")
public class StaffServiceImpl implements StaffService{
    @Resource
    private StaffDao staffDao;

    @Override
    public Staff doLogin(String account, String password) {
        password= EncryptionUtil.encryPasswd(password);
        return staffDao.findStaff(account,password);
    }
}
