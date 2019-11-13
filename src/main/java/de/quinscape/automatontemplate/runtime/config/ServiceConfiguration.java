package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.runtime.domain.IdGenerator;
import de.quinscape.automaton.runtime.domain.UUIDGenerator;
import de.quinscape.automaton.runtime.domain.op.BatchStoreOperation;
import de.quinscape.automaton.runtime.domain.op.DefaultBatchStoreOperation;
import de.quinscape.automaton.runtime.domain.op.DefaultStoreOperation;
import de.quinscape.automaton.runtime.domain.op.StoreOperation;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.automaton.runtime.ws.DefaultAutomatonWebSocketHandler;
import de.quinscape.automatontemplate.runtime.service.ChatMessageHandler;
import de.quinscape.automatontemplate.runtime.service.ChatService;
import de.quinscape.domainql.DomainQL;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class ServiceConfiguration
{
    private final static Logger log = LoggerFactory.getLogger(ServiceConfiguration.class);

    private final ApplicationContext applicationContext;


    @Autowired
    public ServiceConfiguration(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }


    @Bean
    public ChatService chatService()
    {
        return new ChatService();
    }
    
    @Bean
    public AutomatonWebSocketHandler automatonWebSocketHandler(
        ChatService chatService)
    {

        final AutomatonWebSocketHandler handler = new DefaultAutomatonWebSocketHandler(
            Collections.singleton(
                new ChatMessageHandler(chatService)
            )
        );

        chatService.setAutomatonWebSocketHandler(handler);

        return handler;
    }

    @EventListener(ContextStoppedEvent.class)
    public void onContextStopped(ContextStoppedEvent event) throws IOException
    {
        final AutomatonWebSocketHandler webSocketHandler = automatonWebSocketHandler(null);
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

}
