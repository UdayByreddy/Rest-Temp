package com.restTemp.InterService.Repository;
import com.restTemp.InterService.Entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {

    Optional<AccountDetails> findByBamsId(Integer bamsId);

    boolean existsByBamsId(Integer bamsId);
}
