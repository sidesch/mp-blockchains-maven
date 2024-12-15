# Changes

Changes made to our program:

* Debugged the Block constructor by changing this.nonce every time it checks for its validity.
* Debugged mine so that it checked for hashes given a block rather than the block number.
* Debugged the blocks iterator so that it would start at null and then go to the first block.
* Debugged the remove method so that if the transaction removed a user entirely it would be reflected in the hash table of users.
* Return false in isValid if the hash is invalid (in the try-catch).
* Debugged addTransaction so that it would ignore transactions where the source is the empty string.
* Debugged check to check if transactions had negative amounts.
* Debugged balance to recalculate the balance of the user each time.
* Update the UI.
* Update block.toString().
