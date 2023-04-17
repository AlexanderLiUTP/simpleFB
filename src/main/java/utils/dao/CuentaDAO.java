package utils.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import productos.Cuenta;
public class CuentaDAO implements CrudDAO<Cuenta, Long>{

    @Override
    public void save(Cuenta model) {

    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Cuenta> findAll() {
        return null;
    }

    @Override
    public Cuenta update(Cuenta model) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
