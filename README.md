This repository contains the implementation of an inventory management system for MotorPH, a motorcycle startup company. The system is developed as part of Milestone 1 for the Data Structures and Algorithms course, focusing specifically on the use of ArrayList as the primary data structure.
Project Overview
MotorPH requires a solution to effectively manage their inventory by enabling four key operations:

Adding new stocks with proper engine number validation
Deleting stocks with validation for both engine number existence and status criteria
Sorting inventory items by brand
Searching for inventory items using various criteria

This implementation demonstrates how ArrayList can be effectively used to address these requirements with appropriate algorithms for each operation.
Components
The system consists of two main classes:

Stock.java: Represents individual inventory items with attributes for date, status, brand, engine number, and purchase status. Includes getters, setters, and appropriate data encapsulation.
InventorySystem.java: Implements the core functionality using ArrayList as the primary data structure. Handles CSV data loading, adding/removing stocks with proper validation, sorting, and multi-criteria searching.

Data Structure & Algorithms
This implementation uses ArrayList as the primary data structure due to its:

Dynamic size management for growing inventory
Efficient random access for validation operations
Built-in support for sorting via Collections.sort()
Straightforward API that maps well to the required operations

The implemented algorithms include:

Linear search for validation and finding items
Collections.sort with custom Comparator for brand sorting
Filter pattern for implementing search functionality

Getting Started
To run this application:

Ensure you have Java installed on your system (Java 8 or higher recommended)
Clone this repository to your local machine
Import the project into your preferred IDE (Eclipse, IntelliJ, VS Code, etc.)
Make sure the CSV data file is in the correct location (see file path in InventorySystem.java)
Run InventorySystem.java to start the application

Usage
The application provides a simple command-line interface with the following options:

Add new stock: Enter stock details including date, status, brand, engine number, and purchase status. The system validates that the engine number doesn't already exist.
Delete stock: Enter the engine number of the stock to delete. The system validates that the item exists and meets the criteria for deletion (status is "Old" or purchase status is "Sold").
Sort by brand: Display the inventory sorted alphabetically by brand name.
Search inventory: Search the inventory using various criteria including date, status, brand, engine number, or purchase status.
Exit: Close the application.

Validation Requirements
This implementation includes proper validation as required:

When adding stock: Validates if the engine number already exists
When deleting stock: Validates if the engine number exists and checks if status is "Old" or purchase status is "Sold"
