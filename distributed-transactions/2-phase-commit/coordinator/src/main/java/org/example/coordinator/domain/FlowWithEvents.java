package org.example.coordinator.domain;

import org.example.customer.generated.grpc.TwoPCActionRPC;

public abstract class FlowWithEvents {

    protected Pipe onLock;
    protected Pipe onExecute;
    protected Pipe onCommit;
    protected Pipe onRollback;


    protected void trigger(TwoPCActionRPC action) {
        switch (action) {
            case LOCK:
                onLock.trigger();
                break;
            case EXECUTE:
                onExecute.trigger();
                break;
            case COMMIT:
                onCommit.trigger();
                break;
            case ROLLBACK:
                onRollback.trigger();
                break;
        }
    }
}
