/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockmarket;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author Manny
 */
public class MiningHelper {
    private Block targetBlock;
    private BlockChain targetBlockChain;
    private int nonceVal = 0;
    
    public MiningHelper() {
        
    }
    
    public MiningHelper(BlockChain tbc) {
        targetBlockChain = tbc;
    }
    
    public MiningHelper(Block b, BlockChain tbc) {
        this(tbc);
        targetBlock = b;
    }
    
    public void setTargetBlock(Block b) { targetBlock = b; }
    public void setTargetBlockChain(BlockChain tbc) { targetBlockChain = tbc; }
    public Block getTargetBlock() { return targetBlock; }
    public BlockChain getTargetBlockChain() { return targetBlockChain; } 
    
    public int findNonce() throws NoSuchAlgorithmException{
        nonceVal += 1;

        return nonceVal;
        
    }
    
    public void updateTargetBlock(int nonce) throws NoSuchAlgorithmException {
        targetBlock.setNonce(nonce);
        targetBlock.updateHash();
    }
    
    public boolean validateHash(){
        return targetBlockChain.isValidBlock(targetBlock);
    }
    
    public void setNonceVal(int n){
        nonceVal = n;
    }
    
}
