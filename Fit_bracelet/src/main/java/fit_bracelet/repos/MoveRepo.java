package fit_bracelet.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fit_bracelet.model.Move;

@Repository
public interface MoveRepo extends JpaRepository<Move, Long> {
}
