package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.runtime.config.ScopeTableConfig;
import de.quinscape.automaton.runtime.i18n.TranslationService;
import de.quinscape.automaton.runtime.provider.AutomatonJsViewProvider;
import de.quinscape.automaton.runtime.provider.ProcessInjectionService;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.domainql.DomainQL;
import de.quinscape.spring.jsview.JsViewResolver;
import de.quinscape.spring.jsview.loader.ResourceHandle;
import de.quinscape.spring.jsview.loader.ResourceLoader;
import graphql.schema.GraphQLSchema;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration
    implements WebMvcConfigurer
{

    private final Environment env;

    private final ServletContext servletContext;

    private final DomainQL domainQL;

    private final ResourceLoader resourceLoader;

    private final AutomatonWebSocketHandler automatonTestWebSocketHandler;

    private final ProcessInjectionService processInjectionService;
    private final TranslationService translationService;

    private final DSLContext dslContext;

    private final ScopeTableConfig scopeTableConfig;



    @Autowired
    public WebConfiguration(
        Environment env,
        ServletContext servletContext,
        ResourceLoader resourceLoader,
        ProcessInjectionService processInjectionService,
        TranslationService translationService,
        DSLContext dslContext,
        ScopeTableConfig scopeTableConfig,
        @Lazy Optional<AutomatonWebSocketHandler> optionalSocketHandler,
        @Lazy DomainQL domainQL
    )
    {
        this.env = env;
        this.servletContext = servletContext;
        this.domainQL = domainQL;
        this.resourceLoader = resourceLoader;

        this.automatonTestWebSocketHandler = optionalSocketHandler.orElse(null);
        this.processInjectionService = processInjectionService;
        this.translationService = translationService;
        this.dslContext = dslContext;
        this.scopeTableConfig = scopeTableConfig;
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        final GraphQLSchema graphQLSchema = domainQL.getGraphQLSchema();
        registry.viewResolver(
            JsViewResolver.newResolver(servletContext, "WEB-INF/template.html")
                .withResourceLoader(resourceLoader)

                // Process injections and general miscellaneous data we would normally
                // inject by hand in a Spring-JsView application
                .withViewDataProvider(
                    new AutomatonJsViewProvider(
                        dslContext,
                        domainQL,
                        processInjectionService,
                        translationService,
                        automatonTestWebSocketHandler,
                        scopeTableConfig
                    )
                )

                .build()
        );
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/js/**")
            .addResourceLocations("/js/", "/css/", "/webfonts/")
            .setCacheControl(CacheControl.maxAge(90, TimeUnit.DAYS));
    }
}
