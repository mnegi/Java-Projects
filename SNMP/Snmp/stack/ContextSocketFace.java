

import java.io.*;

public interface ContextSocketFace 
{
    
public void create(int port) throws IOException;

public void create(String host, int port) throws IOException;

public String getHostAddress();

public ByteArrayInputStream receive(int maxRecvSize) throws IOException;

public void send(byte[] packet) throws IOException;
public void close();

}
