package edu.grinnell.csc207.blockchains;

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
   */
  public Block(int num, Transaction transaction, Hash prevHash,
      HashValidator check) {
    this.blockNum = num;
    this.trans = transaction;
    this.previousHash = prevHash;
    Random rand = new Random();
    this.nonce = rand.nextLong();
    
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
   */
  public Block(int num, Transaction transaction, Hash prevHash, long nonce) {
    // STUB
  } // Block(int, Transaction, Hash, long)

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Compute the hash of the block given all the other info already
   * stored in the block.
   */
  static void computeHash(int blockNum, Transaction transaction,
      Hash prev, long nonce) throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("sha-256");
    
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
    return this.trans; // STUB
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
    return new Hash(new byte[] {0});  // STUB
  } // getPrevHash

  /**
   * Get the hash of the current block.
   *
   * @return the hash of the current block.
   */
  Hash getHash() {
    return new Hash(new byte[] {0});  // STUB
  } // getHash

  /**
   * Get a string representation of the block.
   *
   * @return a string representation of the block.
   */
  public String toString() {
    return "";  // STUB
  } // toString()
} // class Block
