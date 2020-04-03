package com.company;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class FileServer {

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


    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        FileServer fileServer = new FileServer();
        if(args.length > 1){
            // if the port is given as second argument
            fileServer.start(Integer.parseInt(args[1]));
        }
        else{
            //use port 8888 as default
            fileServer.start(8888);
        }
        fileServer.stop();
    }

    private class UserHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public UserHandler(Socket socket, int index) {
            this.clientSocket = socket;
        }

        //send a message to connected user
        public void sendMessage(String message){
            out.println(message);
        }

        private String getAllFiles(File curDir) {
            File[] filesList;
            filesList = curDir.listFiles();
            StringBuilder stringBuilder = new StringBuilder();
            for (File f : filesList) {
                if (f.isDirectory())
                    getAllFiles(f);
                if (f.isFile()) {
                    stringBuilder.append(f.getName());
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }

        private String parseGetCommand(String path) throws FileNotFoundException {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (sc.hasNextLine())
                stringBuilder.append(sc.nextLine());
            return stringBuilder.toString();
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                boolean done = false;
                while ((inputLine = in.readLine()) != null) {
                    String command = inputLine.split(" ")[0];
                    StringBuilder message = new StringBuilder();
                    //the first word is the command

                    switch (command){
                        case "get":
                            //todo: get content of file
                            message.append("get ");
                            message.append(parseGetCommand("./" + inputLine.split(" ")[1]));
                            break;
                        case "dir":
                            //todo: get dir contents
                            File curDir = new File(".");
                            String contents = getAllFiles(curDir);
                            sendMessage(contents);
                            break;
                        case "exit":
                            done = true;
                            break;
                        default:
                            out.println("Unsupported command");
                    }
                    if(done) break;
                    sendMessage(message.toString());
//                    System.out.println(inputLine);
//                    out.println(inputLine);
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

    }
}
