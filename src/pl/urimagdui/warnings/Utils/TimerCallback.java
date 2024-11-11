package pl.urimagdui.warnings.Utils;

public interface TimerCallback<E> {
    void success(E paramE);
    void error(E paramE);
}
