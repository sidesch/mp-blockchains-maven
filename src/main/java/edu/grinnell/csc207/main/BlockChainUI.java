package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.util.Scanner;

import edu.grinnell.csc207.blockchains.Block;
import edu.grinnell.csc207.blockchains.BlockChain;
import edu.grinnell.csc207.blockchains.HashValidator;
import edu.grinnell.csc207.blockchains.Transaction;

import java.util.Iterator;

/**
 * a main function containing the UI to interact with our block chain.
 * @author Jana Vadillo
 * @author Sarah Deschamps
 * @author Samuel A. Rebelsky
 */
public class BlockChainUI {
  private static void printInstructions(PrintWriter pen) {
    pen.println("""
      Valid commands:
      mine: discovers the nonce for a given transaction
      append: appends a new block onto the end of the chain
      remove: removes the last block from the end of the chain
      check: checks that the block chain is valid
      users: prints a list of users
      balance: finds a user's balance
      help: prints this list of commands
      quit: quits the program""");
  } // printInstructions(PrintWriter)


  /**
   * Run the UI.
   *
   * @param args
   *   Command -line arguments.
   */
  public static void main(String[] args) {
    HashValidator standardValidator = (hash) -> (hash.length() >= 3) && (hash.get(0) == 0);

    Scanner eyes = new Scanner(System.in); // Create a Scanner object
    PrintWriter pen = new PrintWriter(System.out, true); // Create a writing object
    printInstructions(pen);

    BlockChain currentChain = new BlockChain(standardValidator);

    while (true) {
      pen.printf("> ");
      String commandLine = eyes.nextLine(); // Read user input
      if (commandLine.equals("quit")) {
        break;
      } else if (commandLine.equals("help")) {
        printInstructions(pen);
      } else if (commandLine.equals("balance")) {
        pen.println("please enter the user you want to find");
        pen.printf(">");
        String findUser = eyes.nextLine(); // Read user input
        int balance = currentChain.balance(findUser);
        if (balance <= 0) {
          pen.printf("%s does not have a balance or does not exist,"
                + "try users to see valid users", findUser);
        } else {
          pen.printf("%s has a balance of %d\n", findUser, balance);
        } // if-else
      } else if (commandLine.equals("users")) {
        Iterator<String> userIterator = currentChain.users();
        while (userIterator.hasNext()) {
          pen.println(userIterator.next());
        } // while
      } else if (commandLine.equals("check")) {
        if (currentChain.isCorrect()) {
          pen.println("the stored block chain is valid");
        } else {
          pen.println("there is an issue with the stored block chain.");
        } // if-else
      } else if (commandLine.equals("remove")) {
        if (currentChain.removeLast()) {
          pen.println("last element removed succesfully");
        } else {
          pen.println("the array is empty so no elements where removed");
        } // if-else
      } else if (commandLine.equals("mine")) {
        pen.println("Please enter the amount of the transaction");
        pen.printf(">");
        String amount = eyes.nextLine();
        int val;
        try {
          val = Integer.valueOf(amount);
          if (val <= 0) {
            pen.println("please make sure your amnt is a positive nonzero value");
            continue;
          } // if
        } catch (Exception e) {
          pen.println("please make sure your amnt is a number");
          continue;
        } // try-catch
        pen.println("Please enter the source user");
        pen.printf(">");
        String source = eyes.nextLine();
        pen.println("Please enter the receiver");
        pen.printf(">");
        String recepient = eyes.nextLine();
        Block newBlock = currentChain.mine(new Transaction(source, recepient, val));
        pen.printf("your nonce is %d", newBlock.getNonce());
      } else if (commandLine.equals("append")) {
        pen.println("Please enter the ammount of the transaction");
        pen.printf(">");
        String amount = eyes.nextLine();
        pen.println("Please enter the nonce");
        pen.printf(">");
        String nonceStr = eyes.nextLine();
        int val;
        int nonce;
        try {
          val = Integer.valueOf(amount);
          nonce = Integer.valueOf(nonceStr);
        } catch (Exception e) {
          pen.println("please make sure your nonce and amount are numbers");
          continue;
        } // try-catch
        pen.println("Please enter the source user");
        pen.printf(">");
        String source = eyes.nextLine();
        pen.println("Please enter the reciever");
        pen.printf(">");
        String recepient = eyes.nextLine();
        Transaction trans = new Transaction(source, recepient, val);
        Block lastBlock = currentChain.getLastBlock();
        Block blk = new Block(lastBlock.getNum() + 1, trans, lastBlock.getHash(), nonce);
        try {
          currentChain.append(blk);
          pen.printf("succesfully added block");
        } catch (Exception e) {
          pen.printf("failed to add block because %e\n", e);
        } // try-catch
      } else {
        pen.println("invalid command, use help to see the valid commands");
      } // if-else
    } // while loop to perpetually take commands
    eyes.close();
  } // main()
} // class BlockChainUI
