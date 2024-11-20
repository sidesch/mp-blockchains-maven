package edu.grinnell.csc207.blockchains;

import java.security.NoSuchAlgorithmException;
import edu.grinnell.csc207.blockchains.BlockNode;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A full blockchain.
 *
 * @author Sarah Deschamps
 * @author Jana Vadillo
 */
public class BlockChain implements Iterable<Transaction> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The first block in the BlockChain.
   */
  BlockNode first;

  /**
   * The last block in the BlockChain.
   */
  BlockNode last;

  /**
   * The validator that determines if the BlockChain is valid.
   */
  HashValidator valid;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new blockchain using a validator to check elements.
   *
   * @param check
   *   The validator used to check elements.
   */
  public BlockChain(HashValidator check) {
    Transaction emptyTransaction = new Transaction(null, null, 0);
    byte[] emptyBytes = new byte[] {};
    Hash prev = new Hash(emptyBytes);
    Block block = new Block(0, emptyTransaction, prev, check);
    BlockNode node = new BlockNode(block);
    this.first = node;
    this.last = node;
    this.valid = check;
  } // BlockChain(HashValidator)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Mine for a new valid block for the end of the chain, returning that
   * block.
   *
   * @param t
   *   The transaction that goes in the block.
   *
   * @return a new block with correct number, hashes, and such.
   */
  public Block mine(Transaction t) {
    return new Block(10, t, new Hash(new byte[] {7}), 11);       // STUB
  } // mine(Transaction)

  /**
   * Get the number of blocks curently in the chain.
   *
   * @return the number of blocks in the chain, including the initial block.
   */
  public int getSize() {
    return this.last.getBlock().getNum();
  } // getSize()

  /**
   * Add a block to the end of the chain.
   *
   * @param blk
   *   The block to add to the end of the chain.
   *
   * @throws IllegalArgumentException if (a) the hash is not valid, (b)
   *   the hash is not appropriate for the contents, or (c) the previous
   *   hash is incorrect.
   */
  public void append(Block blk) {
    BlockNode newBlockNode = new BlockNode(blk, this.last);
    Block nB = newBlockNode.getBlock();//nB = newBlock
    Hash PrevHash = nB.getPrevHash();
    Hash blockHash = nB.getPrevHash();
    if (!(this.valid.isValid(blockHash))){
      throw new IllegalArgumentException();
    }//checks if hash is valid
    if (!(PrevHash.equals(this.last.getBlock().getHash()))){
      throw new IllegalArgumentException();
    }//checks if previous hash lines up
    try{
      Hash expectedHash = new Hash(Block.computeHash(nB.getNum(),nB.getTransaction(),
      nB.getPrevHash(),nB.getNonce()));

      if (!(blockHash.equals(expectedHash))){
        throw new IllegalArgumentException();
      }//checks if hash is what it should be for message
    }catch (Exception e){}

    this.last = newBlockNode;
    return;
  } // append()

  /**
   * Attempt to remove the last block from the chain.
   *
   * @return false if the chain has only one block (in which case it's
   *   not removed) or true otherwise (in which case the last block
   *   is removed).
   */
  public boolean removeLast() {
    if (this.getSize() == 0) {
      return false;
    } else {
      this.last = this.last.getPrevNode();
      return true;
    } // if-else
  } // removeLast()

  /**
   * Get the hash of the last block in the chain.
   *
   * @return the hash of the last sblock in the chain.
   */
  public Hash getHash() {
    return this.last.getBlock().getHash();
  } // getHash()

  /**
   * Determine if the blockchain is correct in that (a) the balances are
   * legal/correct at every step, (b) that every block has a correct
   * previous hash field, (c) that every block has a hash that is correct
   * for its contents, and (d) that every block has a valid hash.
   *
   * @return true if the blockchain is correct and false otherwise.
   */
  public boolean isCorrect() {
    try {
      check();
      return true;
    } catch (Exception e) {
      return false;
    } // try-catch
  } // isCorrect()

  /**
   * Determine if the blockchain is correct in that (a) the balances are
   * legal/correct at every step, (b) that every block has a correct
   * previous hash field, (c) that every block has a hash that is correct
   * for its contents, and (d) that every block has a valid hash.
   *
   * @throws Exception
   *   If things are wrong at any block.
   */
  public void check() throws Exception {
    // STUB
  } // check()

  /**
   * Return an iterator of all the people who participated in the
   * system.
   *
   * @return an iterator of all the people in the system.
   */
  public Iterator<String> users() {
    return new Iterator<String>() {
      public boolean hasNext() {
        return false;   // STUB
      } // hasNext()

      public String next() {
        throw new NoSuchElementException();     // STUB
      } // next()
    };
  } // users()

  /**
   * Find one user's balance.
   *
   * @param user
   *   The user whose balance we want to find.
   *
   * @return that user's balance (or 0, if the user is not in the system).
   */
  public int balance(String user) {
    return 0;   // STUB
  } // balance()

  /**
   * Get an interator for all the blocks in the chain.
   *
   * @return an iterator for all the blocks in the chain.
   */
  public Iterator<Block> blocks() {
    return new Iterator<Block>() {
      public boolean hasNext() {
        return false;   // STUB
      } // hasNext()

      public Block next() {
        throw new NoSuchElementException();     // STUB
      } // next()
    };
  } // blocks()

  /**
   * Get an interator for all the transactions in the chain.
   *
   * @return an iterator for all the blocks in the chain.
   */
  public Iterator<Transaction> iterator() {
    return new Iterator<Transaction>() {
      public boolean hasNext() {
        return false;   // STUB
      } // hasNext()

      public Transaction next() {
        throw new NoSuchElementException();     // STUB
      } // next()
    };
  } // iterator()

} // class BlockChain
