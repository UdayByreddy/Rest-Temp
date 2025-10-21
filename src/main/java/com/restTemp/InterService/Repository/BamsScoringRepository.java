package com.restTemp.InterService.Repository;
import com.restTemp.InterService.Entity.BamsScoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BamsScoringRepository extends JpaRepository<BamsScoring, Long> {

    Optional<BamsScoring> findByBamsId(String bamsId);

    boolean existsByBamsId(String bamsId);
}
