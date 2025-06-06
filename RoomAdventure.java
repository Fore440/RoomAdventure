/*
Aaron Fore: added rooms 3, 4, and 5, added use command, added apple. using apple does nothing, using coal kills you, using key in room 5 with new door wins
Used chatgpt 4o mini for the lines to exit the program (System.exit(0)) as well as general formatting help
*/

import java.util.Scanner; // Import Scanner for reading a user import


public class RoomAdventure{ // Main class containing game logic
    
    // class Varibles
    private static Room currentRoom; // Current room for player
    private static String[] inventory = {null, null, null, null, null}; // Player inventory
    private static String status; // Message to display after each action
    
    
    // Constants
    final private static String DEFUALT_STATUS =
        "Sorry, I do not understand. Try {verb} {noun}. Valid verbs include 'go', 'look', 'take', and 'use'"; //Defualt error message

    private static void handleGo(String noun) { // Handles moving between rooms
        String[] exitDirections = currentRoom.getExitDirections(); // Get the available directions
        Room[] exitDestinations = currentRoom.getExitDestinations(); // Get rooms in those directions
        status = "I don't see that room."; // Defuealt if that room is not found
        for (int i = 0; i < exitDirections.length; i++) { // Loop through the directions
            if (noun.equals(exitDirections[i])) { // Check if the direction is valid
                currentRoom = exitDestinations[i]; // Move to the new room
                status = "Changed Room "; // Update Status
            }

        }

    }

    private static void handlelook(String noun) { // Handles looking at items
        String[] items = currentRoom.getItems(); // Get the items in the room
        String[] itemDescriptions = currentRoom.getItemDescriptions(); // Get the item descriptions
        status = "I don't see that item."; // Default if the item is not found
        for (int i = 0; i < items.length; i++) { // Loop through the items
            if (noun.equals(items[i])) { // Check if the item is valid
                status = itemDescriptions[i]; // Update status with description
            }
        }
    }

    private static void handleTake(String noun) { // Handles taking items
        String[] grabbables = currentRoom.getGrabbables(); // Items that can be taken
        status = "I cant take that item."; // Default if the item is not grabbable
        for (String item: grabbables){ // Loop through the grabbables
            if (noun.equals(item)) { // Check if the item is valid
                for (int j = 0; j < inventory.length; j++) { // Loop through the inventory
                    if (inventory[j] == null) { // Check for empty slot
                        inventory[j] = noun; // Add item to inventory
                        status = "Added it to your inventory."; // Update status
                        break; // Break out of the loop
                    }
                }
            }
        }
    }
    
    private static void handleUse(String noun) {
        boolean hasItem = false;
        for (String item : inventory) {
            if (noun.equals(item)) {
                hasItem = true;
                break;
            }
        }
        if (!hasItem) {
            status = "You don't have that item.";
            return;
        }
        
        if (noun.equals("key") && currentRoom.name.equals("Room 5")) {
            status = "You used the key to unlock the door. You win!";
            System.out.println(status);
            System.exit(0);
        } else if (noun.equals("coal")) {
            status = "You ate the coal and died.";
            System.out.println(status);
            System.exit(0);
        } else if (noun.equals("apple")) {
            status = "You eat an apple. Nothing happens.";
        } else {
            status = "You can't use that here.";
        }
    }

