package day;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author jh.wang
 * @since 2024/5/29
 */
public class Day20240529 {

    @Test
    public void arrayListTest(){
        final ArrayList<Object> objects = new ArrayList<>(12);

        final int size = 12;
        for (int i = 0; i < size; i++) {
            objects.add(i);
        }

        objects.trimToSize();
    }

    @Test
    public void linkedListTest(){
        final LinkedList<Object> objects = new LinkedList<>();
        objects.addFirst(1);
    }

}
