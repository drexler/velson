package com.drexler.velson;

/**
 * The TransformException is thrown when a transform fails.
 *
 * @author drexler
 */
public class TransformException extends RuntimeException
{
   /** Serialization ID */
   private static final long serialVersionUID = 0;

   /**
    * Constructs a TransformException with an explanatory message.
    *
    * @param message
    *            Detail about the reason for the exception.
    */
   public TransformException(final String message)
   {
      super(message);
   }

   /**
    * Constructs a TransformException with an explanatory message and cause.
    *
    * @param message
    *            Detail about the reason for the exception.
    * @param cause
    *            The cause.
    */
   public TransformException(final String message, final Throwable cause)
   {
      super(message, cause);
   }

   /**
    * Constructs a new TransformException with the specified cause.
    *
    * @param cause
    *            The cause.
    */
   public TransformException(final Throwable cause)
   {
      super(cause.getMessage(), cause);
   }
}
