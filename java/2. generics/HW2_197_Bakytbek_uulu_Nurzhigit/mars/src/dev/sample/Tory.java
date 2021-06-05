package sample;

import java.util.Collection;
import java.util.HashSet;

public class Tory<T> implements IMartian<T> {

    private final T code;
    private final Tory<T> parent;
    private final HashSet<Tory<T>> children;

    /**
     * Создает объект из новатора.
     *
     * @param innovator новатор.
     */
    public Tory(Innovator<T> innovator) {
        code = innovator.getCode();
        parent = null;
        children = new HashSet<>();
        for (var i : innovator.getChildren()) {
            children.add(new Tory<T>((Innovator<T>) i, this));
        }
    }

    /**
     * Создает объект из новатора, устанавливает родителя.
     *
     * @param innovator новатор.
     * @param parent    родитель.
     */
    private Tory(Innovator<T> innovator, Tory<T> parent) {
        code = innovator.getCode();
        this.parent = parent;
        children = new HashSet<>();
        for (var i : innovator.getChildren()) {
            children.add(new Tory<T>((Innovator<T>) i, this));
        }
    }

    /**
     * Возращает родителя.
     *
     * @return родитель
     */
    @Override
    public IMartian<T> getParent() {
        return parent;
    }

    /**
     * Возращает всех членов семьи.
     *
     * @return все члены семьи.
     */
    @Override
    public Collection<IMartian<T>> getChildren() {
        var collection = new HashSet<IMartian<T>>(children.size());
        collection.addAll(children);
        return collection;
    }

    /**
     * Возвращает всех потомков.
     *
     * @return потомки.
     */
    @Override
    public Collection<IMartian<T>> getDescendants() {
        var collection = new HashSet<Tory<T>>();
        Dfs(this, collection);
        var c = new HashSet<IMartian<T>>(collection.size());
        c.addAll(collection);
        c.remove(this);
        return c;
    }

    /**
     * Возращает, есть ли ребенок с данным генетическим кодом.
     *
     * @param value код
     * @return результат поиска.
     */
    @Override
    public boolean hasChildWithValue(T value) {
        for (var i : children) {
            if (i.code == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возращает, есть ли потомок с данным генетическим кодом.
     *
     * @param value код
     * @return результат поиска.
     */
    @Override
    public boolean hasDescendantWithValue(T value) {
        var collection = new HashSet<Tory<T>>();
        Dfs(this, collection);
        for (var i : collection) {
            if (i.code == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает генетический код.
     *
     * @return код.
     */
    @Override
    public T getCode() {
        return code;
    }

    /**
     * Алгоритм обхода графа.
     *
     * @param tory граф.
     * @param used помеченные.
     */
    public void Dfs(Tory<T> tory, HashSet<Tory<T>> used) {
        used.add(tory);
        for (var i : tory.children) {
            if (!used.contains(i)) {
                Dfs(i, used);
            }
        }
    }

    @Override
    public String toString() {
        return "Tory (" + code.getClass().getSimpleName() + ":" + code + ")";
    }
}
