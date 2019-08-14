package de.quinscape.automatontemplate.model;

import de.quinscape.automaton.model.message.OutgoingMessage;

public class ChatMessage
{
    public final static String CHAT = "CHAT";

    public final static String SYSTEM = "SYSTEM";

    private final String text;

    private final String source;


    public ChatMessage(String text, String source)
    {
        this.text = text;
        this.source = source;
    }


    public String getText()
    {
        return text;
    }


    public String getSource()
    {
        return source;
    }


    public OutgoingMessage createMessage()
    {
        return new OutgoingMessage(
            CHAT,
            this
        );
    }
}