    private static void setupGame() { //init game world
        RoomAdventure adventure = new RoomAdventure(); // Create a new instance of the game
        Room room1 = adventure.new Room("Room 1"); // Create room 1
        Room room2 = adventure.new Room("Room 2"); // Create room 2
        Room room3 = adventure.new Room("Room 3"); // Create room 3
        Room room4 = adventure.new Room("Room 4"); // Create room 4
        Room room5 = adventure.new Room("Room 5"); // Create room 5
        
        // Room 1
        String[] room1ExitDirections = {"east", "south"}; // Exit directions for room 1
        Room[] room1ExitDestinations = {room2, room3}; // Exit destinations for room 1
        String[] room1Items = {"chair", "desk"}; // Items in room 1
        String[] room1ItemDescriptions = {
            "A wooden chair", 
            "A wooden desk with a key on it"
        }; // Item descriptions for room 1
        String[] room1Grabbables = {"key"}; // items in room 1 you can take
        room1.setExitDirections(room1ExitDirections); // Set exit directions for room 1
        room1.setExitDestinations(room1ExitDestinations); // Set exit destinations for room 1
        room1.setItems(room1Items); // Set items for room 1
        room1.setItemDescriptions(room1ItemDescriptions); // Set item descriptions for room 1
        room1.setGrabbables(room1Grabbables); // Set grabbables for room 1

        // Room 2
        String[] room2ExitDirections = {"west", "south"}; // Exit directions for room 2
        Room[] room2ExitDestinations = {room1, room4}; // Exit destinations for room 2
        String[] room2Items = {"fireplace", "rug"}; // Items in room 2
        String[] room2ItemDescriptions = {
            "Its on fire",
            "theres a lump of coal on the rug"
        }; // Item descriptions for room 2
        String[] room2Grabbables = {"coal"}; // items in room 2 you can take
        room2.setExitDirections(room2ExitDirections); // Set exit directions for room 2
        room2.setExitDestinations(room2ExitDestinations); // Set exit destinations for room 2
        room2.setItems(room2Items); // Set items for room 2
        room2.setItemDescriptions(room2ItemDescriptions); // Set item descriptions for room 2
        room2.setGrabbables(room2Grabbables); // Set grabbables for room 2

        // Room 3
        String[] room3ExitDirections = {"north", "east"}; // Exit directions for room 3
        Room[] room3ExitDestinations = {room1, room4}; // Exit destinations for room 3
        String[] room3Items = {"apple"}; // Items in room 3
        String[] room3ItemDescriptions = {"A red apple"}; // Item descriptions for room 3
        String[] room3Grabbables = {"apple"}; // items in room 3 you can take
        room3.setExitDirections(room3ExitDirections); // Set exit directions for room 3
        room3.setExitDestinations(room3ExitDestinations); // Set exit destinations for room 3
        room3.setItems(room3Items); // Set items for room 3
        room3.setItemDescriptions(room3ItemDescriptions); // Set item descriptions for room 3
        room3.setGrabbables(room3Grabbables); // Set grabbables for room 3

        // Room 4
        String[] room4ExitDirections = {"north", "west", "south"}; // Exit directions for room 4
        Room[] room4ExitDestinations = {room2, room3, room5}; // Exit destinations for room 4
        String[] room4Items = {"table"}; // Items in room 4
        String[] room4ItemDescriptions = {"A small table"}; // Item descriptions for room 4
        String[] room4Grabbables = {}; // items in room 4 you can take
        room4.setExitDirections(room4ExitDirections); // Set exit directions for room 4
        room4.setExitDestinations(room4ExitDestinations); // Set exit destinations for room 4
        room4.setItems(room4Items); // Set items for room 4
        room4.setItemDescriptions(room4ItemDescriptions); // Set item descriptions for room 4
        room4.setGrabbables(room4Grabbables); // Set grabbables for room 4

        // Room 5
        String[] room5ExitDirections = {"north"}; // Exit directions for room 5
        Room[] room5ExitDestinations = {room4}; // Exit destinations for room 5
        String[] room5Items = {"door"}; // Items in room 5
        String[] room5ItemDescriptions = {"A locked door"}; // Item descriptions for room 5
        String[] room5Grabbables = {}; // items in room 5 you can take
        room5.setExitDirections(room5ExitDirections); // Set exit directions for room 5
        room5.setExitDestinations(room5ExitDestinations); // Set exit destinations for room 5
        room5.setItems(room5Items); // Set items for room 5
        room5.setItemDescriptions(room5ItemDescriptions); // Set item descriptions for room 5
        room5.setGrabbables(room5Grabbables); // Set grabbables for room 5

        currentRoom = room1; // Set the current room to room 1
    }

