package com.banquito.banquitoApp.utils.mapper;

import java.sql.ResultSet;
import java.util.Map;

public interface BaseMapper <T>{
    public T toModel(ResultSet s);
    public T fromJSON(Map<String, Object> json);
}
