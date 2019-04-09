/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockmarket;

import java.util.*;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Manny
 */
public class BlockChain {
    //private Hashtable<String, Block> chain;
    private Block[] chain;
    private Block GenesisBlock;
    private Block lastBlock;
    private int size = 0;
    private int difficulty; 
    
    // CONSTRUCTOR
    public BlockChain() throws NoSuchAlgorithmException {
        GenesisBlock = new Block();
        
        chain = new Block[1000];
        difficulty = 3;
        chain[0] = GenesisBlock;
        size += 1;
    }
    
    public BlockChain(int capacity, int d) throws NoSuchAlgorithmException {
        GenesisBlock = new Block();
        
        chain = new Block[capacity];
        difficulty = d;
        chain[0] = GenesisBlock;
        size += 1;
    }
    
    public Block[] copyChain(){
        Block[] tempChain = new Block[chain.length];
        for (int i = 0; i < tempChain.length; i++){
            tempChain[i] = chain[i];
        }
        return tempChain;
    }
    
    public BlockChain copy() throws NoSuchAlgorithmException{
        BlockChain tempBlockChain = new BlockChain(this.chain.length, difficulty);
        tempBlockChain.setChain(this.copyChain());
        tempBlockChain.setSize(this.size);
        tempBlockChain.setGBlock(GenesisBlock);
        return tempBlockChain;
    }
    
    public int getSize(){
        return size;
    }
    
    public Block[] getChain(){
        return chain;
    }
    
    public void setGBlock(Block g){
        GenesisBlock = g;
    }
    
    public void setSize(int s){
        size = s;
    }
    
    public void setChain(Block[] c){
        chain = c;
    }
    
    
    public boolean equals(BlockChain that){
        boolean a = this.chain.equals(that.getChain());
        boolean b = this.size == (that.getSize());
        return a && b;
    }
    
    public void insertNewBlock(Block b){
        /*
        System.out.println("Inserting new block...");
        System.out.println("Current block size: " + size);
        System.out.println("Current block capacity: " + chain.length);
        System.out.println("Current block:\n" + b);
        */
        b.setPreviousHash(chain[size-1].getHash());
        chain[size++] = b;
        
    }
    public boolean isValidBlock(Block b){
        // TODO: check to see if block has a valid hash
        if (!isValidNonce(b.getNonce())){
            return false;
        }
        
        if (!isValidPrevHash(b.getPreviousHash())){
            return false;
        }
        
        if (!isValidHash(b.getHash())){
            return false;
        }
        
        return true;
    }
    
    public boolean isValidNonce(int n){
        if (n <= 0){
            return false;
        }
        return true;
    }
    
    public boolean isValidHash(String h){
        for (int i = 0; i < difficulty; i++){
            if (h.charAt(i) != '0'){
                return false;
            }
        }
        return true;
    }
    
    
    public boolean isValidPrevHash(String h){
        return h.equals(chain[size-1].getHash());
    }
    
    
    
    public boolean validateChain(BlockChain otherBlockChain){
        // TODO: make a consensus protocol
        return true;
    }
    
}