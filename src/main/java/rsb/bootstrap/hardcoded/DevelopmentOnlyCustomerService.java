package rsb.bootstrap.hardcoded;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import rsb.bootstrap.BaseCustomerService;
import rsb.bootstrap.DataSourceUtils;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

public class DevelopmentOnlyCustomerService extends BaseCustomerService {

    public DevelopmentOnlyCustomerService(){
        super(buildDataSource());
    }
//    protected DevelopmentOnlyCustomerService(DataSource ds) {
//        super(ds);
//    }
    private static DataSource buildDataSource(){
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2).build();

        return DataSourceUtils.initializeDdl(dataSource);
    }
}
