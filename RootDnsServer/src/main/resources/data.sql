CREATE TABLE IF NOT EXISTS dns_entry (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         domain VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255) NOT NULL
    );



INSERT INTO dns_entry (domain, ip_address) VALUES ('google.com', '192.168.1.1');