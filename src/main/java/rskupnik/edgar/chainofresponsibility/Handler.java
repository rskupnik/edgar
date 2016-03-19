package rskupnik.edgar.chainofresponsibility;

public interface Handler {
    Handler next();
    boolean handle(Object... input);
}
