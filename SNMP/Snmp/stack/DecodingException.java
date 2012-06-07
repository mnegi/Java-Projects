

public class DecodingException extends PduException 
{
/** 
 * Constructs a DecodingException with no specified detail message. 
 *
 */
public DecodingException() 
{
    super();
}

/** 
 * Constructs a DecodingException with the specified detail
 * message. 
 *
 * @param str The detail message.
 */
public DecodingException(String str) 
{
    super(str);
}


}
