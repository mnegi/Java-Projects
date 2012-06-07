
public class PassiveSnmpContext extends SnmpContext
{
public PassiveSnmpContext(String a_host, int a_port)
throws java.io.IOException
{
    super(a_host, a_port);
}

/**
 * Constructor.
 *
 * @param host The host to which the Pdu will send
 * @param port The port where the SNMP server will be
 * @param typeSocketA The type of socket to use.
 * @see SnmpContext#SnmpContext(String, int, String)
 * @see SnmpContextFace#STANDARD_SOCKET
 * @see SnmpContextFace#NETSCAPE_SOCKET
 * @see SnmpContextFace#KVM_SOCKET
 */
public PassiveSnmpContext(String a_host, int a_port, String socketType)
throws java.io.IOException
{
    super(a_host, a_port, socketType);
}

/**
 * Overrides the AbstractSnmpContext.activate() to do nothing.
 * This prevents the creation of threads in the base class.
 *
 * @see AbstractSnmpContext#activate()
 */
protected void activate()
{
    // do nothing
}

}
