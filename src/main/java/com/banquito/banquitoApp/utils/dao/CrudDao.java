package com.banquito.banquitoApp.utils.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudDao <T extends Serializable,K>{
    boolean save(T model);
    Optional<T> findById(K id);
    List<T> findAll();
    boolean update(T model);
    boolean delete(Long id);
    void deleteAll();
}
