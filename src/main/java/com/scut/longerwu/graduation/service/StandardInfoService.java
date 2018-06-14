package com.scut.longerwu.graduation.service;

import com.scut.longerwu.graduation.models.*;

import java.util.*;

public interface StandardInfoService {
    List<StandardInfo> getStandardInfoListByIcs(Set<String> icsSet);

    StandardInfo getStandardById(Integer id);

    StandardInfo getStandardByNumber(String number);
}
