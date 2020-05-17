package sap.gb.spring.one.server.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import sap.gb.spring.one.server.execute.Handler;
import sap.gb.spring.one.server.execute.HandlerExecutor;
import sap.gb.spring.one.server.send.Connection;

import java.net.ServerSocket;
import java.net.Socket;


@Component
public class Server {
    private final int PORT = 5000;
    private HandlerExecutor handlerExecutor;

    public Server(HandlerExecutor handlerExecutor) {
        this.handlerExecutor = handlerExecutor;
    }

    public void start() {
        Socket socket;
        Handler handler;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!!!");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected!");
                handler = createHandler();
                handler.setConnection(new Connection(socket));
                handlerExecutor.addTask(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Lookup
    public Handler createHandler() {
        return null;
    }

}
