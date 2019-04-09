/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockmarket;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
/**
 *
 * @author Manny
 */
public class Miner implements Runnable {
    private String name;
    private BlockChain mainBlockChain; // reference to main block chain which will be used to detect changes
    private MiningHelper helper;
    private BlockData[] transactions;
    private Range transactionRange; // range of transactions from given transaction list
    private double earnings;
    private double processingPower; // percentage, controls delay between loops to simulate real miner processing power
    
    public Miner() throws NoSuchAlgorithmException{
        helper = new MiningHelper();
        earnings = 0.0;
    }
    
    
    public Miner(String n, BlockChain bc, BlockData[] t, Range r) throws NoSuchAlgorithmException {
        this();
        name = n;
        mainBlockChain = bc;
        transactions = t;
        transactionRange = r;
    }
    
    public void setName(String n){
        name = n;
    }
    public String getName(){
        return name;
    }
    
    public double getEarnings() { return earnings; }
    
    /*
    public boolean updateBlockChain() throws NoSuchAlgorithmException{
        if (!blockChainCopy.equals(mainBlockChain)){
            blockChainCopy = mainBlockChain.copy();
            return true;
        }
        
        return false;
        
    }
    */
    
    @Override
    public void run() {
        int nonce = 0;
        int count = 0;
        helper = new MiningHelper(mainBlockChain);
        try {
            //long time_check = System.currentTimeMillis() + 1000;
            while (!transactionRange.isComplete()){
                count+=1;
                //if (time_check < System.currentTimeMillis()){
                //    System.out.println("Loops per second: " + count);
                //    break;
                //}
                Block newBlock = new Block(mainBlockChain.getSize(), transactions[transactionRange.getPointer()].copy(), mainBlockChain.getChain()[mainBlockChain.getSize() - 1].getHash());
                
                helper.setTargetBlock(newBlock);

                nonce = helper.findNonce();
                helper.updateTargetBlock(nonce);
                if (count % 10000 == 0){
                    /*
                    System.out.println(nonce);
                    System.out.println(newBlock.getNonce());
                    
                    System.out.println(transactionRange.isComplete());
                    */
                }
                try {
                    if (helper.validateHash()){
                        System.out.println(name + " found a valid hash!");
                        newBlock.getBlockData().setMiner(name); // sign block with miners name
                        earnings += newBlock.getBlockData().getFee();
                        mainBlockChain.insertNewBlock(newBlock.copy());
                        transactionRange.incrementPointer();
                        System.out.println("Pointer position: " + transactionRange.getPointer() +
                                           "\nEndpoint: " + transactionRange.getEnd() + 
                                           "\nBlock chain size: " + mainBlockChain.getSize() + "\n");
                        helper.setNonceVal(0); // reset nonce counter
                        //System.out.println(newBlock.getBlockData());
                        //System.out.println(mainBlockChain.getChain()[0].getBlockData());
                    }
                }
                catch (NullPointerException e){
                    helper.setNonceVal(nonce - 1); // aka try again
                    
                }
                
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(name + " has encountered an error");
        }
        System.out.println(name + " finished.\n");
        
    }
    
}
