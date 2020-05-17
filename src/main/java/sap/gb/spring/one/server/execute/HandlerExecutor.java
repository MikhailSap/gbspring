package sap.gb.spring.one.server.execute;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class HandlerExecutor{
    private ExecutorService executorService;


    public HandlerExecutor() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public synchronized Future<Boolean> addTask(Callable<Boolean> task) {
        return executorService.submit(task);
    }
}
