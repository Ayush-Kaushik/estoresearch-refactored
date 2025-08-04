
# eStoreSearch Program - User Guide

## Overview

The eStoreSearch program is a command-line application that manages an electronic store inventory system. It allows users to load product data from files, add new products (books and electronics), search through the inventory, and export data back to files.

## System Requirements

- Java Development Kit (JDK) 8 or higher
- Command-line terminal/console access
- Text editor for creating input files

## How to Run the Program

### 1. Compilation
First, compile the Java files:
```bash
javac -d . src/main/java/*.java
```

### 2. Execution
Run the program with an input file:
```bash
java Main input_file.txt
```

> The program requires exactly one command-line argument (the input file path). If no file is provided, the program will highlight that file is not provided and continue running.

## Input File Format

The program reads product data from a specially formatted text file. Each product entry follows this structure:

### Book Format:
```
type = "book"
productID = "123456"
name = "Java Programming Guide"
price = "29.99"
year = "2020"
authors = "John Smith"
publisher = "Tech Publications"

```

### Electronics Format:
```
type = "electronics"
productID = "654321"
name = "Wireless Headphones"
price = "99.99"
year = "2023"
maker = "AudioTech"

```

### Key Rules:
- Each field must be on a separate line
- Values must be enclosed in double quotes
- A blank line separates each product entry
- Product IDs must be exactly 6 digits
- Year must be a 4-digit number
- Price can be decimal (optional field)
- Authors, publisher, and maker fields are optional (can be empty)

## Program Features

### Main Menu Options

Once the program starts, you'll see the main menu:
```
-------Welcome to E-Search --------
Select the numbers or type command corresponding to the options below: 
(1)Add (2)Search (3)Quit
```

You can enter:
- **Numbers:** 1, 2, or 3
- **Full commands:** add, search, quit
- **Short commands:** a, s, q

## Feature 1: Adding Products

### Step-by-Step Process:

1. **Select Add Option**
   - Enter `1`, `add`, or `a`

2. **Choose Product Type**
   ```
   Select the numbers corresponding to the options below: (1)Book (2)Electronics
   ```

3. **Enter Product Details**
   The program will prompt for each field in sequence:

   **For All Products:**
   - **Product ID:** Must be exactly 6 digits and unique
   - **Name:** Cannot be empty
   - **Year:** Must be a 4-digit number (1000-9999)
   - **Price:** Enter numeric value or leave blank for 0.0

   **Additional for Books:**
   - **Author:** Optional (can be left blank)
   - **Publisher:** Optional (can be left blank)

   **Additional for Electronics:**
   - **Maker:** Optional (can be left blank)

### Example Add Session:
```
Selected option: Add

Select the numbers corresponding to the options below: (1)Book (2)Electronics 
1
Enter the ID:
123457
Enter year:
2023
Enter the name of the item:
Learning Java
Enter the price of item or leave blank: 
45.99
Enter the author of item or leave blank: 
Jane Doe
Enter the Publisher of item or leave blank: 
Education Press

Adding this entry in list: 123457 Learning Java 45.99 2023 Jane Doe Education Press
```

## Feature 2: Searching Products

### Step-by-Step Process:

1. **Select Search Option**
   - Enter `2`, `search`, or `s`

2. **Enter Search Criteria**
   The program will ask for three optional search parameters:

### Product ID Search:
```
Do you wish to enter the product ID: (1) Yes (2) No
```
- If Yes: Enter exactly 6 digits
- If No: Skip this criteria

### Product Name Search:
```
Do you wish to enter product name: (1)Yes (2)No
```
- If Yes: Enter full or partial product name
- The search supports multiple keywords (space-separated)
- Search is case-insensitive

### Year Search:
```
Do you wish to enter product year: (1)Yes (2)No
```
- **Exact year:** `2020`
- **Up to year:** `-2020` (products from 2020 and earlier)
- **From year:** `2020-` (products from 2020 onwards)
- **Year range:** `2018-2022` (products between 2018 and 2022)

