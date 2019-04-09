/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockmarket;

/**
 *
 * @author Manny
 */
public class BlockData {
    private String seller;
    private String buyer;
    private String currencyName;
    private double currency, fee;
    private String itemID; // purchase, return, gift
    private String miner;
    
    public BlockData(){
        seller = "NULL";
        buyer = "NULL";
        currencyName = "NULL";
        currency = 0; fee = 0;
        itemID = "NULL";
    }
    
    public BlockData(String s, String b, String cn, double c, double f, String iid){
        seller = s;
        buyer = b;
        currencyName = cn;
        currency = c; 
        fee = f;
        itemID = iid;
    }
    
    public double getFee() { return fee; }
    
    public BlockData copy(){
        BlockData temp = new BlockData(seller, buyer, currencyName, currency, fee, itemID);
        temp.setMiner(miner);
        //System.out.println("Copying data:\n" + this.toString());
        return temp;
    }
    
    public String prepDigest(){
        String temp_str = seller.concat(buyer).concat(currencyName).concat(Double.toString(currency)).concat(Double.toString(fee)).concat(itemID);
        return temp_str;
    }
    
    public void setMiner(String m){
        miner = m;
    }
    
    public String toString(){
        String temp_str = "";
        temp_str = temp_str.concat("Seller ID:\t" + seller + "\n");
        temp_str = temp_str.concat("Buyer ID:\t" + buyer + "\n");
        temp_str = temp_str.concat("Currency Name:\t" + currencyName + "\n");
        temp_str = temp_str.concat("Currency:\t" + currency + "\n");
        temp_str = temp_str.concat("Fee:\t\t" + fee + "\n");
        temp_str = temp_str.concat("Item ID:\t" + itemID + "\n");
        temp_str = temp_str.concat("Miner:\t" + miner + "\n");
            
        return temp_str;
    }
    
}
