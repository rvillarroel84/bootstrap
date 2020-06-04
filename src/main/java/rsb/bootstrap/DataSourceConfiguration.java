package rsb.bootstrap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;

@Configuration
public class DataSourceConfiguration {

    @Configuration
    @Profile("prod")
    @PropertySource("application-prod.properties")
    public static class ProductionConfiguration {
        @Bean
        DataSource productionDataSource(@Value("${spring.datasource.url}") String url,
                                        @Value("${spring.datasource.username}") String username,
                                        @Value("${spring.datasource.password}") String pasword,
                                        @Value("${spring.datasource.driver-class-name}") Class<Driver> driverClass) {

            DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, pasword);
            dataSource.setDriverClassName(driverClass.getName());
            return dataSource;
        }
    }

    @Configuration
    @Profile("default")
    @PropertySource("application.properties")
    public static class DevelopmentConfiguration {
        @Bean
        DataSource developmentDataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }
        @Bean
        DataSourcePostProcessor dataSourcePostProcessor(){
            return new DataSourcePostProcessor();
        }

        public static class DataSourcePostProcessor implements BeanPostProcessor{

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

                if(bean instanceof DataSource){
                    DataSourceUtils.initializeDdl(DataSource.class.cast(bean));
                }
                return bean;
            }
        }

    }

}
