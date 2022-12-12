package ru.vsu.cs;

public interface IQuadTree<Key extends Comparable<Key>, Value> {
    void insert(Key x, Key y, Value value);
    void remove(Key x, Key y, Value value);
    Value find(Key x, Key y);
}
