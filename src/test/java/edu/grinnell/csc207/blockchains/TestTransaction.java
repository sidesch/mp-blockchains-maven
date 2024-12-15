package edu.grinnell.csc207.blockchains;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Some simple tests of our Transaction class.
 *
 * @author Samuel A. Rebelsky
 */
public class TestTransaction {
  /**
   * Test that we can create a basic transfer.
   */
  @Test
  public void basicTest() {
    Transaction trans = new Transaction("Here", "There", 10);
    assertEquals("Here", trans.getSource(), "R: Source of basic transaction");
    assertEquals("There", trans.getTarget(), "R: Target of basic transaction");
    assertEquals(10, trans.getAmount(), "R: Amount of basic transaction");
    assertEquals("[Source: Here, Target: There, Amount: 10]",
        trans.toString(),
        "M: String representation of basic transaction");
  } // basicTest

  /**
   * Test that we can create a deposit.
   */
  @Test
  public void depositTest() {
    Transaction trans = new Transaction("", "There", 42);
    assertEquals("", trans.getSource(), "R: Source of deposit");
    assertEquals("There", trans.getTarget(), "R: Target of deposit");
    assertEquals(42, trans.getAmount(), "R: Amount of deposit");
    assertEquals("[Deposit, Target: There, Amount: 42]",
        trans.toString(),
        "M: String representation of deposit");
  } // depositTest()
} // class TestTransaction
