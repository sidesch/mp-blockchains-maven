package edu.grinnell.csc207.blockchains;
/**
 * a class to store a single node holding a block in the blochain.
 * @author Jana Vadillo
 * @author Sarah Deschamps
 */
public class BlockNode {
    /**
     * the block being stored
      */
    Block block;
    /** 
     * the next node in the chain, null if none
     */
    BlockNode next;
/**
 * Constructor for block.
 * @param heldBlock the block to be held
 */
    public BlockNode (Block heldBlock){
        this.block = heldBlock;
        this.next = null;
    }//BlockNode(Block)
/**
 * constructor for block.
 * @param heldBlock the block being held
 * @param nextBlock the next block.
 */
    public BlockNode (Block heldBlock,  BlockNode nextBlock){
        this.block = heldBlock;
        this.next = nextBlock;
    }//BlockNode(Block, Block)
/**
 * Sets the next block value as a given value
 * @param nextBlock the next block
 */
    public void setNext (BlockNode nextBlock){
        this.next = nextBlock; 
    }//setNext(nextBlock)

}//BlockNode class
