package org.mse.rootdnsserver.controller;


import java.util.List;
import org.mse.rootdnsserver.model.DnsEntry;
import org.mse.rootdnsserver.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/dns")
public class DnsController {


    @Autowired
    private DnsService dnsService;


    @GetMapping("/resolve")
    public ResponseEntity<String> resolveDomain(
        @RequestParam String domain
    ) {
        Optional<String> ipAddress = dnsService.resolveDomain(domain);
        return ipAddress.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(404).body(domain));
    }


    @GetMapping("/domains")
    public ResponseEntity<List<DnsEntry>> getAllDomains() {
        return ResponseEntity.ok(dnsService.getAllDomains());
    }


}
