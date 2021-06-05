package sample;

import java.util.Collection;

public interface IMartian <T> {
    IMartian<T> getParent();

    Collection<IMartian<T>> getChildren();

    Collection<IMartian<T>> getDescendants();

    boolean hasChildWithValue(T value);

    boolean hasDescendantWithValue(T value);

    T getCode();

}
