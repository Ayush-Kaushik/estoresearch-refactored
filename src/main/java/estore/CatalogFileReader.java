package estore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Loads catalog products from the application's text-file format and builds the
 * name index for the products that were loaded.
 */
public class CatalogFileReader {

    private ArrayList<Product> _productList;
    private HashMap<String, ArrayList<Integer>> _nameIndex;

    public CatalogFileReader(ArrayList<Product> productList, HashMap<String, ArrayList<Integer>> nameIndex) {
        this._productList = productList;
        this._nameIndex = nameIndex;
    }

    /**
     * Reads products into the supplied in-memory catalog, then rebuilds its name
     * index from that catalog.
     *
     * @param fileName the catalog file to read
     * @throws FileNotFoundException if the catalog file cannot be opened
     */
    public void read(String fileName) throws FileNotFoundException {
        HashMap<String, String> inputMap = new HashMap<String, String>();
        Scanner inputStream = new Scanner(new FileInputStream(fileName));

        try {
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();
                String[] values = line.split("=");
                if (values.length == 2) {
                    String key = values[0].trim();
                    char[] array = values[1].trim().toCharArray();
                    array[0] = ' ';
                    array[array.length - 1] = ' ';
                    String value = new String(array).trim();
                    inputMap.put(key, value);
                } else {
                    addProduct(inputMap);
                    inputMap.clear();
                }
            }
        } finally {
            inputStream.close();
        }

        createNameIndex();
    }

    private void addProduct(HashMap<String, String> inputMap) {
        String type = inputMap.get("type");
        String id = inputMap.get("productID");
        String name = inputMap.get("name");
        String price = inputMap.get("price");
        String year = inputMap.get("year");

        if (type.equals("book")) {
            String author = inputMap.get("authors");
            String publisher = inputMap.get("publisher");
            _productList.add(new Book(
                id, 
                name, 
                Double.parseDouble(price), 
                Integer.parseInt(year), 
                author == null ? "" : author, 
                publisher == null ? "" : publisher));
        }

        if (type.equals("electronics")) {
            String maker = inputMap.get("maker");
            _productList.add(new Electronics(id, name, Double.parseDouble(price), Integer.parseInt(year),
                    maker == null ? "" : maker));
        }
    }

    private void createNameIndex() {
        _nameIndex.clear();
        for (int index = 0; index < _productList.size(); index++) {
            String name = _productList.get(index).getName();
            if (name.indexOf(' ') >= 0) {
                String[] tokens = name.toLowerCase().split(" ");
                for (String token : tokens) {
                    addIndexEntry(token, index);
                }
            } else {
                addIndexEntry(name.toLowerCase(), index);
            }
        }
    }

    private void addIndexEntry(String token, int productIndex) {
        if (!_nameIndex.containsKey(token)) {
            _nameIndex.put(token, new ArrayList<Integer>());
        }
        _nameIndex.get(token).add(productIndex);
    }
}
