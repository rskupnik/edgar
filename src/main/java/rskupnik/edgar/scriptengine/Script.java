package rskupnik.edgar.scriptengine;

import groovy.lang.GroovyObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.util.Arrays;
import java.util.Optional;

class Script implements Handler {

    private final GroovyObject script;
    private final String[] keywords;

    private Script next;

    Script(String[] keywords, GroovyObject script) {
        this.script = script;
        this.keywords = keywords;
    }

    @Override
    public Handler next() {
        return next;
    }

    @Override
    public Optional<Object> handle(Object... input) {
        if (input == null || input.length < 1)
            return next() == null ? Optional.empty() : next.handle(input);

        if (keywords == null || keywords.length == 0)
            return next() == null ? Optional.empty() : next.handle(input);

        String cmd = (String) input[0];
        if (!shouldHandle(cmd))
            return next() == null ? Optional.empty() : next.handle(input);

        String output = (String) script.invokeMethod("act", new Object[] {cmd});

        return Optional.of(output);
    }

    private boolean shouldHandle(String cmd) {
        for (String keyword : keywords) {
            if (keyword.equals(cmd))
                return true;
        }

        return false;
    }

    void setNext(Script next) {
        this.next = next;
    }
}
