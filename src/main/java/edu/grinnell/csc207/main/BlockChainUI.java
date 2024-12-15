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
    pen.printf("""
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

  private static Transaction promptTransaction(PrintWriter pen, Scanner eyes) throws Exception{
    pen.printf("Source (return for deposit): ");
    String source = eyes.nextLine();

    pen.printf("Target: ");
    String recepient = eyes.nextLine();

    pen.printf("Amount: ");
    String amount = eyes.nextLine();
    int val;
    try {
      val = Integer.valueOf(amount);
      if (val <= 0) {
        pen.println("please make sure your amnt is a positive nonzero value");
        throw new Exception();
      } // if
    } catch (Exception e) {
      pen.println("please make sure your amnt is a number");
      throw new Exception();
    } // try-catch

    return (new Transaction(source, recepient, val));


  }


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
      pen.printf("\n\nCommand: ");
      String commandLine = eyes.nextLine(); // Read user input
      if (commandLine.equals("quit")) {
        break;
      } else if (commandLine.equals("help")) {
        printInstructions(pen);
      } else if (commandLine.equals("balance")) {
        pen.printf("User: ");
        String findUser = eyes.nextLine(); // Read user input
        int balance = currentChain.balance(findUser);
        if (balance <= 0) {
          pen.printf("%s does not have a balance or does not exist,"
                + "try users to see valid users", findUser);
        } else {
          pen.printf("%s's balance is %d", findUser, balance);
        } // if-else
      } else if (commandLine.equals("users")) {
        Iterator<String> userIterator = currentChain.users();
        while (userIterator.hasNext()) {
          pen.println(userIterator.next());
        } // while
      } else if (commandLine.equals("check")) {
        if (currentChain.isCorrect()) {
          pen.printf("the stored block chain is valid");
        } else {
          pen.printf("there is an issue with the stored block chain.");
        } // if-else
      } else if (commandLine.equals("remove")) {
        if (currentChain.removeLast()) {
          pen.printf("last element removed succesfully");
        } else {
          pen.printf("the array is empty so no elements where removed");
        } // if-else
      } else if (commandLine.equals("mine")) {
        Transaction trans;
        try {
          trans = promptTransaction(pen, eyes);
        } catch (Exception e) {
          continue;
        } // try-catch

        Block newBlock = currentChain.mine(trans);
        pen.printf("\nUse nonce: %d", newBlock.getNonce());

      } else if (commandLine.equals("append")) {
        Transaction trans;
        try {
          trans = promptTransaction(pen, eyes);
        } catch (Exception e) {
          continue;
        } // try-catch

        pen.printf("Nonce: ");
        String nonceStr = eyes.nextLine();
        long nonce;
        try {
          nonce = Long.valueOf(nonceStr);
        } catch (Exception e) {
          pen.println("please make sure your nonce is a number");
          continue;
        } // try-catch
        Block lastBlock = currentChain.getLastBlock();
        Block blk = new Block(lastBlock.getNum() + 1, trans, lastBlock.getHash(), nonce);
        try {
          currentChain.append(blk);
          // + blk.toString()
          pen.printf("Appended: " + blk.toString());
        } catch (Exception e) {
          pen.printf("failed to add block because " +  e.getMessage());
        } // try-catch
      } else {
        pen.printf("invalid command, use help to see the valid commands");
      } // if-else
    } // while loop to perpetually take commands
    eyes.close();
  } // main()
} // class BlockChainUI
