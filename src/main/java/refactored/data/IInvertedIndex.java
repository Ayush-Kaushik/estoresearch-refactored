package refactored.data;

import java.util.ArrayList;

public interface IInvertedIndex {

  public void add(String[] keys, int index);

  public void add(String key, int index);

  public ArrayList<Integer> get(String key);

  public void remove(String key, int index);

  public void clear();
}