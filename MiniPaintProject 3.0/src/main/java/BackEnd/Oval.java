/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import FrontEnd.DrawingFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import static java.lang.Math.pow;
import org.json.simple.JSONObject;

/**
 *
 * @author moham
 */
public class Oval extends SublimeShape{
    
    private int verticalRadius;
    private int horizontalRadius;
    private final String name = "Oval";
    private Rectangle ULCorner;
    private Rectangle URCorner;
    private Rectangle LLCorner;
    private Rectangle LRCorner;

    public Oval( Point startingCordinates, int horizontalRadius, int verticalRadius) {
        super(startingCordinates);
        this.verticalRadius = verticalRadius;
        this.horizontalRadius = horizontalRadius;
    }

    public int getVerticalRadius() {
        return verticalRadius;
    }

    public void setVerticalRadius(int verticalRadius) {
        this.verticalRadius = verticalRadius;
    }

    public int getHorizontalRadius() {
        return horizontalRadius;
    }

    public void setHorizontalRadius(int horizontalRadius) {
        this.horizontalRadius = horizontalRadius;
    }

    
   
    @Override
    public void draw(Graphics canvas) {
        Color oldCOlor = canvas.getColor();
        canvas.setColor(this.getColor());
        canvas.drawOval(this.getPosition().x, this.getPosition().y, 2*horizontalRadius, 2*verticalRadius);
        canvas.setColor(this.getFillColor());
        canvas.fillOval(this.getPosition().x, this.getPosition().y, 2*horizontalRadius, 2*verticalRadius);
        canvas.setColor(oldCOlor);
    }

    @Override
    public boolean contains(Point point) {
        Point center = new Point(this.getPosition().x + this.getHorizontalRadius(), this.getPosition().y + this.getVerticalRadius());
        return (pow(point.x - center.x, 2) / pow(horizontalRadius, 2)) + (pow(point.y - center.y, 2) / pow(verticalRadius, 2)) <= 1;
    }

