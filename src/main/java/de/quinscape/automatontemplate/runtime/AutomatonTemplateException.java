package de.quinscape.automatontemplate.runtime;

public class AutomatonTemplateException
    extends RuntimeException
{
    private static final long serialVersionUID = 2074553315181786207L;

    public AutomatonTemplateException(String message)
    {
        super(message);
    }


    public AutomatonTemplateException(String message, Throwable cause)
    {
        super(message, cause);
    }


    public AutomatonTemplateException(Throwable cause)
    {
        super(cause);
    }


    public AutomatonTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
