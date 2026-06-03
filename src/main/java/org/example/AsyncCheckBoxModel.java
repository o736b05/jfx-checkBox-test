package org.example;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface AsyncCheckBoxModel {
    Publisher<Boolean> getValuePublisher();
    Publisher<Void> setValuePublisher(boolean newValue);
}
