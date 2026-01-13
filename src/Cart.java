
import java.util.ArrayList;

public class Cart {
    private final double salesTax = 1.08;
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
    public void removeItem(InventoryItem a, int quant){
        //check to make sure quantity to remove is not more than what is there
        
        if(a.getQuantity()-quant > 0){
            totItems -= quant;
            totPrice -= a.getPrice() * quant;
            a.setQuantity(a.getQuantity()-quant);
            System.out.println(quant + " items have been removed");
        }else if (a.getQuantity()-quant == 0) {
            //remove item from cart
            for(int i = 0; i < cart.size(); i++){
                if(a.getName().equals(cart.get(i).getName())){
                    cart.remove(i);
                    System.out.println(quant + " items have been removed");
                }
            }
            totItems -= quant;
            totPrice -= a.getPrice() * quant;
            a.setQuantity(a.getQuantity()-quant);
        }else{
            System.out.println("Cannot remove " + quant + " items as it exceeds the quantity that already exists");
        }
    }

    public void updateItem(InventoryItem a, int quant){
        //check to make sure quantity to remove is not more than what is there
        
        if(quant == 0){
            //remove item from cart
            for(int i = 0; i < cart.size(); i++){
                if(a.getName().equals(cart.get(i).getName())){
                    cart.remove(i);
                    System.out.println("Items have been removed");
                }
            }
            totItems -= a.getQuantity()-quant;
            totPrice -= a.getPrice() * (a.getQuantity()-quant);
            a.setQuantity(quant);
        }else if (a.getQuantity()-quant > 0) {
            totItems -= a.getQuantity()-quant;
            totPrice -= a.getPrice() * (a.getQuantity()-quant);
            a.setQuantity(quant);
            System.out.println(a.getName() + " has been updated!");
        }else{
            //greater than 0 then update, tot Items, tot Price, and quant
            totItems += a.getQuantity()-quant;
            totPrice += a.getPrice() * (a.getQuantity()-quant);
            a.setQuantity(quant);
            System.out.println(a.getName() + " has been updated!");
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
    public int totSize(){
        return totItems;
    }

    public void setSize(int c){
        totItems = c;
    }

    //round to nearest hundreth place 
    public double getTotalCost(){
        /* how to round
            multiply by 100, round, divide by 100
        */
        double finAns = Math.round(totPrice*100);
        return finAns/100;
    }

    public void setTotalCost(double c){
        totPrice = c;
    }

    //search for a specific item in the bag by name, and return slot # in cart
        //returns index in cart
    public int findItemName(String name){
        int ind = -1;
        //traverse through cart, compare every itemName
        for(int i = 0; i < cart.size(); i++){
            //System.err.println("testing: " + cart.get(i).getName().indexOf(name));
            if(cart.get(i).getName().contains(name)){
                ind = cart.get(i).getName().indexOf(name);
            }
        }
        //search by name
        return ind;
        
    }

    public boolean validQuant(int index, int quant){
        return cart.get(index).getQuantity() >= quant;
    }


}
