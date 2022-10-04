package com.apiadminpage.repository.account;

import com.apiadminpage.entity.account.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Integer> {
    AccountInfo findByUserId(String userId);
}
