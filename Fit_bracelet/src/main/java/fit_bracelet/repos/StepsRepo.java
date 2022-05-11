package fit_bracelet.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fit_bracelet.model.Steps;

@Repository
public interface StepsRepo extends JpaRepository<Steps, Long> {
}
