package com.miu.onlinemarketplace.repository;

import com.miu.onlinemarketplace.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
