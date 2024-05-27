package org.mse.localdnsserver.service;

import java.util.List;
import org.mse.localdnsserver.model.DnsEntry;
import org.mse.localdnsserver.repository.DnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DnsService {

    @Autowired
    private DnsRepository dnsRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String rootDnsServerUrl = "http://localhost:8081/dns/resolve?domain=";

    public Optional<String> resolveDomain(
        String domain
    ) {
        Optional<DnsEntry> dnsEntryOptional = dnsRepository.findByDomain(domain);
        if (dnsEntryOptional.isPresent()) {
            return Optional.of(dnsEntryOptional.get().getIpAddress());
        } else {
            try {
                String rootDnsUrl = rootDnsServerUrl + domain;
                String ipAddress = restTemplate.getForObject(rootDnsUrl, String.class);
                if (ipAddress != null) {
                    DnsEntry newDnsEntry = new DnsEntry();
                    newDnsEntry.setDomain(domain);
                    newDnsEntry.setIpAddress(ipAddress);
                    dnsRepository.save(newDnsEntry);
                    return Optional.of(ipAddress);
                }
            } catch (HttpClientErrorException.NotFound e) {

                return Optional.empty();
            }
            return Optional.empty();
        }
    }


    public DnsEntry registerDomain(
        DnsEntry dnsEntry
    ) {
        return dnsRepository.save(dnsEntry);
    }


    public List<DnsEntry> getAllDomains() {
        return dnsRepository.findAll();
    }
}
