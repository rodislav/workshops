package org.example.saga.api;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    CREATED, PLACED, FAILED
}
