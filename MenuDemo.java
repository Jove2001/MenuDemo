// Menu demo for terminal-based programs
// Ian McElwaine, s3863018, RMIT University

// Dependencies: 
// menutext.csv

// memtext.csv data structure: 
// Menu number, Menu name, Number of valid menu options, Menu text to print to console

import java.io.*;
import java.util.*;

// TO DO: Load menu text to memory as String[][] menuText instead of on-the-fly file access
public class MenuDemo
{
   // Create scanner for user input
   private Scanner scanner;

   // Hold some ANSI codes as strings
   public static final String ANSI_CYAN = "\u001B[36m";
   public static final String ANSI_RESET = "\u001B[0m";

   // TO DO: Load text to memory.
   // Data structure:
   // i = Index of menu text to display
   // j = Lines of menu text
   // private String[i][j] menuText;

   public MenuDemo()
   {
      this.scanner = new Scanner(System.in);

      // TO DO:
      // this.menuText = null;
      // loadMenuText();

      viewMenu(0, 4);
   }

   // TO DO:
   // public void loadMenuText() {
   //
   // }

   // View a line of text from MenuText.csv
   // Pass the menu number and number of valid menu options as parameters to this
   // method as integers.
   public void viewMenu(int menuNumber, int numberOfMenuOptions)
   {
      try
      {
         // Create variables
         BufferedReader bReader = new BufferedReader(new FileReader("menutext.csv"));
         String menuText;
         int currentMenuNumber = 0;

         // Read a line of file
         while ((menuText = bReader.readLine()) != null)
         {
            // Split input text into a string array
            String[] menuTextArray = menuText.split(",");

            // Find the menu number (0-4) that we want to display
            if (Integer.parseInt(menuTextArray[0]) == menuNumber)
            {
               // -- Avoid NumberFormatException - Technique 1 --
               // Only parse a string value that we know can ALWAYS
               // be represented as a integer.
               // In this example .parseInt() will never throw a
               // NumberFormatException because menuTextArray[2] contains an int
               // value.
               currentMenuNumber = Integer.parseInt(menuTextArray[2]);

               // Start counter at 3 to skip index + menu name + number of menu
               // options.
               int i = 3;
               while (i < menuTextArray.length)
               {
                  // Print lines of text to console
                  System.out.println(menuTextArray[i]);
                  i++;
               }
               // Get valid user input. Pass the current menu number from
               // menuTextArray[2] to getValidUserInput().
               int validInput = getValidUserInput(currentMenuNumber);

               // Display new menu
               viewMenu(validInput, currentMenuNumber);
            }
         }
         // Close bufferedreader object when finished
         bReader.close();
      }

      // Notify user about problems with the data file.
      catch (FileNotFoundException e)
      {
         System.err.println("menutext.csv not found");
      }
      catch (IOException e)
      {
         System.err.println("Can't read menutext.csv");
      }
   }

   // Get valid data entry.
   // Pass the number of valid menu options as an integer.
   public int getValidUserInput(int numberOfMenuOptions)
   {
      // Data entry option prompts
      String[] optionText =
               { "Enter 0: ", "Enter 0-1: ", "Enter 0-2: ", "Enter 0-3: ", "Enter 0-4: " };

      // Create variables
      String userInput;
      int validInput = 0;
      boolean validInputReceived = false;

      // -- Avoid NumberFormatException - Technique 2 --
      // Contain try/catch for NumberFormatException in a while loop.
      // Loop continues until validInputReceived == true
      while (validInputReceived == false)
      {
         // Print prompt for data entry in ANSI cyan
         // Needs Eclipse plugin 'ANSI Escape in Console' or
         // run program in a terminal.
         System.out.print(ANSI_CYAN + optionText[numberOfMenuOptions] + ANSI_RESET);

         // Take string from user
         userInput = this.scanner.nextLine();

         // Try to parse userInput as an int between 0 and numberOfMenuOptions
         // If userInput can be validated, then store user entry as validInput.
         // Then validInputReceived == true
         try
         {
            if (Integer.parseInt(userInput) >= 0 &&
                Integer.parseInt(userInput) <= numberOfMenuOptions)
            {
               validInput = Integer.parseInt(userInput);
               validInputReceived = true;
            }

            // This else block isn't really required here because
            // user is re-prompted to enter data.
            else
            {
               // You could uncomment the next line if you want.
               // System.err.println("You must enter a valid number");
            }
         }

         // Not really essential to do anything here because
         // user is re-prompted to enter data.
         catch (NumberFormatException e)
         {
            // You could uncomment the next line if you want.
            // System.err.println("You must enter a number");
         }
      }

      // Return validated input to calling method.
      return validInput;
   }

   // Main method
   public static void main(String[] args)
   {
      MenuDemo terminal = new MenuDemo();
   }
}