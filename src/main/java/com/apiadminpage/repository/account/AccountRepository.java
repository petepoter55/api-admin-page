package com.apiadminpage.repository.account;

import com.apiadminpage.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
