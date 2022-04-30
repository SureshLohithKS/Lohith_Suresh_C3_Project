import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime present = getCurrentTime();
        return(present.isAfter(openingTime) && present.isBefore(closingTime));
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());
    }

    public String getName() {
        return name;
    }

    /**
     * @param items
     * @return total cost of the selected items.
     * @assumptions
     *  1. All the items in the list are in the menu.
     *  2. Items cannot be repeated in the selected items list as user can check / uncheck an item,
     *  as there is no provision to set the quantity for each item. So repeated items are removed from the list.
     * @throws itemNotFoundException
     */
    public int getCost(List<String> items) throws itemNotFoundException {
        int totalcost = 0;
        List<String> nonRepeatedItems = removeRepetitions(items);
        for (String name : nonRepeatedItems) {
            Item item = findItemByName(name);
            if (item != null)
                totalcost += item.getPrice();
            else
                throw new itemNotFoundException(name + " Not Found");
        }
        return totalcost;
    }

    /**
     *
     * @param items
     * @return List of non repeated items.
     */
    private List<String> removeRepetitions(List<String> items)
    {
        Set<String> set = new HashSet<String>(items);
        return new ArrayList<String>(set);
    }
}
