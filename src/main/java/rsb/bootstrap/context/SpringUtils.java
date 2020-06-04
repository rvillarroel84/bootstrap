package rsb.bootstrap.context;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

public class SpringUtils {
    public static ConfigurableApplicationContext run(Class<T> source, String profile){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        if(StringUtils.hasText(profile)){
            applicationContext.getEnvironment().setDefaultProfiles(profile);
        }

        applicationContext.register(source);
        applicationContext.refresh();

        applicationContext.start();

        return applicationContext;
    }


}
