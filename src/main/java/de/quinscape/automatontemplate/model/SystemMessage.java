package de.quinscape.automatontemplate.model;

import de.quinscape.automaton.model.message.OutgoingMessage;

public class SystemMessage
{
    public final static String SYSTEM = "SYSTEM";

    private final String text;


    public SystemMessage(String text)
    {
        this.text = text;
    }


    public String getText()
    {
        return text;
    }


    public OutgoingMessage createMessage()
    {
        return new OutgoingMessage(
            SYSTEM,
            this
        );
    }
}
