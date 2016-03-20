package rskupnik.edgar.scriptengine;

import groovy.lang.GroovyObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rskupnik.edgar.glue.designpatterns.chainofresponsibility.Handler;

import java.util.Arrays;

class Script implements Handler {

    private static final Logger log = LogManager.getLogger(Script.class);

    private final GroovyObject script;
    private final String[] keywords;

    private Script next;

    Script(String[] keywords, GroovyObject script) {
        this.script = script;
        this.keywords = keywords;
    }

    @Override
    public Handler next() {
        return null;
    }

    @Override
    public boolean handle(Object... input) {
        return false;
    }

    void setNext(Script next) {
        this.next = next;
        log.debug(Arrays.toString(keywords)+" next: "+next);
    }
}
