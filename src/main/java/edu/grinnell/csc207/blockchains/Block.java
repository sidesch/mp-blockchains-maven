package edu.grinnell.csc207.blockchains;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Blocks to be stored in blockchains.
 *
 * @author Sarah Deschamps
 * @author Jana Vadillo
 * @author Samuel A. Rebelsky
 */
public class Block {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * Number of the block in the blockchain.
   */
  int blockNum;

  /**
   * The hash of this block.
   */
  Hash hash;

  /**
   * The nonce of this block.
   */
  long nonce;

  /**
   * The transaction.
   */
  Transaction trans;

  /**
   * The hash of the previous block in the chain.
   */
  Hash previousHash;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new block from the specified block number, transaction, and
   * previous hash, mining to choose a nonce that meets the requirements
   * of the validator.
   *
   * @param num
   *   The number of the block.
   * @param transaction
   *   The transaction for the block.
   * @param prevHash
   *   The hash of the previous block.
   * @param check
   *   The validator used to check the block.
   * @throws NoSuchAlgorithmException 
   */
  public Block(int num, Transaction transaction, Hash prevHash,
      HashValidator check) {
    this.blockNum = num;
    this.trans = transaction;
    this.previousHash = prevHash;
    Random rand = new Random();
    long nonce = rand.nextLong();
    Hash hash;
    try {
      hash = new Hash(computeHash(num, transaction, prevHash, nonce));
      while (!check.isValid(hash)) {
        nonce = rand.nextLong();
        hash = new Hash(computeHash(num, transaction, prevHash, nonce));
      } // while
      this.nonce = nonce;
      this.hash = hash;
    } catch (NoSuchAlgorithmException e) {
      // don't set it
    } // try-catch
  } // Block(int, Transaction, Hash, HashValidator)

  /**
   * Create a new block, computing the hash for the block.
   *
   * @param num
   *   The number of the block.
   * @param transaction
   *   The transaction for the block.
   * @param prevHash
   *   The hash of the previous block.
   * @param nonce
   *   The nonce of the block.
   * @throws NoSuchAlgorithmException 
   */
  public Block(int num, Transaction transaction, Hash prevHash, long nonce){
    this.blockNum = num;
    this.trans = transaction;
    this.previousHash = prevHash;
    this.nonce = nonce;
    try {
      this.hash = new Hash(computeHash(num, transaction, prevHash, nonce));
    } catch (Exception e) {
      // don't set it
    } // try-catch
  } // Block(int, Transaction, Hash, long)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute the hash of the block given all the other info already
   * stored in the block.
   *
   * @param blockNum
   *    Current block number.
   * @param transaction
   *    Transaction that holds source, target, and amount.
   * @param prev
   *    Previous hash.
   * @param nonce
   *    Nonce.
   * @return the computed byte array, representing the hash.
   * @throws NoSuchAlgorithmException
   */
  static byte[] computeHash(int blockNum, Transaction transaction,
      Hash prev, long nonce) throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(ByteBuffer.allocate(Integer.BYTES).putInt(blockNum).array());
    md.update(transaction.getSource().getBytes());
    md.update(transaction.getTarget().getBytes());
    md.update(ByteBuffer.allocate(Integer.BYTES).putInt(transaction.getAmount()).array());
    md.update(prev.getBytes());
    md.update(ByteBuffer.allocate(Long.BYTES).putLong(nonce).array());
    byte[] hash = md.digest();
    return hash;
  } // computeHash()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get the number of the block.
   *
   * @return the number of the block.
   */
  public int getNum() {
    return this.blockNum;
  } // getNum()

  /**
   * Get the transaction stored in this block.
   *
   * @return the transaction.
   */
  public Transaction getTransaction() {
    return this.trans;
  } // getTransaction()

  /**
   * Get the nonce of this block.
   *
   * @return the nonce.
   */
  public long getNonce() {
    return this.nonce;
  } // getNonce()

  /**
   * Get the hash of the previous block.
   *
   * @return the hash of the previous block.
   */
  Hash getPrevHash() {
    return previousHash;
  } // getPrevHash

  /**
   * Get the hash of the current block.
   *
   * @return the hash of the current block.
   */
  Hash getHash() {
    return hash;
  } // getHash

  /**
   * Get a string representation of the block.
   *
   * @return a string representation of the block.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Block %d ",this.blockNum));
    sb.append(String.format("Transaction: [Source: %s", this.trans.getSource()));
    sb.append(String.format(", Target %s, Amount: %s]", this.trans.getTarget(), this.trans.getAmount()));

    sb.append(String.format(", Nonce: %l",this.nonce));
    sb.append(String.format(", prevHash: " + this.previousHash.toString()));
    sb.append(String.format(", hash: " + this.hash.toString()) + ")");
    

    return sb.toString();  // STUB
  } // toString()
} // class Block
