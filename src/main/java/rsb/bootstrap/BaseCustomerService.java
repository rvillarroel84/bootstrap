package rsb.bootstrap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BaseCustomerService implements CustomerService {

    private final RowMapper<Customer> rowMapper = ((resultSet, i)
            -> new Customer(resultSet.getLong("id"), resultSet.getString("name")));

    private final JdbcTemplate jdbcTemplate;

    protected BaseCustomerService(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Collection<Customer> save(String... names) {
        List<Customer> customerList = new ArrayList<>();
        for(String name : names ){
            KeyHolder keyHolder  = new GeneratedKeyHolder();
            this.jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into Customers(name) values(?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, name);
                return ps;
            }, keyHolder);

            Long keyHolderKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            Customer customer = this.findById(keyHolderKey);
            Assert.notNull(name, "the name must be not null");
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public Customer findById(Long id) {

        String sql = "select * from Customers where id = ?";
        return this.jdbcTemplate.queryForObject(sql, this.rowMapper, id);
    }

    @Override
    public Collection<Customer> findAll() {
        return this.jdbcTemplate.query("Select * from Customers", rowMapper);
    }
}
