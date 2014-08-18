import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class client {
    static Socket server;
    static int destPort = 12345;
    static String clientName;
    static boolean validName = false;
    static boolean chatEnded = false;
    static BufferedReader in = null;
    static PrintWriter out = null;

    public static void main(String[] args) {
        try {
            server = new Socket("localhost", destPort);
            in = new BufferedReader(new InputStreamReader(
                    server.getInputStream()));
            out = new PrintWriter(server.getOutputStream(), true);
            BufferedReader sIn = new BufferedReader(new InputStreamReader(System.in));            
            while(true) {
                String line = in.readLine();
                System.out.println(line);
                if (line == null) break;
                if (line.contains("SUBMIT_USERNAME")) {
                    out.println(sIn.readLine());
                } else if (line.contains("NAME_ACCEPTED")){
                    validName = true;
                    System.out.println("user name accepted by user");
                    break;
                }
            }

            // now that client has registered start a new thread
            // which will listen to server broadcasts
            new ServerChatHandler().start();
            while(true) {
                System.out.println("enter ur chat:");
                String user = sIn.readLine();
                out.println(user);
                if (user != null && user.equals("EXIT_CLIENT")) {
                    System.out.println("client exiting");
                    break;
                }
              }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("END SESSION!!");
        }
    }
    
    public static class ServerChatHandler extends Thread { 
        ServerChatHandler() {}
        public void run() {
            if (in == null) return;
            String line;
            try {
                while ((line = in.readLine()) != null
                            && chatEnded == false) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }
            
        }
        
    }
}
