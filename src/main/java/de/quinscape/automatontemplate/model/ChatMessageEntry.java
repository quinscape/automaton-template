package de.quinscape.automatontemplate.model;

public class ChatMessageEntry
{
    private final ChatMessage message;
    private final String timestamp;


    public ChatMessageEntry(ChatMessage message, String timestamp)
    {
        this.message = message;
        this.timestamp = timestamp;
    }


    public ChatMessage getMessage()
    {
        return message;
    }


    public String getTimestamp()
    {
        return timestamp;
    }
}
