

import java.util.*;

class SnmpContextPoolItem 
{
    private SnmpContextBasisFace context = null;
    private int counter = 0;

/**
 * Constructor.
 *
 * @param con The context
 */
SnmpContextPoolItem(SnmpContextBasisFace con)
{
    context = con;
    counter = 0;
}


SnmpContextBasisFace getContext()
{
    return context;
}

int getCounter()
{
    return counter;
}

void setCounter(int i)
{
    counter = i;
}

/**
 * Returns a string representation of the object.
 * @return The string
 */
public String toString()
{
    StringBuffer buffer = new StringBuffer("SnmpContextPoolItem[");
    buffer.append("context=").append(context.toString());
    buffer.append(", counter=").append(counter);
    buffer.append("]");
    return buffer.toString();
}


}
