package com.github.rskupnik.edgar.executor.tasks;

import com.github.rskupnik.pigeon.commons.Connection;

public abstract class ConnectionInfo implements Runnable {

    final Connection connection;

    public ConnectionInfo(Connection connection) {
        this.connection = connection;
    }
}
