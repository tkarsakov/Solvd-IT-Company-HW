package com.solvd.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        //MenuSwitch menu = new MenuSwitch();
        //menu.mainMenuSwitch();
        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<CompletableFuture<Void>> connectionFutures = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            if(i < 2){
                connectionFutures.add(CompletableFuture
                        .runAsync(ConnectionTask.getConnectionTask(1000, LOGGER, connectionPool)));
                continue;
            }
            connectionFutures.add(CompletableFuture
                    .runAsync(ConnectionTask
                            .getConnectionTask(2000, LOGGER, connectionPool), executorService));
        }

        connectionFutures.stream().map(CompletableFuture::join);
        executorService.shutdown();
    }
}

