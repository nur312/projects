package sample;


import java.util.Collection;
import java.util.HashSet;

public class Innovator<T> implements IMartian<T> {
    /**
     * Создает объект с заданным кодом.
     *
     * @param code генетический тип.
     */
    public Innovator(T code) {
        this.code = code;
        children = new HashSet<>();
    }

    private T code;
    private Innovator<T> parent;
    private HashSet<Innovator<T>> children;

    /**
     * Добавляет указанного ребенка к данному новатору, если добавление
     * успешно, метод должен вернуть true.
     *
     * @param child укзанный ребенок.
     * @return удалось ли добавить.
     */
    boolean addChild(Innovator<T> child) {

        if (getAllMembers().contains(child)) {
            return false;
        } else {
            if (child.parent != null) {
                child.parent.children.remove(child);
            }
            child.parent = this;
            children.add(child);
            return true;
        }
    }

    /**
     * Удаляет указанного ребенка у данного новатора, если удаление
     * успешно, метод должен вернуть true.
     *
     * @param child укзанный ребенок.
     * @return удалось ли удалить.
     */
    boolean removeChild(Innovator<T> child) {
        if (children.contains(child)) {
            Tree.roots.add(child);
            children.remove(child);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Устанавливает значение генетического кода.
     *
     * @param code генетический код.
     */
    public void setCode(T code) {
        this.code = code;
    }

    /**
     * Установка нового родителя.
     *
     * @param other_parent новый родитель.
     */
    public void setParent(Innovator<T> other_parent) {
        if (getDescendants().contains(other_parent)) {
            return;
        }
        if (parent != null) {
            parent.children.remove(this);
        }
        parent = other_parent;
        other_parent.addChild(this);
    }

    /**
     * Устанавливает новых детей.
     *
     * @param other_children
     */
    public void setChildren(Collection<Innovator<T>> other_children) {
        var s = new HashSet<>(getAllMembers());
        s.retainAll(other_children);
        if (s.isEmpty()) {
            children = new HashSet<>(other_children);
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
     * Возвращает детей.
     *
     * @return дети.
     */
    @Override
    public Collection<IMartian<T>> getChildren() {
        HashSet<IMartian<T>> collection = new HashSet<>(children.size());
        collection.addAll(children);
        return collection;
    }

    /**
     * Возращает всех членов семьи.
     *
     * @return все члены семьи.
     */
    private Collection<IMartian<T>> getAllMembers() {
        var root = this;
        while (root.parent != null) {
            root = root.parent;
        }
        var t = root.getDescendants();
        t.add(this);
        return t;
    }

    /**
     * Возвращает всех потомков.
     *
     * @return потомки.
     */
    @Override
    public Collection<IMartian<T>> getDescendants() {
        var collection = new HashSet<Innovator<T>>();
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
        var collection = new HashSet<Innovator<T>>();
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
     * @param innovator граф.
     * @param used      помеченные.
     */
    private void Dfs(Innovator<T> innovator, HashSet<Innovator<T>> used) {
        used.add(innovator);
        for (var i : innovator.children) {
            if (!used.contains(i)) {
                Dfs(i, used);
            }
        }
    }

    @Override
    public String toString() {
        return "Innovator (" + code.getClass().getSimpleName() + ":" + code + ")";
    }
}
