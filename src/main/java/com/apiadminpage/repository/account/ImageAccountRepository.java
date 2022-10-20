package com.apiadminpage.repository.account;

import com.apiadminpage.entity.account.ImageAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageAccountRepository extends JpaRepository<ImageAccount, Integer> {
    ImageAccount findByUserId(String userId);
}
