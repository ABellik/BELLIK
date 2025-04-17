package data;

import java.util.List;

public abstract class DataLoader<T> {
    public abstract List<T> load();
}
