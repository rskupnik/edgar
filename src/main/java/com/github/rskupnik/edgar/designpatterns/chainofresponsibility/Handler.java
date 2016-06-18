package com.github.rskupnik.edgar.designpatterns.chainofresponsibility;

import java.util.Optional;

public interface Handler {
    Handler next();
    Optional<Object> handle(Object... input);
}
