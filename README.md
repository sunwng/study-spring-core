## @Transactional

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