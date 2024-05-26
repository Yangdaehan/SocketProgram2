package org.mse.rootdnsserver.repository;


import java.util.Optional;
import org.mse.rootdnsserver.model.DnsEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DnsRepository extends JpaRepository<DnsEntry, Long> {
    Optional<DnsEntry> findByDomain(String domain);
}


