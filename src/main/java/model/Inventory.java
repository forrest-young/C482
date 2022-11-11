package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Forrest Young
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int currentId = 0;
    public static int getNextId(){return ++currentId;}

    /**
     *
     * @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /**
     *
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        if (newProduct != null) {
            allProducts.add(newProduct);
        }
    }

    /**
     *
     * @param partId the id to match
     * @return the matching part
     */
    public static Part lookupPart(int partId){
        if (!allParts.isEmpty()){
            for (Part part : allParts) {
                if (part.getId() == partId) {
                    return part;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param productId the id to match
     * @return the matching product
     */
    public static Product lookupProduct(int productId){
        if (!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if (product.getId() == productId) {
                    return product;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param partName the name to match
     * @return the matching parts
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> list = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                list.add(part);
            }
        }
        return list;
    }

    /**
     *
     * @param productName the name to match
     * @return the matching products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> list = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                list.add(product);
            }
        }
        return list;
    }

    /**
     *
     * @param index the index to set
     * @param selectedPart the part to set
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index the index to set
     * @param selectedProduct the product to set
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * @param selectedPart the part to delete
     */
    public static void deletePart(Part selectedPart){
        if (selectedPart != null){
            allParts.remove(selectedPart);
        }else {
            System.out.print("Please select a part");
        }
    }

    /**
     *
     * @param selectedProduct the product to delete
     * @return true if deleted, false if not
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (selectedProduct != null){
            allProducts.remove(selectedProduct);
            return true;
        }else {
            System.out.print("Please select a product");
            return false;
        }
    }

    /**
     *
     * @return the parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return the products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}