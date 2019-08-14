package de.quinscape.automatontemplate.runtime.service;

import de.quinscape.automaton.model.message.IncomingMessage;
import de.quinscape.automaton.runtime.message.IncomingMessageHandler;
import de.quinscape.automaton.runtime.ws.AutomatonClientConnection;
import de.quinscape.automatontemplate.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.quinscape.automatontemplate.model.ChatMessage.*;

public class ChatMessageHandler
    implements IncomingMessageHandler

{
    private final static Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);


    public static final String TYPE = "CHAT";


    private  final ChatService chatService;


    public ChatMessageHandler(ChatService chatService)
    {
        this.chatService = chatService;
    }

    @Override
    public String getMessageType()
    {
        return TYPE;
    }

    @Override
    public void handle(
        IncomingMessage msg, AutomatonClientConnection connection
    )
    {

        final Map<String, Object> payload = (Map<String, Object>) msg.getPayload();
        final String message = (String) payload.get("message");
        final String source = (String) payload.get("source");

        log.debug("Handle incoming message : {}", msg);

        chatService.receive(
            new ChatMessage(
                message,
                source
            ),
            connection
        );
    }
}
