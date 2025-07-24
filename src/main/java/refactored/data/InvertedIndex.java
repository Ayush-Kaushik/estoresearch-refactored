package refactored.data;

import java.util.ArrayList;
import java.util.HashMap;

public class InvertedIndex implements IInvertedIndex {

  private HashMap<String, ArrayList<Integer>> invertedIndex;

  public InvertedIndex() {
    this.invertedIndex = new HashMap<String, ArrayList<Integer>>();
  }

  public void add(String[] keys, int index) {
    for (String token : keys) {
      this.add(token, index);
    }
  }

  public void add(String key, int index) {
    if (this.invertedIndex.containsKey(key)) {
      this.invertedIndex.get(key).add(index);
    } else {
      this.invertedIndex.put(key, new ArrayList<Integer>());
    }
  }

  public ArrayList<Integer> get(String key) {
    if (this.invertedIndex.containsKey(key)) {
      return this.invertedIndex.get(key);
    }

    return null;
  }

  public void remove(String key, int index) {
    // TODO: finish implementing this method
  }

  public void clear() {
    // TODO: finish implementing this method
  }

}