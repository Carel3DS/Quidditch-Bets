package es.dws.quidditch.model;

public enum Status {
    OPEN,STARTED,FINISHED,CANCELLED;

    public Status next() {
        Status status;
        switch (this){
            case OPEN -> status = STARTED;
            case STARTED -> status = FINISHED;
            default -> status = CANCELLED;
        }
        return status;
    }
}
