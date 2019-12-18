package Utilities;

import java.util.ArrayList;

public class CircularList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;

	@Override
    public E get(int index) {
        return super.get(index % size());
    }
}