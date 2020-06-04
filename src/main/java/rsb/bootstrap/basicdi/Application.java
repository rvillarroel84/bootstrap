package rsb.bootstrap.basicdi;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import rsb.bootstrap.*;


import javax.sql.DataSource;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2).build();

        DataSource initializedDataSource = DataSourceUtils.initializeDdl(dataSource);
        CustomerService cs = new DataSourceCustomerService(initializedDataSource);
        Demo.workWithCustomerService(Application.class, cs);
    }
}
