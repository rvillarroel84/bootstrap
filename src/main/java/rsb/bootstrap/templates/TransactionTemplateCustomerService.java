package rsb.bootstrap.templates;

import org.springframework.transaction.support.TransactionTemplate;
import rsb.bootstrap.BaseCustomerService;
import rsb.bootstrap.Customer;

import javax.sql.DataSource;
import java.util.Collection;

public class TransactionTemplateCustomerService extends BaseCustomerService {

    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateCustomerService(DataSource ds, TransactionTemplate tt) {
        super(ds);
        this.transactionTemplate = tt;
    }

    @Override
    public Collection<Customer> save(String... names) {
        return this.transactionTemplate.execute(s-> super.save(names));
    }

    @Override
    public Customer findById(Long id) {
        return  this.transactionTemplate.execute(transactionStatus -> super.findById(id));
    }

    @Override
    public Collection<Customer> findAll() {
        return this.transactionTemplate.execute(transactionStatus -> super.findAll());
    }

}
