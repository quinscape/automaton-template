package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.runtime.config.ScopeTableConfig;
import de.quinscape.automaton.runtime.i18n.TranslationService;
import de.quinscape.automaton.runtime.provider.AutomatonJsViewProvider;
import de.quinscape.automaton.runtime.provider.ProcessInjectionService;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.automatontemplate.model.ChatMessageEntry;
import de.quinscape.automatontemplate.runtime.service.ChatService;
import de.quinscape.domainql.DomainQL;
import de.quinscape.spring.jsview.JsViewResolver;
import de.quinscape.spring.jsview.loader.ResourceLoader;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Configuration
public class WebConfiguration
    implements WebMvcConfigurer
{

    private final static int LIMIT = 20;

    private final Environment env;

    private final ServletContext servletContext;

    private final DomainQL domainQL;

    private final ResourceLoader resourceLoader;

    private final AutomatonWebSocketHandler automatonWebSocketHandler;

    private final ProcessInjectionService processInjectionService;
    private final TranslationService translationService;

    private final DSLContext dslContext;

    private final ScopeTableConfig scopeTableConfig;
    private final ChatService chatService;



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
        @Lazy DomainQL domainQL,
        @Lazy ChatService chatService
    )
    {
        this.env = env;
        this.servletContext = servletContext;
        this.domainQL = domainQL;
        this.resourceLoader = resourceLoader;

        this.automatonWebSocketHandler = optionalSocketHandler.orElse(null);
        this.processInjectionService = processInjectionService;
        this.translationService = translationService;
        this.dslContext = dslContext;
        this.scopeTableConfig = scopeTableConfig;
        this.chatService = chatService;
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
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
                        automatonWebSocketHandler,
                        scopeTableConfig
                    )
                )
                .withViewDataProvider(
                    ctx -> {
                        final List<ChatMessageEntry> history = chatService.getHistory();
                        final List<ChatMessageEntry> recentHistory = history.size() < LIMIT ? history : history.subList(
                            history.size() - LIMIT,
                            history.size()
                        );

                        ctx.provideViewData("chatHistory",
                            recentHistory
                        );
                    }
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
