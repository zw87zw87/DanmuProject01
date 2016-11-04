package com.danmu.socket;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket socket;
    public ServerHandler(Socket socket){
        this.socket=socket;
    }
    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut=socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
    private BufferedReader getReader(Socket socket) throws IOException{
        InputStream socketIn=socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public String echo(String msg){
        return "echo:"+msg;
    }
    public void run(){
        try {
            System.out.println("1111111111");
            System.out.println("New connection accepted "+socket.getInetAddress()+":"+socket.getPort());
            BufferedReader br=getReader(socket);
            PrintWriter pw=getWriter(socket);
            String msg=null;
            while((msg=br.readLine())!=null){
                System.out.println(msg);
                pw.println(echo(msg));
                if(msg.equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
