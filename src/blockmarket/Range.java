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
public class Range {
    private int start;
    private int length;
    private int pointer; // current value of the range
    
    public Range(int start, int length){
        this.start = start;
        this.pointer = start;
        this.length = length;
    }
    
    public int getStart() {return start;}
    public int getLength() {return length;}
    public int getEnd() {return start+length;}
    public int getPointer() {return pointer;}
    
    public void setStart(int s) {start=s;}
    public void setLength(int l) {length=l;}
    public void setPointer(int p) {pointer=p;}
    
    public void incrementPointer() {pointer++;}
    public boolean isComplete() {return pointer >= start+length;} // pointer is out of range
    
}
