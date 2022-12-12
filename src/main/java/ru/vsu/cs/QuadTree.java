package ru.vsu.cs;

public class QuadTree<Key extends Comparable<Key>, Value> implements IQuadTree<Key, Value> {

    private Node root;

    private class Node {
        Key x, y;              // координаты x и y
        Node NW, NE, SE, SW;   // четыре поддерева
        Value value;           // связанные данные

        /**
         *
         * @param x
         * @param y
         * @param value
         */
        Node(Key x, Key y, Value value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    @Override
    public void insert(Key x, Key y, Value value) {
        root = insert(root, x, y, value);
    }

    //возможны 4 результата: координаты либо больше, либо меньше текущего -> вызов insert() для дочернего узла
    private Node insert(Node root, Key x, Key y, Value value) {
        if (root == null) return new Node(x, y, value);

        else if (less(x, root.x) && less(y, root.y)) root.SW = insert(root.SW, x, y, value);
        else if (less(x, root.x) && !less(y, root.y)) root.NW = insert(root.NW, x, y, value);
        else if (!less(x, root.x) && less(y, root.y)) root.SE = insert(root.SE, x, y, value);
        else if (!less(x, root.x) && !less(y, root.y)) root.NE = insert(root.NE, x, y, value);
        return root;
    }

    @Override
    public void remove(Key x, Key y, Value value) {
        root = remove(root, x, y, value);
    }

    //Если он не найдет узел - ничего не произойдет;
    //Если он находит узел - узел удаляется, а все его дочерние элементы повторно вставляются в дерево
    private Node remove(Node root, Key x, Key y, Value value) {
        if (root == null)
            return null;
        if (numOfChildren(root) == 0) {
            if (root.value == value) {
                return null;

            } else return root;
        } else if (root.value != value) {
            if (less(x, root.x) && less(y, root.y)) root.SW = remove(root.SW, x, y, value);
            else if (less(x, root.x) && !less(y, root.y)) root.NW = remove(root.NW, x, y, value);
            else if (!less(x, root.x) && less(y, root.y)) root.SE = remove(root.SE, x, y, value);
            else if (!less(x, root.x) && !less(y, root.y)) root.NE = remove(root.NE, x, y, value);
        } else {
            //продолжаем с детьми
            Node ne = root.NE;
            Node nw = root.NW;
            Node sw = root.SW;
            Node se = root.SE;
            root = null;
            //вставляем их в родительский узел найденного узла
            insert(ne, ne.x, ne.y, ne.value);
            insert(nw, nw.x, nw.y, nw.value);
            insert(sw.x, sw.y, sw.value);
            insert(se.x, se.y, se.value);
        }
        return root;
    }

    @Override
    public Value find(Key x, Key y) {
        return find(root, x, y);
    }

    //возвращает значение null, если ничего не найдено, и значение value, если что-то есть
    private Value find(Node root, Key x, Key y) {
        if (root == null) return null;
        if (eq(root.x, x) && eq(root.y, y)) return root.value;

        if (less(x, root.x) && less(y, root.y)) return find(root.SW, x, y);
        if (less(x, root.x) && !less(y, root.y)) return find(root.NW, x, y);
        if (!less(x, root.x) && less(y, root.y)) return find(root.SE, x, y);
        if (!less(x, root.x) && !less(y, root.y)) return find(root.NE, x, y);
        return null;
    }

    private int numOfChildren(Node root) {
        int n = 0;
        if (root.SW != null) n++;
        if (root.SE != null) n++;
        if (root.NW != null) n++;
        if (root.NE != null) n++;
        return n;
    }

    private boolean less(Key k1, Key k2) {//меньше
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Key k1, Key k2) {//равен
        return k1.compareTo(k2) == 0;
    }
}