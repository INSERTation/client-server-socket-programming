import java.net.*;
import java.io.*;

public class CSNETWKClient
{
    public static void main(String[] args)
    {
        String sServerAddress = args[0];
        int nPort = Integer.parseInt(args[1]);
        
        try
        {
            Socket clientEndpoint = new Socket(sServerAddress, nPort);
            System.out.println("Client: Connected to server at " + clientEndpoint.getRemoteSocketAddress());

            DataInputStream disReader = new DataInputStream(clientEndpoint.getInputStream());
            int fileLength = disReader.readInt();
            byte[] fileBytes = new byte[fileLength];
            disReader.readFully(fileBytes);
            FileOutputStream fos = new FileOutputStream("Received.txt");
            fos.write(fileBytes);
            fos.close();
            
            DataOutputStream dosWriter = new DataOutputStream(clientEndpoint.getOutputStream());
            dosWriter.writeUTF("Client: File received and saved!");

            System.out.println(disReader.readUTF());
            
            clientEndpoint.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("Client: Connection is terminated.");
        }
    }
}
