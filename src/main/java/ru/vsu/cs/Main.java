package ru.vsu.cs;

public class Main {

    public static void main(String[] args) {
        QuadTree<Integer, String> st = new QuadTree<Integer, String>();
        int x1 = 6;
        int y1 = 3;
        int k = 99;
        st.insert(x1, y1, "P" + k);
        // вставьте N случайных точек в единичный квадрат
        for (int i = 0; i < 5; i++) {
            Integer x = (int) (100 * Math.random());
            Integer y = (int) (100 * Math.random());
            st.insert(x, y, "P" + i);
        }
        System.out.println(st.find(x1, y1));

        // удалить некоторые случайные элементы
        for (int i = 0; i < 3; i++) {
            Integer x = (int) (100 * Math.random());
            Integer y = (int) (100 * Math.random());
            st.remove(x, y, "P" + i);
        }
    }
}
