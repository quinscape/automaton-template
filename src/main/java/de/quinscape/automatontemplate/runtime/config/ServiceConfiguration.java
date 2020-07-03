package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.model.js.StaticFunctionReferences;
import de.quinscape.automaton.runtime.domain.DomainMonitorService;
import de.quinscape.automaton.runtime.domain.IdGenerator;
import de.quinscape.automaton.runtime.domain.UUIDGenerator;
import de.quinscape.automaton.runtime.domain.op.BatchStoreOperation;
import de.quinscape.automaton.runtime.domain.op.DefaultBatchStoreOperation;
import de.quinscape.automaton.runtime.domain.op.DefaultStoreOperation;
import de.quinscape.automaton.runtime.domain.op.StoreOperation;
import de.quinscape.automaton.runtime.filter.JavaFilterTransformer;
import de.quinscape.automaton.runtime.i18n.DefaultTranslationService;
import de.quinscape.automaton.runtime.i18n.TranslationService;
import de.quinscape.automaton.runtime.merge.MergeService;
import de.quinscape.automaton.runtime.pubsub.DefaultPubSubService;
import de.quinscape.automaton.runtime.pubsub.PubSubMessageHandler;
import de.quinscape.automaton.runtime.pubsub.PubSubService;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.automaton.runtime.ws.DefaultAutomatonWebSocketHandler;
import de.quinscape.automatontemplate.domain.tables.pojos.AppTranslation;
import de.quinscape.domainql.DomainQL;
import de.quinscape.spring.jsview.loader.ResourceHandle;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.util.Collections;

import static de.quinscape.automatontemplate.domain.Tables.*;

@Configuration
public class ServiceConfiguration
{
    private final static Logger log = LoggerFactory.getLogger(ServiceConfiguration.class);

    private final ApplicationContext applicationContext;


    @Autowired
    public ServiceConfiguration(
        ApplicationContext applicationContext,
        JavaFilterTransformer javaFilterTransformer
    )
    {
        this.applicationContext = applicationContext;
    }


    @Bean
    public AutomatonWebSocketHandler automatonWebSocketHandler(
        PubSubService pubSubService,
        JavaFilterTransformer javaFilterTransformer,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultAutomatonWebSocketHandler(
            Collections.singletonList(
                new PubSubMessageHandler(domainQL, pubSubService, javaFilterTransformer)
            )
        );
    }

    @Bean
    public PubSubService pubSubService()
    {
        return new DefaultPubSubService();
    }

    @EventListener(ContextStoppedEvent.class)
    public void onContextStopped(ContextStoppedEvent event) throws IOException
    {
        final AutomatonWebSocketHandler webSocketHandler = automatonWebSocketHandler(null, null, null);
        webSocketHandler.shutDown();
    }

    @Bean
    public IdGenerator uuidGenerator()
    {
        return new UUIDGenerator();
    }

    @Bean
    public StoreOperation defaultStoreOperation(
        DSLContext dslContext,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultStoreOperation(
            dslContext,
            domainQL
        );
    }

    @Bean
    public BatchStoreOperation defaultBatchStoreOperation(
        DSLContext dslContext,
        @Lazy DomainQL domainQL
    )
    {
        return new DefaultBatchStoreOperation(
            dslContext,
            domainQL
        );
    }


    @Bean
    public TranslationService translationService(
        DSLContext dslContext,
        @Qualifier("jsFunctionReferences") ResourceHandle<StaticFunctionReferences> jsFunctionReferencesHandle
    )
    {
        return new DefaultTranslationService(
            dslContext,
            jsFunctionReferencesHandle,
            APP_TRANSLATION,
            AppTranslation.class
        );
    }


    @Bean
    public DomainMonitorService domainMonitorService()
    {
        return new DomainMonitorService(
            pubSubService()
        );
    }

    @Bean
    public MergeService mergeService(DomainQL domainQL, DSLContext dslContext)
    {
        return MergeService.build(domainQL, dslContext).buildService();
    }

}
