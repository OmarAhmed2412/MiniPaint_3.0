/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.lang.Math.*;
import org.json.simple.JSONObject;

/**
 *
 * @author moham
 */
public class LineSegment extends SublimeShape{

    private Point endingCoordinates;
    private final String name = "Line";

    public LineSegment(Point startingCoordinates, Point endingCoordinates) {
        super(startingCoordinates);
        this.endingCoordinates = endingCoordinates;
    }
    
    public void setEndPosition(Point position){
        this.endingCoordinates = position;
    }
    
    public Point getEndPosition(){
        return endingCoordinates;
    }
    @Override
    public void draw(Graphics canvas) {
        Color oldCOlor = canvas.getColor();
        canvas.setColor(this.getColor());
        canvas.drawLine(this.getPosition().x, this.getPosition().y, this.getEndPosition().x, this.getEndPosition().y);
        canvas.setColor(oldCOlor);
    }
    
    private double distance(Point A, Point B) {
        return Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }
    
    @Override
    public boolean contains(Point point) {
         double distance = Line2D.ptLineDist(getPosition().x, getPosition().y, 
                getEndPosition().x, getEndPosition().y, point.x, point.y);
        return distance < 4;
    }

    @Override
    public void moveTo(Point point) {
        int offsetX = point.x - this.getDraggingPoint().x;
        int offsetY = point.y - this.getDraggingPoint().y;
        this.setPosition(new Point(this.getPosition().x + offsetX, this.getPosition().y + offsetY));
        this.setEndPosition(new Point(this.getEndPosition().x + offsetX, this.getEndPosition().y + offsetY));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void displayCorners() {
            
    }

    @Override
    public void removeCorners() {
        
    }

    @Override
    public JSONObject getJsonObject() {
        JSONObject jObj = super.getJsonObject();
        JSONObject jEndPosition = new JSONObject();
        
        jEndPosition.put("x", getEndPosition().x);
        jEndPosition.put("y", getEndPosition().y);
        
        jObj.put("endPosition", jEndPosition);
        jObj.put("ShapeName", getName());
        return jObj;
    }
    public static LineSegment JsonToShape(JSONObject jObj)
    {
        JSONObject positionJSON = (JSONObject) jObj.get("position");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        JSONObject endPostionJSON = (JSONObject) jObj.get("endPosition");
        Point endPostion = new Point(Integer.parseInt(endPostionJSON.get("x").toString()), Integer.parseInt(endPostionJSON.get("y").toString()));
        LineSegment line = new LineSegment(position, endPostion);
        int colorRGB = Integer.parseInt(jObj.get("borderColor").toString());
        int fillColorRGB = Integer.parseInt(jObj.get("fillColor").toString());
        line.setColor(new Color(colorRGB, true));
        line.setFillColor(new Color(fillColorRGB, true));
        return line;
    }


    
}
