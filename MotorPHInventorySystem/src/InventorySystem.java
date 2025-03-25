import java.io.*;
import java.util.*;

public class InventorySystem {
    private ArrayList<Stock> inventory;
    
    // Constructor
    public InventorySystem() {
        this.inventory = new ArrayList<>();
    }
    
    /**
     * Load inventory data from CSV file
     */
    public boolean loadInventoryFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            
            while ((line = br.readLine()) != null) {
                // Skip header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                // Split CSV line
                String[] data = line.split(",");
                if (data.length >= 5) {
                    // Create new stock and add to inventory
                    Stock stock = new Stock(
                        data[0].trim(),          // date
                        data[1].trim(),          // status (Old/New)
                        data[2].trim(),          // brand
                        data[3].trim(),          // engineNumber
                        data[4].trim()           // purchaseStatus (On-hand/Sold)
                    );
                    inventory.add(stock);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error loading inventory data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add new stock with validation
     */
    public String addStock(Stock newStock) {
        // Validation: Check if Engine Number already exists
        for (Stock stock : inventory) {
            if (stock.getEngineNumber().equals(newStock.getEngineNumber())) {
                return "Error: Engine number already exists in inventory";
            }
        }
        
        // Add the new stock
        inventory.add(newStock);
        return "Success: New stock added to inventory";
    }
    
    /**
     * Delete stock with validation
     */
    public String deleteStock(String engineNumber) {
        // Validation: Check if Engine Number exists
        int indexToRemove = -1;
        
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getEngineNumber().equals(engineNumber)) {
                indexToRemove = i;
                break;
            }
        }
        
        if (indexToRemove == -1) {
            return "Error: Engine number not found in inventory";
        }
        
        // Check status criteria (Old or Sold)
        String status = inventory.get(indexToRemove).getStatus();
        String purchaseStatus = inventory.get(indexToRemove).getPurchaseStatus();
        
        if (status.equals("Old") || purchaseStatus.equals("Sold")) {
            inventory.remove(indexToRemove);
            return "Success: Stock with engine number " + engineNumber + " has been deleted";
        } else {
            return "Error: Only Old or Sold stocks can be deleted";
        }
    }
    
    /**
     * Sort inventory by brand
     */
    public ArrayList<Stock> sortByBrand() {
        // Create a copy of the inventory to sort
        ArrayList<Stock> sortedInventory = new ArrayList<>(inventory);
        
        // Sort using a comparator on the brand field
        Collections.sort(sortedInventory, new Comparator<Stock>() {
            @Override
            public int compare(Stock s1, Stock s2) {
                return s1.getBrand().compareToIgnoreCase(s2.getBrand());
            }
        });
        
        return sortedInventory;
    }
    
    /**
     * Search inventory by various criteria
     */
    public ArrayList<Stock> searchStock(String criteria, String value) {
        ArrayList<Stock> results = new ArrayList<>();
        
        for (Stock stock : inventory) {
            switch (criteria.toLowerCase()) {
                case "date":
                    if (stock.getDate().contains(value)) {
                        results.add(stock);
                    }
                    break;
                case "status":
                    if (stock.getStatus().equalsIgnoreCase(value)) {
                        results.add(stock);
                    }
                    break;
                case "brand":
                    if (stock.getBrand().toLowerCase().contains(value.toLowerCase())) {
                        results.add(stock);
                    }
                    break;
                case "enginenumber":
                    if (stock.getEngineNumber().contains(value)) {
                        results.add(stock);
                    }
                    break;
                case "purchasestatus":
                    if (stock.getPurchaseStatus().equalsIgnoreCase(value)) {
                        results.add(stock);
                    }
                    break;
                default:
                    // No matching criteria
                    break;
            }
        }
        
        return results;
    }
    
    /**
     * Get the entire inventory
     */
    public ArrayList<Stock> getInventory() {
        return inventory;
    }
    
    /**
     * Display all inventory items
     */
    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Stock stock : inventory) {
            System.out.println(stock);
        }
        System.out.println("Total items: " + inventory.size());
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        InventorySystem system = new InventorySystem();
        
        // Load inventory data
        System.out.println("Loading inventory data...");
        boolean loaded = system.loadInventoryFromCSV("/home/vegand0g/Documents/DSA Array/MotorPHInventorySystem/src/data/Copy of MotorPH Inventory Data - March 2023 Inventory Data.csv");
        if (!loaded) {
            System.out.println("Failed to load inventory data. Exiting...");
            return;
        }
        
        System.out.println("Successfully loaded " + system.getInventory().size() + " inventory items.");
        
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        while (choice != 5) {
            System.out.println("\n=== MotorPH Inventory Management ===");
            System.out.println("1. Add new stock");
            System.out.println("2. Delete stock");
            System.out.println("3. Sort by brand");
            System.out.println("4. Search inventory");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        // Add new stock
                        System.out.println("\n=== Add New Stock ===");
                        System.out.print("Enter date (MM/DD/YYYY): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter status (Old/New): ");
                        String status = scanner.nextLine();
                        System.out.print("Enter brand: ");
                        String brand = scanner.nextLine();
                        System.out.print("Enter engine number: ");
                        String engineNumber = scanner.nextLine();
                        System.out.print("Enter purchase status (On-hand/Sold): ");
                        String purchaseStatus = scanner.nextLine();
                        
                        Stock newStock = new Stock(date, status, brand, engineNumber, purchaseStatus);
                        System.out.println(system.addStock(newStock));
                        break;
                        
                    case 2:
                        // Delete stock
                        System.out.println("\n=== Delete Stock ===");
                        System.out.print("Enter engine number to delete: ");
                        String engineToDelete = scanner.nextLine();
                        System.out.println(system.deleteStock(engineToDelete));
                        break;
                        
                    case 3:
                        // Sort by brand
                        System.out.println("\n=== Inventory Sorted by Brand ===");
                        ArrayList<Stock> sorted = system.sortByBrand();
                        for (Stock stock : sorted) {
                            System.out.println(stock);
                        }
                        break;
                        
                    case 4:
                        // Search inventory
                        System.out.println("\n=== Search Inventory ===");
                        System.out.println("1. Search by date");
                        System.out.println("2. Search by status");
                        System.out.println("3. Search by brand");
                        System.out.println("4. Search by engine number");
                        System.out.println("5. Search by purchase status");
                        System.out.print("Enter search type: ");
                        
                        int searchType = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        String criteria = "";
                        switch (searchType) {
                            case 1: criteria = "date"; break;
                            case 2: criteria = "status"; break;
                            case 3: criteria = "brand"; break;
                            case 4: criteria = "enginenumber"; break;
                            case 5: criteria = "purchasestatus"; break;
                            default: 
                                System.out.println("Invalid search type");
                                continue;
                        }
                        
                        System.out.print("Enter search value: ");
                        String searchValue = scanner.nextLine();
                        
                        ArrayList<Stock> results = system.searchStock(criteria, searchValue);
                        System.out.println("\nSearch Results:");
                        if (results.isEmpty()) {
                            System.out.println("No matching items found");
                        } else {
                            for (Stock stock : results) {
                                System.out.println(stock);
                            }
                            System.out.println("Found " + results.size() + " items");
                        }
                        break;
                        
                    case 5:
                        System.out.println("Exiting...");
                        break;
                        
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        
        scanner.close();
    }
}

