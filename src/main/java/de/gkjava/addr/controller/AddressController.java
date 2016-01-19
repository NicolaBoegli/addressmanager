package de.gkjava.addr.controller;

import de.gkjava.addr.persistence.model.Address;
import de.gkjava.addr.persistence.repository.AddressRepository;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * A Address Controller
 */

@Controller
public class AddressController {

    @Resource
    private AddressRepository repository;

    public List<Address> findAll() {
        return repository.findAll();
    }

    public Address findOne(Long id) {
        return repository.findOne(id);
    }

    public Address persist(Address address) {
        return repository.save(address);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
