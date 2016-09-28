package com.datalife.entities;

/**
 * Created by Supriya on 12/31/2015.
 */
public enum RequestStatus {
    INITIATED(0),
    INPROGRESS(1),
    PENDING(2),
    COMPLETED(3),
    CLOSED(4),
    ALLSTATUS(5);

    public final int status;

    RequestStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
