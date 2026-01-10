/**
 * @author Shreya
 */
public class InventoryItem {
    private String name;
    private double price;
    private int quantity = 0;

    public InventoryItem(String name, double price, int quant){
        this.name = name;
        this.price = price;
        this.quantity = quant;
    }

    public InventoryItem(String name, double price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity( int q){
        quantity = q;
    }

}
