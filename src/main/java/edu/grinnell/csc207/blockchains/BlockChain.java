package edu.grinnell.csc207.blockchains;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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

  /**
   * The table of users and deposits.
   */
  HashMap<String, Integer> clients;

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
    Transaction emptyTransaction = new Transaction("", "", 0);
    byte[] emptyBytes = new byte[] {};
    Hash next = new Hash(emptyBytes);
    Block block = new Block(0, emptyTransaction, next, check);
    BlockNode node = new BlockNode(block);
    this.first = node;
    this.last = node;
    this.valid = check;
    this.clients = new HashMap<String, Integer>();
  } // BlockChain(HashValidator)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Checks whether this block is valid in the chain.
   *
   * @param blk
   *    The block to check validity.
   *
   * @return true if everything is valid, false otherwise.
   */
  private boolean checkBlock(Block blk) {
    Hash prevHash = blk.getPrevHash();
    Hash blockHash = blk.getHash();
    int previous = blk.getNum() - 1;
    if (!this.valid.isValid(blockHash)
        || !prevHash.equals(this.getNode(previous).getBlock().getHash())) {
      throw new IllegalArgumentException();
    } // if
    try {
      blk.computeHash();
      Hash expectedHash = blk.getHash();
      if (!(blockHash.equals(expectedHash))) {
        throw new IllegalArgumentException();
      } // if
    } catch (Exception e) {
      // do not append
    } // try-catch
    return true;
  } // checkBlock(Block)

  /**
   * Returns the block with the given number. Return null
   * if this block doesn't exist.
   *
   * @param num
   *    The block number.
   *
   * @return the block with the block number.
   */
  private BlockNode getNode(int num) {
    BlockNode curr = this.first;
    for (int i = 0; i < num; i++) {
      if (curr.getNext() == null) {
        return null;
      } // if
      curr = curr.getNext();
    } // for
    return curr;
  } // getBlock(int)

  /**
   * Updates the HashMap of users given the transaction.
   *
   * @param t
   *    The transaction from which to read.
   *
   * @return true if the addition is valid, false otherwise.
   */
  private boolean addTransaction(Transaction t) {
    String source = t.getSource();
    String target = t.getTarget();
    int amount = t.getAmount();
    if (clients.containsKey(target)) {
      int amounttgt = (int) this.clients.get(target) + amount;
      this.clients.put(target, amounttgt);
    } else {
      this.clients.put(target, amount);
    } // if-else
    if (clients.containsKey(source)) {
      int amountsrc = (int) this.clients.get(source) - amount;
      this.clients.put(source, amountsrc);
      if (amountsrc < 0) {
        return false;
      } // if
    } else {
      this.clients.put(source, -1 * amount);
      return false;
    } // if-else
    return true;
  } // addTransaction(Transaction)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Gets the last block.
   *
   * @return the last block in the block chain.
   */
  public Block getLastBlock() {
    return this.last.getBlock();
  } // getLastBlock()

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
    int lastNum = this.last.getBlock().getNum();
    Hash lastHash = this.last.getBlock().getHash();
    return new Block(lastNum + 1, t, lastHash, this.valid);
  } // mine(Transaction)

  /**
   * Get the number of blocks curently in the chain.
   *
   * @return the number of blocks in the chain, including the initial block.
   */
  public int getSize() {
    return this.last.getBlock().getNum() + 1;
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
  public void append(Block blk) throws IllegalArgumentException {
    BlockNode newBlockNode = new BlockNode(blk);
    addTransaction(blk.getTransaction());
    if (checkBlock(blk)) {
      this.last.setNext(newBlockNode);
      this.last = newBlockNode;
    } else {
      throw new IllegalArgumentException();
    } // if-else
  } // append(Block)

  /**
   * Attempt to remove the last block from the chain.
   *
   * @return false if the chain has only one block (in which case it's
   *   not removed) or true otherwise (in which case the last block
   *   is removed).
   */
  public boolean removeLast() {
    if (this.getSize() <= 1) {
      return false;
    } else {
      Transaction trans = this.last.getBlock().getTransaction();
      String lasttarget = trans.getTarget();
      String lastsrc = trans.getSource();
      Iterator<Block> blks = blocks();
      boolean hastarget = false;
      boolean hassrc = false;
      this.last = getNode(this.last.getBlock().getNum() - 1);
      this.last.setNext(null);
      while (blks.hasNext()) {
        Block curr = blks.next();
        Transaction currenttrans = curr.getTransaction();
        if (currenttrans.getSource().equals(lasttarget) || currenttrans.getTarget().equals(lasttarget)) {
          hastarget = true;
        } else if (currenttrans.getSource().equals(lastsrc) || currenttrans.getTarget().equals(lastsrc)) {
          hassrc = true;
        } // if-else
      } // while
      if (!hastarget) {
        this.clients.remove(lasttarget);
      } else if (!hassrc) {
        this.clients.remove(lastsrc);
      } // if-else
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
    Iterator<Block> blks = blocks();
    clients.clear();
    byte[] emptyBytes = new byte[] {};
    Hash prev = new Hash(emptyBytes);
    while (blks.hasNext()) {
      Block curr = blks.next();
      if (!addTransaction(curr.getTransaction())) {
        throw new Exception("Transactions are invalid");
      } // if
      if (!curr.getPrevHash().equals(prev)) {
        throw new Exception("Previous hash is invalid");
      } // if
      prev = curr.getHash();
      Hash thishash = curr.getHash();
      curr.computeHash();
      Hash actualhash = curr.getHash();
      if (!thishash.equals(actualhash)) {
        throw new Exception("Hash is not valid for its contents");
      } // if
      if (!valid.isValid(actualhash)) {
        throw new Exception("Hash is not valid");
      } // if
    } // while
  } // check()

  /**
   * Return an iterator of all the people who participated in the
   * system.
   *
   * @return an iterator of all the people in the system.
   */
  public Iterator<String> users() {
    return new Iterator<String>() {
      Set<String> people = clients.keySet();
      Iterator<String> iter = people.iterator();

      public boolean hasNext() {
        return iter.hasNext();
      } // hasNext()

      public String next() {
        return iter.next();
      } // next()
    };
  } // users()

  /**
   * Find one user's.
   *
   * @param user
   *   The user whose balance we want to find.
   *
   * @return that user's balance (or 0, if the user is not in the system).
   */
  public int balance(String user) {
    if (this.clients.containsKey(user)) {
      return this.clients.get(user);
    } else {
      return 0;
    } // if-else
  } // balance()

  /**
   * Get an iterator for all the blocks in the chain.
   *
   * @return an iterator for all the blocks in the chain.
   */
  public Iterator<Block> blocks() {
    return new Iterator<Block>() {
      BlockNode curr = null;
      public boolean hasNext() {
        if (curr == null) {
          return first.getNext() != null;
        } // if
        return curr.getNext() != null;
      } // hasNext()

      public Block next() {
        if (curr == null) {
          this.curr = first;
          return first.getBlock();
        } // if
        this.curr = this.curr.getNext();
        return this.curr.getBlock();
      } // next()
    };
  } // blocks()

  /**
   * Get an iterator for all the transactions in the chain.
   *
   * @return an iterator for all the transactions in the chain.
   */
  public Iterator<Transaction> iterator() {
    return new Iterator<Transaction>() {
      Iterator<Block> blks = blocks();
      public boolean hasNext() {
        return blks.hasNext();
      } // hasNext()

      public Transaction next() {
        return blks.next().getTransaction();
      } // next()
    };
  } // iterator()
} // class BlockChain