    @Override
    public void moveTo(Point point) {
        int offsetX = point.x - this.getDraggingPoint().x;
        int offsetY = point.y - this.getDraggingPoint().y;
        this.setPosition(new Point(this.getPosition().x + offsetX, this.getPosition().y + offsetY));
        this.ULCorner.setPosition(new Point(this.ULCorner.getPosition().x + offsetX, this.ULCorner.getPosition().y + offsetY));
        this.URCorner.setPosition(new Point(this.URCorner.getPosition().x + offsetX, this.URCorner.getPosition().y + offsetY));
        this.LLCorner.setPosition(new Point(this.LLCorner.getPosition().x + offsetX, this.LLCorner.getPosition().y + offsetY));
        this.LRCorner.setPosition(new Point(this.LRCorner.getPosition().x + offsetX, this.LRCorner.getPosition().y + offsetY));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void displayCorners() {
        this.setCornersOn(true);

        Point upperLeftCorner = new Point(getPosition().x - 5, getPosition().y - 5);
        Point upperRightCorner = new Point(getPosition().x + horizontalRadius * 2 - 5, getPosition().y - 5);
        Point lowerLeftCorner = new Point(getPosition().x - 5, getPosition().y + verticalRadius * 2 - 5);
        Point lowerRightCorner = new Point(getPosition().x + horizontalRadius * 2 - 5, getPosition().y + verticalRadius * 2 - 5);

        this.ULCorner = new Rectangle(upperLeftCorner, 10, 10);
        this.URCorner = new Rectangle(upperRightCorner, 10, 10);
        this.LLCorner = new Rectangle(lowerLeftCorner, 10, 10);
        this.LRCorner = new Rectangle(lowerRightCorner, 10, 10);

        DrawingFrame.getPanel().getCorners().add(ULCorner);
        DrawingFrame.getPanel().getCorners().add(URCorner);
        DrawingFrame.getPanel().getCorners().add(LLCorner);
        DrawingFrame.getPanel().getCorners().add(LRCorner);

        DrawingFrame.getPanel().refresh(null);
        
    }

    @Override
    public void removeCorners() {
        this.setCornersOn(false);

        DrawingFrame.getPanel().getCorners().remove(ULCorner);
        DrawingFrame.getPanel().getCorners().remove(URCorner);
        DrawingFrame.getPanel().getCorners().remove(LLCorner);
        DrawingFrame.getPanel().getCorners().remove(LRCorner);

        DrawingFrame.getPanel().refresh(null);
    }

    @Override
    public void resize(Rectangle corner, Point point) {
        int index = DrawingFrame.getPanel().getCorners().indexOf(corner);
        int x1_difference = point.x - this.getPosition().x;
        int y1_difference = point.y - this.getPosition().y;
        int x2_difference = point.x - this.URCorner.getPosition().x - 5;
        int y2_difference = point.y - this.URCorner.getPosition().y - 5;
        int x3_difference = point.x - this.LLCorner.getPosition().x - 5;
        int y3_difference = point.y - this.LLCorner.getPosition().y - 5;
        int x4_difference = point.x - this.LRCorner.getPosition().x - 5;
        int y4_difference = point.y - this.LRCorner.getPosition().y - 5;
        switch (index) {
            case 0 -> {
                //top left
                
                this.setPosition(point);

                int oldWidth = this.horizontalRadius * 2;
                int oldHeight = this.verticalRadius * 2;
                this.horizontalRadius = getHorizontalRadius() - x1_difference;
                this.verticalRadius = getVerticalRadius()- y1_difference;
                while(this.horizontalRadius <= 0)
                {
                    this.horizontalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.verticalRadius <= 0)
                {
                    this.verticalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
//                this.setPosition(ULCorner.getPosition());
                if(horizontalRadius * 2 != oldWidth)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x + x1_difference , ULCorner.getPosition().y ));
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x + x1_difference , LLCorner.getPosition().y ));
                    URCorner.setPosition(new Point(URCorner.getPosition().x - x1_difference , URCorner.getPosition().y ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x - x1_difference , LRCorner.getPosition().y ));
                }
                if(verticalRadius * 2 != oldHeight)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x , ULCorner.getPosition().y + y1_difference ));
                    URCorner.setPosition(new Point(URCorner.getPosition().x , URCorner.getPosition().y + y1_difference ));
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x , LLCorner.getPosition().y - y1_difference ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x , LRCorner.getPosition().y - y1_difference ));
                }
            }

            case 1 -> {
                //top right
                int oldWidth = this.horizontalRadius * 2;
                int oldHeight = this.verticalRadius * 2;
                this.setPosition(new Point(getPosition().x, point.y));
                this.horizontalRadius = getHorizontalRadius()+ x2_difference / 2;
                this.verticalRadius = getVerticalRadius() - y2_difference / 2;
                while(this.horizontalRadius <= 0)
                {
                    this.horizontalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.verticalRadius <= 0)
                {
                    this.verticalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                this.setPosition(new Point(getPosition().x, point.y));
                if(horizontalRadius * 2 != oldWidth)
                {
                    URCorner.setPosition(new Point(URCorner.getPosition().x + x2_difference, URCorner.getPosition().y ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x + x2_difference, LRCorner.getPosition().y ));
                }
                if(verticalRadius * 2 != oldHeight)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x , ULCorner.getPosition().y + y2_difference ));
                    URCorner.setPosition(new Point(URCorner.getPosition().x , URCorner.getPosition().y + y2_difference ));
                }
            }
                
            case 2 -> {
                //bottom left
                
                this.setPosition(new Point(point.x, getPosition().y));
                int oldWidth = this.horizontalRadius * 2;
                int oldHeight = this.verticalRadius * 2;
                this.horizontalRadius = getHorizontalRadius() - x3_difference / 2;
                this.verticalRadius = getVerticalRadius() + y3_difference / 2;
                while(this.horizontalRadius <= 0)
                {
                    this.horizontalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.verticalRadius <= 0)
                {
                    this.verticalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                if(horizontalRadius * 2 != oldWidth)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x + x3_difference , ULCorner.getPosition().y ));
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x + x3_difference , LLCorner.getPosition().y ));
                }
                if(verticalRadius * 2 != oldHeight)
                {
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x , LLCorner.getPosition().y + y3_difference ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x , LRCorner.getPosition().y + y3_difference ));
                }
            }
                
            case 3 -> {
                //bottom right
                int oldWidth = this.horizontalRadius * 2;
                int oldHeight = this.verticalRadius * 2;
                this.horizontalRadius = getHorizontalRadius()+ x4_difference / 2;
                this.verticalRadius = getVerticalRadius()+ y4_difference / 2;
                while(this.horizontalRadius <= 0)
                {
                    this.horizontalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.verticalRadius <= 0)
                {
                    this.verticalRadius = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                if(horizontalRadius * 2 != oldWidth)
                {
                    URCorner.setPosition(new Point(URCorner.getPosition().x + x4_difference , URCorner.getPosition().y ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x + x4_difference , LRCorner.getPosition().y ));
                }
                if(verticalRadius * 2 != oldHeight)
                {
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x , LLCorner.getPosition().y + y4_difference ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x , LRCorner.getPosition().y + y4_difference ));
                }
            }
                
            default -> {
            }
        }
    }
    @Override
    public JSONObject getJsonObject() {
        JSONObject jObj = super.getJsonObject();
        jObj.put("horizontalRadius", getHorizontalRadius());
        jObj.put("verticalRadius", getVerticalRadius());
        jObj.put("ShapeName", getName());
        return jObj;
    }
    public static Oval JsonToShape(JSONObject jObj)
    {
        JSONObject positionJSON = (JSONObject) jObj.get("position");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        int horizontalRadius = Integer.parseInt(jObj.get("horizontalRadius").toString());
        int verticalRadius = Integer.parseInt((String) jObj.get("verticalRadius").toString());
        Oval oval = new Oval(position, horizontalRadius, verticalRadius);
        int colorRGB = Integer.parseInt(jObj.get("borderColor").toString());
        int fillColorRGB = Integer.parseInt(jObj.get("fillColor").toString());
        oval.setColor(new Color(colorRGB, true));
        oval.setFillColor(new Color(fillColorRGB, true));
        return oval;
    }
    
}
