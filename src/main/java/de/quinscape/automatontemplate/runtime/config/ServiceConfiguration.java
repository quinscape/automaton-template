package de.quinscape.automatontemplate.runtime.config;

import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandlerImpl;
import de.quinscape.automatontemplate.runtime.service.ChatMessageHandler;
import de.quinscape.automatontemplate.runtime.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        final AutomatonWebSocketHandler handler = new AutomatonWebSocketHandlerImpl(
            Collections.singleton(
                new ChatMessageHandler(chatService)
            )
        );

        chatService.setAutomatonWebSocketHandler(handler);

        return handler;
    }


}
