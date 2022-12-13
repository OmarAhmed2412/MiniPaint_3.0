/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import org.json.simple.JSONObject;

/**
 *
 * @author moham
 */
public class Triangle extends SublimeShape{

    private Point p2;
    private Point p3;
    private final String name = "Line";
    
    public Triangle( Point startingCordinates, Point p2, Point p3) {
        super(startingCordinates);
        this.p2 = p2;
        this.p3 = p3;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

   
    
    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }
    
    
    @Override
    public void draw(Graphics canvas) {
        Color oldCOlor = canvas.getColor();
        canvas.setColor(this.getColor());
        canvas.drawPolygon(new int[] {this.getPosition().x, this.getP2().x, this.getP3().x}, new int[] {this.getPosition().y, this.getP2().y, this.getP3().y}, 3);
        canvas.setColor(this.getFillColor());
        canvas.fillPolygon(new int[] {this.getPosition().x, this.getP2().x, this.getP3().x}, new int[] {this.getPosition().y, this.getP2().y, this.getP3().y}, 3);
        canvas.setColor(oldCOlor);
    }
    
    
    float sign (Point p1, Point p2, Point p3)
    {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }
    
    
    @Override
    public boolean contains(Point point) {
        float d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(point, this.getPosition(), p2);
        d2 = sign(point, p2, p3);
        d3 = sign(point, p3, this.getPosition());

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    
    @Override
    public void moveTo(Point point) {
        int offsetX = point.x - this.getDraggingPoint().x;
        int offsetY = point.y - this.getDraggingPoint().y;
        this.setPosition(new Point(this.getPosition().x + offsetX, this.getPosition().y + offsetY));
        this.setP2(new Point(this.getP2().x + offsetX, this.getP2().y + offsetY));
        this.setP3(new Point(this.getP3().x + offsetX, this.getP3().y + offsetY));
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
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public JSONObject getJsonObject() {
        JSONObject jObj = super.getJsonObject();
        JSONObject jPoint2 = new JSONObject();
        JSONObject jPoint3 = new JSONObject();
        
        jPoint2.put("x", getP2().x);
        jPoint2.put("y", getP2().y);
        
        jPoint3.put("x", getP3().x);
        jPoint3.put("y", getP3().y);
        
        jObj.put("P2", jPoint2);
        jObj.put("P3", jPoint3);
        jObj.put("ShapeName", getName());
        return jObj;
    }
    public static Triangle JsonToShape(JSONObject jObj)
    {
        JSONObject positionJSON = (JSONObject) jObj.get("position");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        JSONObject point2JSON = (JSONObject) jObj.get("P2");
        Point P2 = new Point(Integer.parseInt(point2JSON.get("x").toString()), Integer.parseInt(point2JSON.get("y").toString()));
        JSONObject point3JSON = (JSONObject) jObj.get("P3");
        Point P3 = new Point(Integer.parseInt(point3JSON.get("x").toString()), Integer.parseInt(point3JSON.get("y").toString()));
        Triangle triangle = new Triangle(position, P2, P3);
        int colorRGB = Integer.parseInt(jObj.get("borderColor").toString());
        int fillColorRGB = Integer.parseInt(jObj.get("fillColor").toString());
        triangle.setColor(new Color(colorRGB, true));
        triangle.setFillColor(new Color(fillColorRGB, true));
        return triangle;
    }
    
}