    @SuppressWarnings("java:52189") // Suppress warning for Scanner
    public static void main(String[] args) { //Entry point of the game
        setupGame(); // Setup the game
        
        while (true) { // Game loop, runs until program is terminated
            System.out.print(currentRoom.toString()); // Print the current room
            System.out.print("inventory: "); // Print the inventory

            for (int i = 0; i < inventory.length; i++) { // Loop through the inventory slots
              System.out.print(inventory[i] + " "); // Print each item in the inventory

            }
            
            System.out.println("\nWhat would you like to do?"); // Prompt for user input

            Scanner s = new Scanner(System.in); // Create a new Scanner object
            String input = s.nextLine(); // Read user input
            String[] words = input.split(" "); // Split the input into words

            if (words.length != 2) { // Check for proper two-word command
                status = DEFUALT_STATUS; // Set status to default error message
                System.out.println(status);
                continue; // Skip to the next iteration of the loop    
            }
            
            String verb = words[0]; // Get the verb from the input
            String noun = words[1]; // Get the noun from the input

            switch (verb) { // Decide which aciton to take
                case "go": // if verb is go
                    handleGo(noun); // go to another room
                    break;
                case "look": // if verb is look
                    handlelook(noun); // look at an item
                    break;
                case "take": // if verb is take
                    handleTake(noun); // take an item
                    break;
                case "use": // if verb is use
                    handleUse(noun); // use an item
                    break;
                default: // if verb is not recognized
                    status = DEFUALT_STATUS; // Set status to default error message
            }

            System.out.println(status); // Print the status message
    
        }



    }

class Room{ // Represents a game room
    private String name; // Room name
    private String[] exitDirections; // Directions you can go to exit  
    private Room[] exitDestinations; // Rooms reached by each direciton 
    private String[] items; // Items visible in each room 
    private String[] itemDescriptions; // Descriptions of items
    private String[] grabbables; // Items you can take


    public Room(String name) { // Constructor for room
        this.name = name; // Set the rooms name
    }


    public void setExitDestinations(Room[] exitDestinations){ // Setter for exits
        this.exitDestinations = exitDestinations; // Set the exit destinations
    }

    public Room[] getExitDestinations(){ // Getter for exits
        return exitDestinations; // Return the exit destinations
    }

    public void setExitDirections(String[] exitDestinations){ //Setter for exits
        this.exitDirections = exitDestinations; // Set the exit directions
    }

    public String[] getExitDirections(){ // Getter for exit directions
        return exitDirections; // Return the exit directions
    }

    public void setItems(String[] items){ // Setter for items
        this.items = items; // Set the items
    }

    public String[] getItems(){ // Getter for items
        return items; // Return the items
    }

    public void setItemDescriptions(String[] itemDescriptions){ // Setter for item descriptions
        this.itemDescriptions = itemDescriptions; // Set the item descriptions
    }

    public String[] getItemDescriptions(){ // Getter for item descriptions
        return itemDescriptions; // Return the item descriptions
    }

    public void setGrabbables(String[] grabbables){ // Setter for grabbables
        this.grabbables = grabbables; // Set the grabbables
    }

    public String[] getGrabbables(){ // Getter for grabbables
        return grabbables; // Return the grabbables
    }

// NOTE TO SELF: public void ...(type, varible){ // Setters
// NOTE TO SELF: public type get...(type, varible){ // Getters

    @Override //
    public String toString(){ // Custom print for the room
        String result = "\nLocation: " + name; //Show room name
        result += "\nYou See: "; // Show what you see
        for (String item : items){ //Loop items
            result += item + " "; // apend each item
            }
        result += "\nExits: "; // List exits
        for (String direction : exitDirections) { // Loop exits
            result += direction + " "; // Append each direction
        }
        return result + "\n"; //Return the full result
    }
}

}
