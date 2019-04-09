/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockmarket;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author Manny
 */
public class Block {
    private int blockNumber;
    private int nonce;
    private long timestamp;
    private BlockData data;
    private String hash, prevHash;
    private Date date = new Date();
    private MessageDigest hasher;
    
    public Block() throws NoSuchAlgorithmException { // GENESIS BLOCK
        timestamp = date.getTime() / 1000; // seconds from epoch
        blockNumber = 0;
        nonce = 0;
        hash = "0000000000000000000000000000000000000000000000000000000000000000";
        hasher = MessageDigest.getInstance("SHA-256");
        
    }
    public Block(int bn, BlockData d, String p) throws NoSuchAlgorithmException {
        this();
        blockNumber = bn;
        data = d;
        prevHash = p;
    }
    /*
    public Block(int bn, short n, BlockData d, String p) throws NoSuchAlgorithmException {
        blockNumber = bn; // blocks cannot be initiated with block number, the block chain will decide the block number
        data = d;
        prevHash = p;
    }
    */
    public Block(int bn, BlockData d, String p, int n, String h, MessageDigest md, long t) throws NoSuchAlgorithmException{
        this(bn, d, p);
        nonce = n;
        hash = h;
        hasher = md;
        timestamp = t;
    }
    
    public Block copy()  throws NoSuchAlgorithmException{
        Block tempBlock = new Block(blockNumber, data.copy(), prevHash, nonce, hash, hasher, timestamp);
        return tempBlock;
    }
    
    public void setBlockNumber(int x) { blockNumber = x; }
    public void setNonce(int x) { nonce = x; }
    public void setBlockData(BlockData d) { data = d; }
    public void setHash(String h) { hash = h; }
    public void setPreviousHash(String p) { prevHash = p; }
    
    public int getBlockNumber() { return blockNumber;}
    public int getNonce() { return nonce; }
    public BlockData getBlockData() { return data; }
    public String getHash() { return hash; }
    public String getPreviousHash() { return prevHash; }
    
    private String prepDigest(){ // this makes a string that is to be used for the digest ONLY
        String temp_str = "";
        temp_str = temp_str.concat(Integer.toString(blockNumber)).concat("\n");
        temp_str = temp_str.concat(Integer.toString(nonce)).concat("\n");
        temp_str = temp_str.concat(Long.toString(timestamp)).concat("\n");
        temp_str = temp_str.concat(data.prepDigest()).concat("\n");
        temp_str = temp_str.concat(prevHash).concat("\n");
        
        return temp_str;
    } 
    
    public long getTimeStamp() { 
        updateTimeStamp();
        return timestamp;
    }   
    
    @Override
    public String toString(){
        String temp_str = "";
        temp_str = temp_str.concat("Block Number:\t\t" + Integer.toString(blockNumber)).concat("\n");
        temp_str = temp_str.concat("Nonce:\t\t\t" + Integer.toString(nonce)).concat("\n");
        temp_str = temp_str.concat("Timestamp (seconds):\t" + Long.toString(timestamp)).concat("\n");
        temp_str = temp_str.concat("Transaction Data:\n" + data.toString()).concat("\n");
        temp_str = temp_str.concat("Hash:\t\t\t" + hash).concat("\n");
        temp_str = temp_str.concat("Previous Hash:\t\t" + prevHash).concat("\n");
        
        return temp_str;
    }
    
    public void updateTimeStamp() { timestamp = date.getTime() / 1000; }
    
    public void updateHash() throws NoSuchAlgorithmException{
        updateTimeStamp();
        byte[] hash_bytes = hasher.digest(this.prepDigest().getBytes(StandardCharsets.UTF_8));
        hash = bytesToHex(hash_bytes);
    }
    
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) 
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}    
