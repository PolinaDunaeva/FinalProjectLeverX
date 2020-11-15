package dunaeva.polina.finalproject.repository;

import dunaeva.polina.finalproject.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {

}
