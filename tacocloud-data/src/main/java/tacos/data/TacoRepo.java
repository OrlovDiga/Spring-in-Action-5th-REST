package tacos.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tacos.Taco;

/**
 * @author Orlov Diga
 */
@Repository
public interface TacoRepo extends PagingAndSortingRepository<Taco, Long> {
}
