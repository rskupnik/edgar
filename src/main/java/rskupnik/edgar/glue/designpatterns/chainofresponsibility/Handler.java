package rskupnik.edgar.glue.designpatterns.chainofresponsibility;

public interface Handler {
    Handler next();
    Object handle(Object input);
}
