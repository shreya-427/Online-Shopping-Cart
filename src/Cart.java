
import java.util.ArrayList;

public class Cart {
    private final double salesTax = 0.08;
    private Store s;
    private double totPrice;
    private int totItems;
    private ArrayList<InventoryItem> cart; //items in your cart

    public Cart(Store s){
        cart = new ArrayList<>();
        this.s = s;
    }

    public ArrayList<InventoryItem> getCart(){
        return cart;
    }

    //displayCart
    public void displayCart(){
        for(int i = 0; i < cart.size(); i++){
            System.out.println(i+1+ "." + cart.get(i).getName() + "- $" + cart.get(i).getPrice() +" x " + cart.get(i).getQuantity());
        }
    }
    
    //adds an item to inventory along with the quant
    public void addItem(InventoryItem a, int quant){
        a.setQuantity(quant);
        cart.add(a);
        totItems += quant;
        totPrice += a.getPrice() * quant;
    }

    //removes an item or removes a specific quantity of an item
    public String removeItem(InventoryItem a, int quant){
        //check to make sure quantity to remove is not more than what is there
        if(a.getQuantity()-quant >= 0){
            totItems -= quant;
            totPrice -= a.getPrice() * quant;
            a.setQuantity(a.getQuantity()-quant);
            return quant + " items have been removed";
        }else{
            return "cannot remove " + quant + " items as it exceeds the quantity that already exists";
        }
    }

    //clears cart
    public void clear(){
        cart.clear();
        totItems = 0;
        totPrice = 0;
    }

    //checks if cart is empty
    public boolean isEmpty(){
        return cart.isEmpty();
    }

    //checks the size of the cart
    public int size(){
        return totItems;
    }

    //round to nearest hundreth place 
    public double getTotalCost(){
        /* how to round
            multiply by 100, round, divide by 100
        */
        double finAns = Math.round((((totPrice *salesTax) + totPrice) * 100));
        return finAns/100;
    }

    public void setTotalCost(double c){
        totPrice = c;
    }

    //search for a specific item in the bag by name, and return slot # in cart
    public int findItemName(String name){
        int ind = -1;
        //traverse through cart, compare every itemName
        for(int i = 0; i < cart.size(); i++){
            //System.err.println("testing: " + cart.get(i).getName().indexOf(name));
            if(cart.get(i).getName().indexOf(name) >= 0){
                ind = cart.get(i).getName().indexOf(name);
            }
        }
        //search by name
        return ind;
        
    }


}
