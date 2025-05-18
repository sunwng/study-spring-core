package org.example.studyspringcore.transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
    * @Topic How does Spring catch the @Transactional annotation?
    * @See org.springframework.transaction.interceptor.TransactionAttributeSourcePointcut.TransactionAttributeSourceClassFilter.matches
    * */

    /**
    * @Topic How @Transactional annotation works?
    * @See org.springframework.transaction.interceptor.TransactionInterceptor.intercept
    * */
    @Transactional
    void create(String name, String email) {
        userRepository.save(new User(name, email));
    }
}
