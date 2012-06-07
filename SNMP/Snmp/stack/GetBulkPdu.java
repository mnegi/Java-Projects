

public abstract class GetBulkPdu extends Pdu 
{
    protected int non_repeaters = 0;
    protected int max_repetitions = 0;

public GetBulkPdu(SnmpContextBasisFace con) 
{
    super(con);
    setMsgType(AsnObject.GETBULK_REQ_MSG);
}

/**
 * Sets the non_repeaters. The non_repeaters specifies the number of
 * variables in the varbind list for which a single lexicographic
 * successor is to be returned.
 * If they are not set, the value will be 0 (zero).
 */
public void setNonRepeaters(int no)
{
    // same field as error status in any other PDU.
    non_repeaters = no;
}

/**
 * Returns the non_repeaters. 
 */
public int getNonRepeaters()
{
    return non_repeaters;
}

/**
 * Sets the max_repetitions. The max_repetitions specifies the number of
 * lexicographic successors to be returned for the remaining variables
 * in the varbind list.
 * If they are not set, the value will be 0 (zero).
 */
public void setMaxRepetitions(int no)
{
    // same field as error index in any other PDU.
    max_repetitions = no;
}

/**
 * Old method to set the max_repetitions. Was a spelling mistake,
 * it is still in here for backwards compatibility.
 *
 * @deprecated Use setMaxRepetitions(int no).
 * @see #setMaxRepetitions
 */
public void setMaxRepititions(int no)
{
    setMaxRepetitions(no);
}

/**
 * Returns the max_repetitions. 
 */
public int getMaxRepetitions()
{
    return max_repetitions;
}

/** 
 * Send the Pdu.
 *
 * <p>
 * The GetBulk request has the same format as any other request, except
 * that the error_status field is replaced by non_repeaters and the
 * error_index field is replaces by max_repetitions.
 * </p>
 * @see Pdu#send(int, int)
 */
public boolean send() throws java.io.IOException, PduException
{
    return send(non_repeaters, max_repetitions);
}

/**
 * Returns a string representation of the object.
 * @return The string
 */
public String toString()
{
    StringBuffer buffer = new StringBuffer(super.toString());
    int l = buffer.length();
    buffer.setLength(l-1);

    buffer.append(", non_rep=").append(getNonRepeaters());
    buffer.append(", max_rep=").append(getMaxRepetitions());
    buffer.append("]");
    return buffer.toString();
}

}
