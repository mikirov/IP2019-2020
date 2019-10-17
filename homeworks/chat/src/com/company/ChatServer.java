package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ChatServer {

    private ServerSocket serverSocket;
    private final int NUM_CLIENTS = 3;
    private List<UserHandler> userHandlers = new ArrayList<>();;
    private int clientsConnected = 0;
    private Semaphore handlersLock = new Semaphore(1, true);

    public void start(int port) throws IOException, InterruptedException {
        serverSocket = new ServerSocket(port);

        while (true) {

            Thread.sleep(1);
            if(clientsConnected < NUM_CLIENTS){
                Socket clientSocket = serverSocket.accept();
                UserHandler userHandler = new UserHandler(clientSocket, clientsConnected + 1);
                userHandler.start();
                handlersLock.acquire();

                userHandlers.add(userHandler);
                clientsConnected++;

                handlersLock.release();
            }
        }
    }

    private String getFormattedUserInfo(){
        StringBuilder output = new StringBuilder();
        output.append("users: [");
        for(int i = 0; i < userHandlers.size(); i++){
            output.append(userHandlers.get(i).getUsername());
            if(i != userHandlers.size() - 1){
                output.append(", ");
            }
        }
        output.append("]");
        return output.toString();
    }

    private void sendBroadcastMessage(String message){
        for(UserHandler userHandler : userHandlers){
            userHandler.sendMessage(message);
        }
    }

    private void sendUnicastMessage(String message, String recipient){
        for(UserHandler userHandler : userHandlers){
            if(userHandler.getUsername().equals(recipient)){
                userHandler.sendMessage(message);
            }
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatServer chatServer = new ChatServer();
        if(args.length > 1){
            // if the port is given as second argument
            chatServer.start(Integer.parseInt(args[1]));
        }
        else{
            //use port 8888 as default
            chatServer.start(8888);
        }
        chatServer.stop();
    }

    private class UserHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private int index;

        private String username;

        public UserHandler(Socket socket, int index) {
            this.clientSocket = socket;
            this.index = index;
            this.username = "user" + index;
        }

        //send a message to connected user
        public void sendMessage(String message){
            out.println(message);
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                sendBroadcastMessage("---connected " + getUsername() + "---");

                String inputLine;
                boolean done = false;
                while ((inputLine = in.readLine()) != null) {
                    String command = inputLine.split(" ")[0];
                    StringBuilder message = new StringBuilder();
                    //the first word is the command
                    switch (command){
                        case "name":
                            tryChangeUsername(index - 1, inputLine.substring(4));
                            break;
                        case "info":
                            out.println(getFormattedUserInfo());
                            break;
                        case "exit":
                            sendBroadcastMessage("---disconnected " + getUsername() + "---");
                            done = true;
                            break;
                        case "all":
                            message.append("[");
                            message.append(getUsername());
                            message.append("]");
                            message.append(" (all)");
                            message.append(inputLine.substring(3));
                            sendBroadcastMessage(message.toString());
                            break;
                        case "private":
                            message.append("[");
                            message.append(getUsername());
                            message.append("]");
                            message.append(" (private)");

                            String recipientName = inputLine.split(" ")[1];
                            inputLine = inputLine.substring(8 + recipientName.length());
                            message.append(inputLine);
                            sendUnicastMessage(message.toString(), recipientName);
                            break;
                        default:
                            out.println("---error---");
                            //out.println("Unsupported command");
                    }
                    if(done) break;
                    //System.out.println(inputLine);
                    //out.println(inputLine);
                }

                handlersLock.acquire();

                userHandlers.remove(this);
                clientsConnected--;

                handlersLock.release();
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username){
            this.username = username;
        }
    }

    private void tryChangeUsername(int index, String name) {
        //make sure that the new name is unique and only set if it is
        for(UserHandler userHandler: userHandlers){
            if(userHandler.getUsername().equals(name)) return;
        }
        userHandlers.get(index).setUsername(name);
    }
}
