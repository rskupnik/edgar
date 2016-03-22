package rskupnik.edgar.scriptengine;

import groovy.lang.GroovyObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.util.Arrays;

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
    public boolean handle(Object... input) {
        if (input == null || input.length < 1)
            return next() == null ? false : next.handle(input);

        if (keywords == null || keywords.length == 0)
            return next() == null ? false : next.handle(input);

        String cmd = (String) input[0];
        if (!shouldHandle(cmd))
            return next() == null ? false : next.handle(input);

        return true;
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
