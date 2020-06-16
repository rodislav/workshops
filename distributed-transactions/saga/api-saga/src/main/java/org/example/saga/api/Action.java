package org.example.saga.api;

import java.io.Serializable;

public enum Action implements Serializable {
    EXECUTE,
    ERROR,
    ROLLBACK,
}
