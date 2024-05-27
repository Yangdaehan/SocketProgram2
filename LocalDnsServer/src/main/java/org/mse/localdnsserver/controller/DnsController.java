package org.mse.localdnsserver.controller;

import org.mse.localdnsserver.model.DnsEntry;
import org.mse.localdnsserver.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/dns")
public class DnsController {

    @Autowired
    private DnsService dnsService;

    public DnsController(DnsService dnsService) {
        this.dnsService = dnsService;
    }

    @GetMapping("/resolve")
    public ResponseEntity<String> resolveDomain(
        @RequestParam String domain
    ) {
        Optional<String> ipAddress = dnsService.resolveDomain(domain);
        return ipAddress.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error:domain not found"));
    }

    @PostMapping("/register")
    public ResponseEntity<DnsEntry> registerDomain(
        @RequestBody DnsEntry dnsEntry
    ) {
        DnsEntry savedEntry = dnsService.registerDomain(dnsEntry);
        return ResponseEntity.ok(savedEntry);
    }

}
