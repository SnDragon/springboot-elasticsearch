package com.scut.longerwu.graduation.dao;

import com.scut.longerwu.graduation.models.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface SchemassDao {

    Schemass findSchemassById(@Param("id") Integer id);

    List<Schemass> findSchemassByName(@Param("name") String name);
}
