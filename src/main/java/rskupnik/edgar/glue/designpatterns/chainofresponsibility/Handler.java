package rskupnik.edgar.glue.designpatterns.chainofresponsibility;

import java.util.Optional;

public interface Handler {
    Handler next();
    Optional<Object> handle(Object... input);
}
