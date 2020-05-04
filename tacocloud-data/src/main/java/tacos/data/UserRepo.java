package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tacos.User;

/**
 * @author Orlov Diga
 */
@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
