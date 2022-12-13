/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Point;
import org.json.simple.JSONObject;

/**
 *
 * @author moham
 */
public abstract class SublimeShape implements Shape, Moveable, Cloneable{

    private Point startingCordinates;
    private Color color;
    private Color fillColor;
    private Point draggingPoint;
    private boolean cornersOn;

    
    public SublimeShape(Point startingCordinates) {
        this.startingCordinates = startingCordinates;
    }
    
    
    @Override
    public void setPosition(Point position) {
        this.startingCordinates = position;
    }
    @Override
    public Point getPosition() {
        return new Point(this.startingCordinates.x, startingCordinates.y);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return this.fillColor;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
     
    @Override
    public abstract void draw(Graphics canvas);
    
    
    @Override
    public void setDraggingPoint(Point point) {
        this.draggingPoint = point;
    }

    @Override
    public Point getDraggingPoint() {
        return this.draggingPoint;
    }

    public boolean isCornersOn() {
        return cornersOn;
    }

    public void setCornersOn(boolean cornersOn) {
        this.cornersOn = cornersOn;
    }
    
    
    @Override
    public abstract boolean contains(Point point);

    @Override
    public abstract void moveTo(Point point);
    
    public abstract String getName();
    
    public abstract void displayCorners();
    
    public abstract void removeCorners();
    
    public abstract void resize(Rectangle corner, Point point);
    public JSONObject getJsonObject()
    {
        JSONObject jObj = new JSONObject();
        JSONObject jPosition = new JSONObject();
        jPosition.put("x", getPosition().x);
        jPosition.put("y", getPosition().y);
        jObj.put("position", jPosition);
        jObj.put("borderColor", getColor().getRGB());
        jObj.put("fillColor", getFillColor().getRGB());
        return jObj;
    }
}
