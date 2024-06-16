import java.net.*;
import java.io.*;

public class CSNETWKServer
{
    public static void main(String[] args)
    {
        int nPort = Integer.parseInt(args[0]);
        System.out.println("Server: Listening on port " + args[0] + "...");
        ServerSocket serverSocket;
        Socket serverEndpoint;

        try 
        {
            serverSocket = new ServerSocket(nPort);
            serverEndpoint = serverSocket.accept();
            
            System.out.println("Server: New client connected: " + serverEndpoint.getRemoteSocketAddress());
            
            DataOutputStream dosWriter = new DataOutputStream(serverEndpoint.getOutputStream());
            File file = new File("Download.txt");
            byte[] fileBytes = new byte[(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(fileBytes, 0, fileBytes.length);
            
            dosWriter.writeInt(fileBytes.length);
            dosWriter.write(fileBytes, 0, fileBytes.length);
            
            DataInputStream disReader = new DataInputStream(serverEndpoint.getInputStream());
            System.out.println(disReader.readUTF());
            
            dosWriter.writeUTF("Server: File downloaded successfully!");

            serverEndpoint.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("Server: Connection is terminated.");
        }
    }
}
