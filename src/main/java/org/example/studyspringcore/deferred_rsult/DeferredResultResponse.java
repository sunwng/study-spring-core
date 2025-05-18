package org.example.studyspringcore.deferred_rsult;

public record DeferredResultResponse(String status) {
    static DeferredResultResponse ofCompleted() {
        return new DeferredResultResponse("COMPLETED");
    }

    static DeferredResultResponse ofTimeout() {
        return new DeferredResultResponse("TIMEOUT");
    }
}