package rsb.bootstrap.basicdi;

import rsb.bootstrap.BaseCustomerService;

import javax.sql.DataSource;

public class DataSourceCustomerService extends BaseCustomerService {

    protected DataSourceCustomerService(DataSource ds) {
        super(ds);
    }
}
