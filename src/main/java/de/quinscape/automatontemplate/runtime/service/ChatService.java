package de.quinscape.automatontemplate.runtime.service;

import de.quinscape.automaton.model.message.OutgoingMessage;
import de.quinscape.automaton.runtime.message.ConnectionListener;
import de.quinscape.automaton.runtime.ws.AutomatonClientConnection;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.automatontemplate.model.ChatMessage;
import de.quinscape.automatontemplate.model.ChatMessageEntry;
import de.quinscape.automatontemplate.model.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatService
    implements ConnectionListener
{
    private final static Logger log = LoggerFactory.getLogger(ChatService.class);


    private AutomatonWebSocketHandler automatonWebSocketHandler;
    private final List<ChatMessageEntry> history = new CopyOnWriteArrayList<>();
    private final List<ChatMessageEntry> historyRO = Collections.unmodifiableList(history);


    public void setAutomatonWebSocketHandler(AutomatonWebSocketHandler automatonWebSocketHandler)
    {
        this.automatonWebSocketHandler = automatonWebSocketHandler;
        automatonWebSocketHandler.register(this);
    }


    @Override
    public void onClose(
        AutomatonWebSocketHandler webSocketHandler, AutomatonClientConnection connection
    )
    {
        for (AutomatonClientConnection conn : automatonWebSocketHandler.getConnections())
        {
            conn.send(new ChatMessage(connection.getAuth().getLogin() + " left.", ChatMessage.SYSTEM).createMessage());
        }
    }


    @Override
    public void onOpen(
        AutomatonWebSocketHandler webSocketHandler, AutomatonClientConnection connection
    )
    {
        // don't do this in a production environment
        new Thread(() -> {

            try
            {
                Thread.sleep(3000);

                connection.send(
                    new SystemMessage("Welcome @"  + connection.getAuth().getLogin() + "")
                        .createMessage()
                );
            }
            catch (InterruptedException e)
            {
                log.info("Interrupted", e);
            }

        }).start();

        final OutgoingMessage message = new ChatMessage(
            connection.getAuth().getLogin() + " joined.",
            ChatMessage.SYSTEM
        ).createMessage();

        broadcast(message, connection);
    }


    public void broadcast(OutgoingMessage message, AutomatonClientConnection exclude)
    {
        for (AutomatonClientConnection curr : automatonWebSocketHandler.getConnections())
        {
            if (exclude == null || !curr.getConnectionId().equals(exclude.getConnectionId()))
            {
                curr.send(
                    message);
            }
        }
    }


    public void receive(ChatMessage message, AutomatonClientConnection connection)
    {
        history.add(
            new ChatMessageEntry(
                message,
                ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT )
            )
        );

        broadcast(message.createMessage(), connection);
    }


    public List<ChatMessageEntry> getHistory()
    {
        return historyRO;
    }


    public AutomatonWebSocketHandler getWebSocketHandler()
    {
        return automatonWebSocketHandler;
    }
}
