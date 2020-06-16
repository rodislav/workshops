package org.example.saga.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DebitCustomerResponse {
    private Action action;
    private CustomerDTO customer;
}
