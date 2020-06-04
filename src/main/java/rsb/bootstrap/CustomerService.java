package rsb.bootstrap;
import java.util.Collection;
import java.util.List;


public interface CustomerService {
    Collection<Customer> save(String... name);
    Customer findById(Long id);
    Collection<Customer> findAll();
}
