/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockmarket;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.lang.InterruptedException;

/**
 *
 * @author Manny
 */
public class Blockmarket {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        // TODO code application logic here
        
        //TEST
        /*
        BlockChain bc = new BlockChain(100, 4); // 100 block capacity, difficulty = 4 (valid hash must contain 4 leading ZEROs)
        BlockData transaction = new BlockData("fedcba0987654321", "1234567890abcdef", "MarketCoin", 100.0, 5.0, "abcdef5");
        Block newBlock = new Block(bc.getSize(), transaction, bc.getChain()[bc.getSize() - 1].getHash());
        MiningHelper helper = new MiningHelper(newBlock, bc);
        
        short nonce = helper.findNonce(); // generic function that generates random number within range 0~35,565
        helper.updateTargetBlock(nonce);
        // System.out.println(helper.getTargetBlock());
        // System.out.println(helper.validateHash());
        
        int count = 0;
        int runLimit = 1000000;
        while (!helper.validateHash() && count < runLimit){
            nonce = helper.findNonce();
            helper.updateTargetBlock(nonce);
            count++; // only run a limited number of times
        }
        
        if (!helper.validateHash()){
            System.out.println("No valid hash found");
        }
        else{
            System.out.println(count + " number of runs\n\n" + helper.getTargetBlock());
        }
        */
        
        BlockChain mbc = new BlockChain(20, 5);
        Random rand = new Random();
        BlockData[] transactions = new BlockData[15]; // queue of transactions
        for (int i = 0; i < transactions.length; i++){
            // generate random values for each transaction in queue
            String tempSellerID = generateRandomHash(16);
            String tempBuyerID = generateRandomHash(16);
            String tempCurrencyName = "MarketCoin";
            double tempCurrency = rand.nextInt(1000) + rand.nextDouble();
            double tempFee = 0.05; // percentage of price
            String tempItemID = generateRandomHash(8);
            transactions[i] = new BlockData(tempSellerID, tempBuyerID, 
                    tempCurrencyName, tempCurrency, tempCurrency * tempFee,
                    tempItemID);
            //System.out.println(transactions[i]);
        }
        Range mbcRange = new Range(0, transactions.length);
        Miner m1 = new Miner("mike", mbc, transactions, mbcRange);
        Miner m2 = new Miner("bill", mbc, transactions, mbcRange);
        Miner m3 = new Miner("kate", mbc, transactions, mbcRange);
        
        Thread t1 = new Thread(m1);
        Thread t2 = new Thread(m2);
        Thread t3 = new Thread(m3);
        //
        t1.start();
        t2.start();
        t3.start();
        //
        t1.join();        
        t2.join();
        t3.join();
        
        System.out.println(mbc.getSize());
        for (int i = 0; i < mbc.getSize(); i++){
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println(mbc.getChain()[i]);
            System.out.println("--------------------------------------------------------------------------------");
        }
        System.out.println(m1.getName() + "'s earnings: " + m1.getEarnings() + " MarketCoin");
        System.out.println(m2.getName() + "'s earnings: " + m2.getEarnings() + " MarketCoin");
        System.out.println(m3.getName() + "'s earnings: " + m3.getEarnings() + " MarketCoin");

    }
    
    static String generateRandomHash(int length){
        String temp_hash = "";
        String hex_digits = "1234567890abcdef";
        Random rand = new Random();
        for (int i = 0; i < length; i++){
            temp_hash = temp_hash.concat(Character.toString(hex_digits.charAt(rand.nextInt(hex_digits.length()))));
        }
        
        return temp_hash;
    }
    
    
}
