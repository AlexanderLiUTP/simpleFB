package utils.dao;

import personas.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteDAO implements CrudDAO<Cliente, Long>{
    @Override
    public void save(Cliente model) {

    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Cliente> findAll() {
        return null;
    }

    @Override
    public Cliente update(Cliente model) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
