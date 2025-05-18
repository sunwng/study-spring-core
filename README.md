## [@Transactional](src/main/java/org/example/studyspringcore/transactional)

- How does Spring catch the `@Transactional` annotation?
    - `DefaultAdvisorChainFactory`.`getInterceptorsAndDynamicInterceptionAdvice`

      → `pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)`

      (call `TransactionAttributeSourcePointcut`.`TransactionAttributeSourceClassFilter`.`matches()`)

      → `AnnotationTransactionAttributeSource`.`isCandidateClass()`

      (implementation of `parser` is `SpringTransactionAnnotationParser`)

- How `@Transactional` annotation works?
    - Here comes `AopProxy`

      → call `AopProxy`.`proceed()`

      → `ProxyMethodInvocation`.`proceed()`

      → `TransactionInterceptor`.`invoke()`

      → `commitTransactionAfterReturning`

      → `getTransactionManager`.`commit()`

## [DeferredResult in Controller](src/main/java/org/example/studyspringcore/deferred_rsult)

- What if a transaction is a long-running, logically single unit, but distributed across multiple systems and client wants to keep checking its status?
    - the simplest way is to use polling API with short intervals
    - but, long polling offers a more elegant and resource-efficient approach and we can implement it using `DeferredResult`
- DeferredResult
    - Enables non-blocking request handling.
    - The servlet thread is released immediately after the controller method returns a DeferredResult.
    - When another thread sets the result using `deferredResult.setResult()`, the server sends the response to the client.
    - We can register additional behaviors using:
        - onTimeout (with timeoutCallback)
        - onError (with errorCallback)
        - onCompletion (with completionCallback)
- Hot it works?
    1. `DispatcherServlet.doDispatch()` is called on the initial request
    2. `RequestMappingHandlerAdapter.invokeHandlerMethod()` executes the controller method
    3. `ServletInvocableHandlerMethod.invokeAndHandle()` processes the return value
    4. `DeferredResultMethodReturnValueHandler.handleReturnValue()` detects that the type of return value is `DeferredResult` and handle it
    5. `DeferredResult` is registered to `WebAsyncManager` with the current `WebRequest`
    6. Later, another thread calls`deferredResult.setResult()`
    7. `DeferredResultHandler.handleResult()` is invoked
    8. This triggers `AsyncWebRequest.dispatch()`, which re-dispatches the servlet
    9. early return in `RequestMappingHandlerAdapter.invokeHandlerMethod()`
        - see `asyncManager.hasConcurrentResult()`

  ⇒ The dispatcher servlet is triggered again, but it skips controller execution and proceeds directly to rendering the response