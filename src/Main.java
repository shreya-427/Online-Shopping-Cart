import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store walmart = new Store();
        walmart.loadStoreItems();
        Cart cart = new Cart(walmart);
        //walmart.getInventory();

        //Scanner
        Scanner s = new Scanner(System.in);

        int option = 0;
        String o = "";
        while(option != -1){
            System.out.println("Welcome to our online grocerry store, here are your options:\n1. view store inventory\n2. add to cart\n3. remove from cart\n4. empty cart\n5. edit quantity of existing item\n6. check cart size\n7. checkout\n");
            System.out.print("\nWhich option would you like to pick? To exit type -1 ");
            //if input is not an integer
            /*hasNextInt() promts user input, then only peeks at the input to check if its an int but doesn't assign anything */
            while(!s.hasNextInt()){
                System.out.println("\nInvalid option, whole numbers only!");
                System.out.println("Here are your options:\n1. view store inventory\n2. add to cart\n3. remove from cart\n4. empty cart\n5. edit quantity of existing item\n6. check cart size\n7. checkout\n");
                System.out.print("Which option would you like to pick? To exit type -1 ");
                s.next(); //this reads the input and removes it from scanner
                //important b/c if not removed hasNextInt() will be checking the same input over again
            }    
            option = s.nextInt(); //nextInt reads the input on buffer, checks int, stores it in var, then removes from buffer
            //switch case for options
            switch(option){
                case 1: //view store inventory
                    System.out.println("Inventory: ");
                    walmart.displayInventory();
                    System.out.println("\n");
                    break;
                case 2: //add to cart
                    System.out.println("Inventory: ");
                    walmart.displayInventory();
                    System.out.print("Enter the item number you would like to add: ");
                    
                    //if invalid item # input - not int
                    while(!s.hasNextInt()){
                        System.err.println("Not a valid item number, try again");
                        walmart.displayInventory();
                        System.out.print("Enter the item number you would like to add: ");
                        s.next();
                    }
                    int index = s.nextInt();

                    //checking if index is between valid indexes - atp already int
                    while((index > walmart.getInventory().size() || index < 1)) { 
                        System.out.println("Not a valid item number, try again!");
                        //walmart.displayInventory();
                        System.out.print("Enter the item number you would like to add: ");
                        //checking if new input is an int
                        while(!s.hasNextInt()) {
                            System.out.println("Whole numbers only, try again!");
                            System.out.print("Enter the item number you would like to add: ");
                            s.next();    
                        }
                        index = s.nextInt();//if int read,save, disgard
                    }

                    System.out.print("Enter the quantity: ");
                    //checks if quant is also an int
                    while(!s.hasNextInt()){
                        System.err.println("Not a valid quantity, try again");
                        s.next();
                    }
                    int quant = s.nextInt();

                    //make sure quantity is valid

                    while((quant < 0 || quant > Integer.MAX_VALUE)) { 
                        System.err.println("Not a valid quantity, try again!");
                        System.out.print("Enter the quanity: ");
                        while(!s.hasNextInt()){ //if quant is not an int
                            System.err.println("Whole numbers only & can't be too large, try again");
                            System.out.print("Enter the quantity: ");
                            s.next();
                        }
                         quant = s.nextInt();
                    }
                   
                    
                    //if valid quant and item
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
                    
                case 3: //remove from cart
                    System.out.println("Current cart:");
                    for(InventoryItem i: cart.getCart()){
                        System.out.println(i.getName() + "- " + i.getPrice() + " x " + i.getQuantity());

                    }
                    System.out.print("Enter the item number you would like to remove: ");
                    //checks if item number is an int
                    while(!s.hasNextInt()){
                        System.err.println("Whole numbers only, try again!");
                        System.out.print("Enter the item number you would like to remove: ");
                        s.next();
                    }
                    
                    int ind = s.nextInt();

                    //getname from ind
                    String nameInput = walmart.getInventory().get(ind-1).getName();
                    //index in cart
                    int exist = cart.findItemName(nameInput);

                    //check if item number is in cart
                    if(ind <= 0 || ind > walmart.getInventory().size()+1 || exist == -1){
                        System.err.println("Item does not exist in cart");
                        break;
                    }

                    System.out.print("Enter the quantity to remove: ");
                    //check if quantity is int
                    while(!s.hasNextInt()){
                        System.err.println("Whole numbers only, try again!");
                        System.out.print("Enter the quantity to remove: ");
                        s.next();
                    }
                    int quanty = s.nextInt();
                
                //check if quant is valid
                    boolean valQuan = cart.validQuant(exist,quanty);

                    if(quanty <= 0 || quanty > Integer.MAX_VALUE) {
                        System.err.println("Not a valid quantity, try again!");
                        break;
                    }else if (!valQuan) {
                        System.err.println("Not a valid quantity, try again!\n");
                        break;
                    }else{
                        //update quantity of the item
                        int q = cart.getCart().get(exist).getQuantity() - quanty;
                        cart.getCart().get(exist).setQuantity(q);
                        System.out.println("Item is removed");

                        //update total cost
                        int prevQ = cart.getCart().get(exist).getQuantity();
                        double c = Math.abs(quanty - prevQ) * cart.getCart().get(exist).getPrice();
                        double prevCost = cart.getTotalCost();
                        if(quanty >= prevQ){
                        //add to total cost
                            cart.setTotalCost(prevCost + c);
                        }else{
                        //substract from total cost
                            cart.setTotalCost(prevCost - c);
                        }
                    //update total Num Items
                        int difQ = Math.abs(quanty - prevQ);
                        if(quanty >= prevQ){
                        //add to total items
                            cart.setSize(prevQ + difQ);
                        }else{
                        //substract from total items
                            cart.setSize(prevQ - difQ);
                        }
                    //update quant
                        cart.getCart().get(exist).setQuantity(quanty);
                        System.out.println("Quantity of" + nameInput + "has been updated!");
                        break;
                    }
                case 4: //empty cart2
                    cart.getCart().clear();
                    //update total items
                    cart.setSize(0);
                    //update total price
                    cart.setTotalCost(0);
                    System.out.println("cart is cleared, "  + cart.getCart().size() + " items remain");
                    break;
                case 5: //edit quantity of existing item
                    System.out.print("Enter the item number you would like to edit the quantity of: ");
                    while(!s.hasNextInt()){
                        System.err.println("Whole numbers only, try again!");
                        System.out.print("Enter the item number you would like to edit the quantity of: ");
                        s.next();
                    }
                    int ind1 = Integer.parseInt(s.next());

                    System.out.print("Enter the quantity: ");
                    while(!s.hasNextInt()){
                        System.err.println("Whole numbers only, try again!");
                        System.out.print("Enter the quantity to be updated: ");
                        s.next();
                    }
                    int quanty1 = s.nextInt();

                //get price and name from the number
                    String name2 = walmart.getInventory().get(ind1-1).getName();
                //index of item in cart
                    int indItem = cart.findItemName(name2);

                //check if item exists in cart
                    if(indItem == -1 ||  indItem < 0 || indItem > walmart.getInventory().size()+1){
                        System.err.println("Item does not exist in cart");
                        break;
                    }else if (quanty1 < 0 || quanty1 > Integer.MAX_VALUE) {
                        System.err.println("Not a valid quantity, try again!");
                        break;
                    }else{
                    //update total price
                        int prevQ = cart.getCart().get(indItem).getQuantity();
                        double c = Math.abs(quanty1 - prevQ) * cart.getCart().get(indItem).getPrice();
                        double prevCost = cart.getTotalCost();
                        if(quanty1 >= prevQ){
                        //add to total cost
                            cart.setTotalCost(prevCost + c);
                        }else{
                        //substract from total cost
                            cart.setTotalCost(prevCost - c);
                        }
                    //update total Num Items
                        int difQ = Math.abs(quanty1 - prevQ);
                        if(quanty1 >= prevQ){
                        //add to total items
                            cart.setSize(prevQ + difQ);
                        }else{
                        //substract from total items
                            cart.setSize(prevQ - difQ);
                        }
                    //update quant
                        cart.getCart().get(indItem).setQuantity(quanty1);
                        System.out.println("Quantity of" + name2 + "has been updated!");
                        break;
                    }
                case 6: //check cart size
                    System.out.println("Cart size: " + cart.totSize());
                    break;
                case 7: //checkout
                //display cart, items, quantities
                    cart.displayCart();
                //get total items
                    System.out.println("Total number of items: " + cart.totSize());
                //get total cost
                        System.out.printf("%s%.2f%n","Total cost with tax: $", cart.getTotalCost());
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
