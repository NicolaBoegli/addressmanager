package de.gkjava.addr.persistence.repository;

import de.gkjava.addr.persistence.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Address Repository
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
