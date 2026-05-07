package secureforge.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secureforge.api.model.NequiPayment;

@Repository
public interface NequiPaymentRepository extends JpaRepository<NequiPayment, Long> {
}
