/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

/**
 * Class which specifies coordinates of the characteristic objects
 * @author marcin and pawel
 */
public class ObjectLocation {
    
    /**
     * x axis of the object position
     */
    private int x;
   
    /**
     *  y axis of the object position
     */
    private int y;
    
    /**
     * Parametric constructor
     * @param x x axis of the object
     * @param y y axis of the object
     */
    public ObjectLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * 
     * @return x axis of the object
     */
    public int getX(){
        return x;
    }
    
    /**
     * 
     * @return y axis of the object
     */
    public int getY(){
        return y;
    }
    
    /**
     * set parameters of the object
     * @param x x axis of the object
     * @param y y axis of the object
     */
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }
}
