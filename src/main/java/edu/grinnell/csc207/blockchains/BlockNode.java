package edu.grinnell.csc207.blockchains;

/**
 * A node within a BlockChain.
 *
 * @author Jana Vadillo
 * @author Sarah Deschamps
 */
public class BlockNode {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The block being stored.
   */
  Block block;

  /**
   * The previous node in the chain.
   */


  BlockNode next;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new Block Node storing the given block.
   *
   * @param heldBlock
   *   The block to be held in the node.
   */
  public BlockNode(Block heldBlock) {
    this.block = heldBlock;
    this.next = null;
  } // BlockNode(Block)

  /**
   * Create a new Block Node storing the given block using the given next node.
   *
   * @param heldBlock 
   *   The block to be held in the node.
   * @param nextBlock
   *   The next node.
   */
  public BlockNode(Block heldBlock, BlockNode nextBlock) {
    this.block = heldBlock;
    this.next = nextBlock;
  } // BlockNode(Block, Block)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+



  /**
   * Sets the previous node.
   *
   * @param prevBlock
   *   The previous node.
   */
  public void setNext(BlockNode prevBlock) {
    this.next = prevBlock; 
  } // setPrev(BlockNode)

  /**
   * Get the block in the node.
   *
   * @return the block in the node.
   */
  public Block getBlock() {
    return this.block;
  } // getBlock()


    /**
   * Get the block in the node.
   *
   * @return the block in the node.
   */
  public BlockNode getNext() {
    return this.next;
  } // getNext()
} // class BlockNode
