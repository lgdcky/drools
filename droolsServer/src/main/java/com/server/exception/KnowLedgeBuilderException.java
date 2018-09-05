package com.server.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/19/18
 * Time: 3:34 PM
 */
public class KnowLedgeBuilderException extends SystemException{


    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public KnowLedgeBuilderException(String message) {
        super(message);
    }
}
