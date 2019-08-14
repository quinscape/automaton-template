package de.quinscape.automatontemplate.runtime;

import de.quinscape.automaton.runtime.config.AutomatonConfiguration;
import de.quinscape.automatontemplate.runtime.config.DataSourceConfiguration;
import de.quinscape.automatontemplate.runtime.config.GraphQLConfiguration;
import de.quinscape.automatontemplate.runtime.config.SecurityConfiguration;
import de.quinscape.automatontemplate.runtime.config.ServiceConfiguration;
import de.quinscape.automatontemplate.runtime.config.WebConfiguration;
import de.quinscape.automaton.runtime.config.WebsocketConfiguration;
import de.quinscape.automatontemplate.runtime.controller.JsEntryPointController;
import de.quinscape.automatontemplate.runtime.service.AutomatontemplateLogic;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(
    exclude = {
        DataSourceAutoConfiguration.class
    },
    scanBasePackageClasses = {
        AutomatontemplateLogic.class,
        JsEntryPointController.class
    }
)

@Import({
    AutomatonConfiguration.class,

    GraphQLConfiguration.class,
    WebsocketConfiguration.class,
    DataSourceConfiguration.class,
    WebConfiguration.class,
    SecurityConfiguration.class,
    ServiceConfiguration.class
})

@EnableWebSecurity(debug = false)

// TODO: change property import 
@PropertySource({"classpath:automatontemplate-${spring.profiles.active}.properties"})
public class AutomatonTemplateApplication
    extends SpringBootServletInitializer
    implements ApplicationContextAware
{
    private ApplicationContext applicationContext;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(AutomatonTemplateApplication.class);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(AutomatonTemplateApplication.class, args);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
