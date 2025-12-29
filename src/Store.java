
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;

public class Store{
    private ArrayList<InventoryItem> inventory;

    public Store(){
        inventory = new ArrayList<>();
    }

    public void displayInventory(){
        for(int i = 0; i < inventory.size(); i++){
            System.out.println((i+1) + "." + inventory.get(i).getName() + "- " + inventory.get(i).getPrice());
        }
    }

    public ArrayList<InventoryItem> getInventory(){
        return inventory;
    }

    //read all lines in file and return arrayList with all lines of
    public static ArrayList<String> readFile(String fileName){
        try {
            return new ArrayList<String>(Files.readAllLines(Paths.get(fileName)));
        } catch (IOException e) {
            return new ArrayList<>();  
        }
    }

    //takes all lines from file and puts them inventory
    public void loadStoreItems(){
        //when you enter your name of the file, make sure to include its location in the directory
        ArrayList<String> lines = readFile("data/itemList.text");
        //System.out.println("lines :" + lines.size());
        //split by comma, acessing first line b/c there's only 1 line 
        ArrayList<String> splitComma = new ArrayList<>(Arrays.asList(lines.get(0).split(",")));
            //System.out.println("spcomma " + splitComma.size());
            //split by dash & create inventoryitem obj
            
        for(String element: splitComma){
            ArrayList<String> splitDash = new ArrayList<>(Arrays.asList(element.split("-")));
            
            //split by $, use \\ for uncommon regex
            String[] price = splitDash.get(1).split("\\$");
    
            //convert string to double 
            //price is an array
                //first acess the first index, then acess the substring
            double p = Double.parseDouble(price[1]);
        
            //create inventory obj
            InventoryItem item = new InventoryItem(splitDash.getFirst(), p);
            
            //add to obj to inventory
            inventory.add(item);
        }    
    }
}
