import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store walmart = new Store();
        walmart.loadStoreItems();
        Cart cart = new Cart(walmart);
        //walmart.getInventory();

        //Scanner
        Scanner s = new Scanner(System.in);

        //System.out.println("Welcome to our online grocerry store, here are your options:\n1. view store inventory\n2. add to cart\n3. remove from cart\n4. empty cart\n5. edit quantity of existing item\n6. check cart size\n7. checkout\n");

        //1System.out.print("Which option would you like to pick? -1 if none ");
        int option = 0;

        while(option != -1){
            System.out.println("Welcome to our online grocerry store, here are your options:\n1. view store inventory\n2. add to cart\n3. remove from cart\n4. empty cart\n5. edit quantity of existing item\n6. check cart size\n7. checkout\n");
            System.out.print("\nWhich option would you like to pick? To exit type -1 ");
            option = s.nextInt();
            //switch case for options
            switch(option){
                case 1: //view store inventory
                    System.out.println("Inventory: ");
                    walmart.displayInventory();
                    break;
                case 2: //add to cart
                    System.out.println("Inventory: ");
                    walmart.displayInventory();
                    int index = 0,quant = 0;
                    System.out.print("Enter the item number you would like to add: ");
                    //int itemNumber = s.nextInt();
                    //checks if input is an int
                    if(s.hasNextInt()){
                        index = s.nextInt();
                        System.out.print("Enter the quantity: ");
                        //checks if quant is also an int
                        if(s.hasNextInt()){ 
                            quant = s.nextInt();
                            //make sure item number and quantity is valid
                            if(index > walmart.getInventory().size()+1 || index < 1){
                                System.err.println("Not a valid item number, try again");
                                break;
                            }else if (quant < 0 || quant > Integer.MAX_VALUE) {
                                System.err.println("Not a valid quantity, try again");
                                break;
                            }else{
                                //get price and name from the number
                                String name = walmart.getInventory().get(index-1).getName();
                                double price = walmart.getInventory().get(index-1).getPrice();
                                //make an inventory Item
                                InventoryItem item = new InventoryItem(name, price, quant);
                                //update quant of item
                                item.setQuantity(quant);
                                //add to cart
                                cart.addItem(item, quant);
                                System.out.println(quant + name + "added");
                                break;
                            } 
                        }else{
                            System.out.print("Invalid quantity!");
                            break;
                        }
                    }else{
                        System.out.println("Invalid Input!");
                        break;
                    }
                    
                case 3: //remove from cart
                    System.out.println("Current cart:");
                    for(InventoryItem i: cart.getCart()){
                        System.out.println(i.getName() + "- " + i.getPrice() + " x " + i.getQuantity());

                    }
                    System.out.println("Enter the item number you would like to remove: ");
                    if(s.next().contains(".")){
                        System.err.println("Please only enter whole numbers");
                    }
                    int ind = Integer.parseInt(s.next());
                    //check ind beforehand
                    //get price and name from the number

                    String name1 = walmart.getInventory().get(ind-1).getName();
                    System.out.println("Enter the quantity to remove: ");
                    if(s.next().contains(".")){
                        System.err.println("Please only enter whole numbers");
                    }
                    int quanty = s.nextInt();
                
                //index of item in cart
                    //check if its a valid index
                    if(ind < 0 || ind > walmart.getInventory().size()+1){
                        System.err.println("Item does not exist in cart");
                        break;
                    }else if (quanty < 0 || quanty > Integer.MAX_VALUE || quanty > cart.getCart().get(ind-1).getQuantity()) {
                        System.err.println("Not a valid quantity, try again");
                        break;
                    }else{
                    int indd = cart.findItemName(name1);
                        if(indd == -1){
                            System.err.println("Item does not exist in cart");
                            break;
                        }else{
                            //update quantity of the item
                        int q = cart.getCart().get(indd).getQuantity() - quanty;
                        cart.getCart().get(indd).setQuantity(q);
                        System.out.println("Item is removed");
                        break;
                        }
                    }
                case 4: //empty cart2
                    cart.getCart().clear();
                    System.out.println("cart is cleared, "  + cart.getCart().size() + " items remain");
                    break;
                case 5: //edit quantity of existing item
                    System.out.println("Enter the item number you would like to edit the quantity of: ");
                    if(s.next().contains(".")){
                        System.err.println("Please only enter whole numbers");
                    }
                    int ind1 = Integer.parseInt(s.next());
                    System.out.println("Enter the quantity: ");
                    if(s.next().contains(".")){
                        System.err.println("Please only enter whole numbers");
                    }
                    int quanty1 = s.nextInt();

                //get price and name from the number
                    String name2 = walmart.getInventory().get(ind1-1).getName();
                //index of item in cart
                    int indd1 = cart.findItemName(name2);

                //check if item exists in cart
                    if(indd1 == -1 ||  indd1 < 0 || indd1 > walmart.getInventory().size()+1){
                        System.err.println("item does not exist in cart");
                        break;
                    }else if (quanty1 < 0 || quanty1 > Integer.MAX_VALUE) {
                        System.err.println("not a valid quantity, try again");
                        break;
                    }else{
                    //update total price
                        int prevQ = cart.getCart().get(indd1).getQuantity();
                        double c = Math.abs(quanty1 - prevQ) * cart.getCart().get(indd1).getPrice();
                        double prevCost = cart.getTotalCost();
                        if(quanty1 >= prevQ){
                        //add to total cost
                            cart.setTotalCost(prevCost + c);
                        }else{
                        //substract from total cost
                            cart.setTotalCost(prevCost - c);
                        }
                    //update quant
                        cart.getCart().get(indd1).setQuantity(quanty1);
                        System.out.println("quantity of " + name2 + "has been updated");
                        break;
                    }
                case 6: //check cart size
                    System.out.println("Cart size: " + cart.size());
                    break;
                case 7: //checkout
                //display cart, items, quantities
                    cart.displayCart();
                //get total items
                    System.out.println("Total number of items: " + cart.size());
                //get total cost
                        System.out.println("Total cost: $" + cart.getTotalCost());
                        System.out.println("Thank you for shopping with us!");
                    option = -1;
                    break;
                default:
                    System.err.println("Not an option, try again");
                    break;
            }
        }
        
    }
}
