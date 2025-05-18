package org.example.studyspringcore.deferred_rsult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** How it works?
 * @see org.springframework.web.servlet.DispatcherServlet#doDispatch
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#invokeHandlerMethod
 * @see org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod#invokeAndHandle
 * @see org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler#handleReturnValue
 * @see org.springframework.web.context.request.async.DeferredResult.DeferredResultHandler#handleResult
 * @see org.springframework.web.context.request.async.AsyncWebRequest#dispatch
 *
 * */
@RestController
public class DeferredResultTestController {
    private final Map<Long, DeferredResult<DeferredResultResponse>> results = new ConcurrentHashMap<>();

    @GetMapping("/deferred-results/{resultId}")
    public DeferredResult<DeferredResultResponse> queryLongProcessedResult(
        @PathVariable Long resultId
    ) {
        /**
         * @see org.springframework.web.context.request.async.DeferredResult
         * @see org.springframework.web.context.request.async.DeferredResult.timeoutResult
         * @see org.springframework.web.context.request.async.DeferredResult.timeoutCallback
         * @see org.springframework.web.context.request.async.DeferredResult.errorCallback
         * @see org.springframework.web.context.request.async.DeferredResult.completionCallback
         * */
        DeferredResult<DeferredResultResponse> deferred = new DeferredResult<>(
            10000L, DeferredResultResponse::ofTimeout
        );
        results.put(resultId, deferred);
        return deferred;
    }

    @PostMapping("/deferred-results/{resultId}/processed")
    public void completeLongProcess(
        @PathVariable Long resultId
    ) {
        /**
         * @see org.springframework.web.context.request.async.DeferredResult#setResult
         * */
        DeferredResult<DeferredResultResponse> deferred = results.get(resultId);
        deferred.setResult(DeferredResultResponse.ofCompleted());
    }
}
