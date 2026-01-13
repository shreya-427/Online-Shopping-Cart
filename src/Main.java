import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store walmart = new Store();
        walmart.loadStoreItems();
        Cart cart = new Cart(walmart);

        //Scanner
        Scanner s = new Scanner(System.in);

        int option = 0;
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
            
            s.nextLine(); //clears the entire buffer including \n
            //switch case for options
            switch(option){
                case 1: //view store inventory
                    System.out.println("Inventory: ");
                    walmart.displayInventory();
                    System.out.println("\n");
                    break;
                case 2: //add to cart
                    //for the item number
                    int index = 0;
                    while(true) { 
                        System.out.println("Inventory: ");
                        walmart.displayInventory();
                        System.out.print("Enter the item number you would like to add: ");

                        //this removes any white spaces before or after the input
                        String input = s.nextLine().trim();
                        //input.split("\\s+") - makes an array of all input without any type of white space
                        //.length - gets length of array
                        int len = input.split("\\s+").length;

                        //checks if more than one entry
                        if(len > 1){
                            System.err.println("Only one entry & must be a whole number from inventory, try again!");
                            //skips the rest & goes back to the beg of loop
                            continue;
                        }

                        //checks if entry is an int
                        //try catch is used when your code could give an error but you want it to do something when it does
                        try {
                            //could give me an error if input is anything except an int
                            index = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            //the error would be numberformatexpection error
                            System.err.println("Not a valid item number, try again");
                            //if so skip rest & go back to beg of loop
                            continue;
                        }

                        //checking if index is between valid indexes - atp already int
                        if((index > walmart.getInventory().size() || index < 1)){
                            System.out.println("Not a valid item number, try again!");
                            continue;
                        }
                        break;
                    }

                    int quant = 0;
                    //checks quanity
                    while(true){
                        System.out.print("Enter the quantity: ");
                        String input = s.nextLine().trim();
                        int len = input.split("\\s+").length;

                        if(len > 1){
                            System.err.println("Not a valid quantity, try again");
                            continue;
                        }

                        try {
                            quant = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.err.println("Not a valid quantity, try again");
                            continue;
                        }

                        if((quant <= 0 || quant > Integer.MAX_VALUE)){
                            System.err.println("Not a valid quantity, try again");
                            continue;  
                        }
                        break;
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
                                System.out.println("\n" + quant + name + "added");
                                break;
                    
                case 3: //remove from cart
                    System.out.println("Current cart:");
                    if(cart.totSize() == 0){
                        System.err.println("Empty cart, nothing to remove!");
                        break;
                    }
                    int in = 0;

                    for(InventoryItem i: cart.getCart()){
                        in = cart.findItemName(i.getName());
                        System.out.println((in+1) + "." + i.getName() + "- " + i.getPrice() + " x " + i.getQuantity());
                    }

                    int ind = 0;
                    String nameInput = "";
                    int exist = 0;

                    while(true){
                        System.out.print("Enter the item number you would like to remove or -1 to exit: ");
                        String input = s.nextLine().trim();
                        int len = input.split("\\s+").length;

                        if(len > 1){
                            System.err.println("Only one entry & must be whole number, try again");
                            continue;
                        }

                        try {
                            ind = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.err.println("Whole numbers only, try again");
                            continue;
                        }

                        if(ind == -1){
                            break;
                        }

                        if(ind <= 0 || ind > cart.getCart().size()){
                            System.err.println("Item does not exist in cart");
                            continue;
                        }

                        //getname from ind
                        nameInput = cart.getCart().get(ind-1).getName();
                        //index in cart
                        exist = cart.findItemName(nameInput);

                        //checks if its in cart
                        if(exist == -1){
                            System.err.println("Item does not exist in cart");
                            continue;  
                        }
                        break;
                    }
                    int quanty = 0;

                    if(ind == -1){
                        break;
                    }

                    while(true){
                        System.out.print("Enter the quantity to remove: ");
                        String input = s.nextLine().trim();
                        int len = input.split("\\s+").length;

                        if(len > 1){
                            System.err.println("Only one entry & must be whole number, try again");
                            continue;
                        }

                        try {
                            quanty = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.err.println("Whole numbers only, try again");
                            continue;
                        }

                        //checks if its in cart
                        if(quanty <= 0 || quanty > Integer.MAX_VALUE){
                            System.err.println("Not a valid quantity, try again!");
                            continue;  
                        }
                        break;
                    }
                    
                    cart.removeItem(cart.getCart().get(exist), quanty);
                    break;
                    
                case 4: //empty cart2
                    cart.getCart().clear();
                    //update total items
                    cart.setSize(0);
                    //update total price
                    cart.setTotalCost(0);
                    System.out.println("cart is cleared, "  + cart.getCart().size() + " items remain");
                    break;
                case 5: //edit quantity of existing item
                    int ind1;
                    int indItem;
                    String name2;

                    System.out.println("Current cart:");
                    if(cart.totSize() == 0){
                        System.err.println("Empty cart, nothing to remove!");
                        break;
                    }

                    for(InventoryItem i: cart.getCart()){
                        in = cart.findItemName(i.getName()) +1;
                        System.out.println(in + "." + i.getName() + "- " + i.getPrice() + " x " + i.getQuantity());
                    }

                    
                    while (true) { 
                        System.out.print("Enter the item number you would like to edit the quantity of or -1 to exit: ");
                        String input = s.nextLine().trim();
                        int len = input.split("\\s+").length;

                        //checks number of entry
                        if(len > 1){
                            System.err.println("Only one entry & must be whole number, try again");
                            continue;
                        }

                        //checks if int
                        try {
                            ind1 = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.err.println("Whole numbers only, try again!");
                            continue;
                        }

                        if(ind1 == -1){
                            break;
                        }

                        //valid item number
                        if(ind1 <= 0 || ind1 > cart.getCart().size()) {
                            System.err.println("Item does not exist in cart");
                            continue;
                        }
                        //get name from the index
                        name2 = cart.getCart().get(ind1-1).getName();
                        //index of item in cart
                        indItem = cart.findItemName(name2);

                        if(indItem == -1){
                            System.err.println("Item does not exist in cart");
                            continue;
                        }
                        break;
                    }

                    if(ind1 == -1){
                        break;
                    }
                    
                    int quanty1;

                    while(true){
                        System.out.print("Enter the quantity: ");
                        String input = s.nextLine().trim();
                        int len = input.split("\\s+").length;

                        //checks number of entry
                        if(len > 1){
                            System.err.println("Only one entry & must be whole number, try again");
                            continue;
                        }

                        //checks if int
                        try {
                            quanty1 = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.err.println("Whole numbers only, try again!");
                            continue;
                        }

                        if(quanty1 < 0 || quanty1 > Integer.MAX_VALUE){
                            System.err.println("Not a valid quantity, try again!");
                            continue;
                        }
                        break;

                    }
                    
                    cart.updateItem(cart.getCart().get(ind1-1), quanty1);
                    break;
                    
                case 6: //check cart size
                    System.out.println("Cart size: " + cart.totSize());
                    break;
                case 7: //checkout
                    if(cart.totSize() == 0){
                        System.err.println("Can't check out with empty cart!");
                        break;
                    }
                //display cart, items, quantities
                    cart.displayCart();
                //get total items
                    System.out.println("Total number of items: " + cart.totSize());
                //get total cost
                        System.out.printf("%s%.2f%n","Total cost with tax: $", (cart.getTotalCost()*1.08));
                        System.out.println("Thank you for shopping with us!");
                    option = -1;
                    break;
                default:
                    System.err.println("\nNot an option, try again!");
                    break;
            }
            
            
        }
        
    }
}
