package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Forrest Young
 */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double cost;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double cost, int stock, int min, int max){
        setId(id);
        setName(name);
        setCost(cost);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    /**
     *
     * @param id the id to be set
     */
    public void setId (int id){
        this.id = id;
    }

    /**
     *
     * @param name the name to be set
     */
    public void setName (String name){
        this.name = name;
    }

    /**
     *
     * @param cost the price to be set
     */
    public void setCost (double cost){
        this.cost = cost;
    }

    /**
     *
     * @param stock the stock to be set
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     *
     * @param min the min to be set
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     *
     * @param max the max to be set
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     *
     * @return the id
     */
    public int getId (){
        return id;
    }
    /**
     *
     * @return the name
     */
    public String getName (){
        return name;
    }
    /**
     *
     * @return the cost
     */
    public double getCost (){
        return cost;
    }
    /**
     *
     * @return the stock
     */
    public int getStock(){
        return stock;
    }
    /**
     *
     * @return the min
     */
    public int getMin(){
        return min;
    }
    /**
     *
     * @return the max
     */
    public int getMax(){
        return max;
    }

    /**
     *
     * @param part the part to be associated
     */
    public void addAssociatedPart(Part part){
        if(part != null){
            associatedParts.add(part);
        }
    }

    //////////////////////////////////////////////////
    //** Delete function below was never utilized **//
    //////////////////////////////////////////////////
    /**
     *
     * @param selectedAssociatedPart the part to be deleted
     * @return true if deleted, false if not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(selectedAssociatedPart != null){
            int s = selectedAssociatedPart.getId();

            for (int i = 0; i < associatedParts.size(); i++) {
                if (associatedParts.get(i).getId() == s) {
                    associatedParts.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return the associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
