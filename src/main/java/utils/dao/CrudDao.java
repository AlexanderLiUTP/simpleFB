package utils.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudDao <T extends Serializable,K>{
    void save(T model);
    Optional<T> findById(K id);
    List<T> findAll();
    T update(T model);
    void delete(Long id);
    void deleteAll();
}
