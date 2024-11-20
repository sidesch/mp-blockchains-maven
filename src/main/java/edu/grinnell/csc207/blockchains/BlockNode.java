package edu.grinnell.csc207.blockchains;

public class BlockNode {
    Block block;
    BlockNode next;

    public BlockNode (Block heldBlock){
        this.block = heldBlock;
        this.next = null;
    }

    public BlockNode (Block heldBlock,  BlockNode nextBlock){
        this.block = heldBlock;
        this.next = nextBlock;
    }

    public void setNext (BlockNode nextBlock){
        this.next = nextBlock; 
    }

}
