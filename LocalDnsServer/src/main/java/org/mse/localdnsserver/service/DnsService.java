package org.mse.localdnsserver.service;

import org.mse.localdnsserver.model.DnsEntry;
import org.mse.localdnsserver.repository.DnsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.web.client.RestTemplate;

@Service
public class DnsService {

    /* private static final Logger logger = LoggerFactory.getLogger(DnsService.class);
     private final DnsRepository dnsRepository;

     public DnsService(DnsRepository dnsRepository) {
         this.dnsRepository = dnsRepository;
     }

     public Optional<String> resolveDomain(String domain) {
         logger.info("Resolving domain: {}", domain);
         Optional<DnsEntry> dnsEntryOptional = dnsRepository.findByDomain(domain);
         if (dnsEntryOptional.isPresent()) {
             String ipAddress = dnsEntryOptional.get().getIpAddress();
             logger.info("Resolved domain {} to IP address: {}", domain, ipAddress);
             return Optional.of(ipAddress);
         } else {
             logger.warn("Domain {} not found", domain);
             return Optional.empty();
         }
     }*/
    @Autowired
    private DnsRepository dnsRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String rootDnsServerUrl = "http://localhost:8081/dns/resolve?domain=";

    public Optional<String> resolveDomain(String domain) {
        Optional<DnsEntry> dnsEntryOptional = dnsRepository.findByDomain(domain);
        if (dnsEntryOptional.isPresent()) {
            return Optional.of(dnsEntryOptional.get().getIpAddress());
        } else {
            String rootDnsUrl = rootDnsServerUrl + domain;
            String ipAddress = restTemplate.getForObject(rootDnsUrl, String.class);
            if (ipAddress != null) {
                DnsEntry newDnsEntry = new DnsEntry();
                newDnsEntry.setDomain(domain);
                newDnsEntry.setIpAddress(ipAddress);
                dnsRepository.save(newDnsEntry);
                return Optional.of(ipAddress);
            } else {
                return Optional.empty();
            }
        }
    }



}
