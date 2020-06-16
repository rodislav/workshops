package org.example.saga.api;

public enum Action {
    LOCK,
    EXECUTE,
    COMMIT,
    ROLLBACK,
}