### Search Examples:

**Search by name only:**
```
Enter the specs and find the matching product
Do you wish to enter the product ID: (1) Yes (2) No 
2
Do you wish to enter product name: (1)Yes (2)No
1
Enter the product name: 
java
Do you wish to enter product year: (1)Yes (2)No
2
```

**Search by year range:**
```
Do you wish to enter product year: (1)Yes (2)No
1
Enter the product year: 
2020-2023
```

### Search Results:
Results display in this format:
```
item ID: 123456, Name of item: Java Programming, Year: 2020, Price: 29.99, Author: John Smith, Publisher: Tech Publications
```

## Feature 3: Quit and Export

### Step-by-Step Process:

1. **Select Quit Option**
   - Enter `3`, `quit`, or `q`

2. **Automatic Export**
   - The program automatically saves all current inventory to `java.txt`
   - File is created in the same directory as the program
   - Format matches the input file format

3. **Program Exit**
   - Program terminates after successful file write

## How the Search Algorithm Works

### Inverted Index System:
1. **Keyword Extraction:** When products are added, the program breaks down product names into individual words
2. **Index Creation:** Each word is stored as a key in a HashMap, with values being lists of product indices
3. **Multi-word Search:** For searches with multiple keywords, the program finds products containing ALL keywords
4. **Flexible Matching:** Search is case-insensitive and supports partial name matching

### Example:
- Product: "Java Programming Guide"
- Indexed keywords: "java", "programming", "guide"
- Search "java guide" → Finds this product
- Search "JAVA" → Also finds this product (case-insensitive)

## Error Handling

The program includes robust error handling for:

### Input Validation:
- **Invalid Product ID:** Must be exactly 6 digits and unique
- **Invalid Year:** Must be 4 digits between 1000-9999
- **Empty Name:** Product name cannot be empty
- **Negative Price:** Price must be positive or zero
- **Invalid Menu Options:** Only accepts valid menu choices

### File Operations:
- **Missing Input File:** Program exits if file not found
- **File Write Errors:** Error message displayed if output file cannot be created

### User Input:
- **Type Mismatches:** Program re-prompts for correct input type
- **Invalid Selections:** Clear error messages guide user to valid options

## Tips for Best Usage

1. **File Preparation:** Ensure your input file follows the exact format with proper quotes and spacing
2. **Unique IDs:** Keep track of used product IDs to avoid conflicts
3. **Search Strategy:** Use multiple criteria to narrow down search results
4. **Data Backup:** The program overwrites `java.txt` on exit, so backup important data
5. **Case Sensitivity:** Product names are stored as entered but searched case-insensitively

## Common Issues and Solutions

### Problem: Program exits immediately
**Solution:** Ensure you provide an input file: `java original.EStoreSearch yourfile.txt`

### Problem: "Wrong input" for Product ID
**Solution:** Product ID must be exactly 6 digits (e.g., "123456", not "12345" or "1234567")

### Problem: Search returns no results
**Solution:** 
- Check spelling of search terms
- Try searching with fewer keywords
- Verify products exist with `View` or check your input file

### Problem: File not found error
**Solution:** 
- Verify the input file path is correct
- Ensure the file exists in the specified location
- Check file permissions

## Sample Input File

Here's a complete example input file (`sample_input.txt`):

```
type = "book"
productID = "100001"
name = "Java Complete Reference"
price = "59.99"
year = "2022"
authors = "Herbert Schildt"
publisher = "Oracle Press"

type = "electronics"
productID = "200001"
name = "Bluetooth Speaker"
price = "89.99"
year = "2023"
maker = "SoundTech"

type = "book"
productID = "100002"
name = "Learning Python"
price = "45.00"
year = "2021"
authors = "Mark Lutz"
publisher = "O'Reilly"

```

This documentation provides a complete guide to using the eStoreSearch program effectively. Follow the step-by-step instructions and refer to the examples for successful operation.
