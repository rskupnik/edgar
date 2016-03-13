package rskupnik.edgar.chainofresponsibility;

public interface Handler {
    Handler next();
    Object handle(Object input);
}
