package rskupnik.edgar.glue.designpatterns.chainofresponsibility;

public interface Handler {
    Handler next();
    boolean handle(Object... input);
}
