package org.mse.rootdnsserver.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DnsEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String domain;
    private String ipAddress;


    public String getDomain() {
        return domain;
    }

    public String getIpAddress() {
        return ipAddress;
    }

}
