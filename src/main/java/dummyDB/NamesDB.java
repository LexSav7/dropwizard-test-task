package dummyDB;

import java.util.ArrayList;
import java.util.List;

public class NamesDB {
    private static final List<String> NAMES = new ArrayList<>();

    public static List<String> getAll() {
        return NAMES;
    }

    public static void save(String name) {
        NAMES.add(name);
    }
}
