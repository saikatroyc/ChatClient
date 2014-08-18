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
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BufferedReader in = null;
        PrintWriter out = null;
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
}
