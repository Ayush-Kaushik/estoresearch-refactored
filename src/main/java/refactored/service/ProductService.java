package refactored.service;

import java.util.ArrayList;

import refactored.data.IInvertedIndex;
import refactored.data.InvertedIndex;

import refactored.model.Product;

public class ProductService {
    private ArrayList<Product> productList;
    private IInvertedIndex nameSearchInvertedIndex;

    public ProductService() {
        this.productList = new ArrayList<Product>();
        this.nameSearchInvertedIndex = new InvertedIndex();
    }
}
