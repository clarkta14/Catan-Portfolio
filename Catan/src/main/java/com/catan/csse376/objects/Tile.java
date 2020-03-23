/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catan.csse376.objects;

import java.awt.Polygon;

/**
 *
 * @author Indresh
 */
public class Tile {
    private TileType type;
    private int number;
    private Position pos;
    private Polygon hex;
    private Boolean isRobber;
    
    /**
     * Constructor, with given params for its fields
     * @param x the x coordinate
     * @param y the y coordinate
     * @param n the number of the Tile
     * @param typ the type of the Tile
    */
    public Tile(Position p, int n, TileType typ) {
        this.isRobber = false;    
        this.pos = p;
        this.number = n;
        this.type = typ;
    }
    
    public void setHex(Polygon poly){
        this.hex = poly;
    }
    
    public Position getPosition(){
        return this.pos;
    }
    
    public TileType getType(){
        return this.type;
    }
    
    public int getNumber(){
        return this.number;
    }
    
    public void setRobber(){
        this.isRobber = true;
    }
    
    public Boolean isRobber(){
        return this.isRobber;
    }
}
