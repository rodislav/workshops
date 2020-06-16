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

    public final void lock(Pipe c) {
        onLock = c;
        doLock();
    }

    protected abstract void doLock();

    public final void execute(Pipe c) {
        onExecute = c;
        doExecute();
    }

    protected abstract void doExecute();

    public final void commit(Pipe c) {
        onCommit = c;
        doCommit();
    }

    protected abstract void doCommit();

    public final void rollback(Pipe c) {
        onRollback = c;
        doRollback();
    }

    protected abstract void doRollback();

}
