package utils.mapper;

import java.sql.ResultSet;

public interface baseMapper<T> {
    public T toModel(ResultSet s);
}
