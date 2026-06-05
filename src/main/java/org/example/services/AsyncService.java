package org.example.services;
import reactor.core.publisher.Mono;

public interface AsyncService {
    // Вернет Mono с результатом операции
    Mono<Boolean> processClick(boolean newValue);
}
