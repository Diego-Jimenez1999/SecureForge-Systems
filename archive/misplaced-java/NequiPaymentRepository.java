package secureforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secureforge.model.NequiPayment;

@Repository
public interface NequiPaymentRepository extends JpaRepository<NequiPayment, Long> {
}