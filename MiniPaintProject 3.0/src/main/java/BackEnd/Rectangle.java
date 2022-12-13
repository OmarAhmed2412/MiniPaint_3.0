/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import FrontEnd.DrawingFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import org.json.simple.JSONObject;

/**
 *
 * @author Blu-Ray
 */
public class Rectangle extends SublimeShape {

    private int width, height;
    private final String name = "Rectangle";
    private Rectangle ULCorner;
    private Rectangle URCorner;
    private Rectangle LLCorner;
    private Rectangle LRCorner;

    public Rectangle(Point startingCordinates, int width, int height) {
        super(startingCordinates);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void draw(Graphics canvas) {
        Color oldCOlor = canvas.getColor();
        canvas.setColor(this.getColor());
        canvas.drawRect(this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        canvas.setColor(this.getFillColor());
        canvas.fillRect(this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        canvas.setColor(oldCOlor);
    }

    @Override
    public boolean contains(Point point) {
        int xRangeStart = this.getPosition().x;
        int xRangeEnd = this.getPosition().x + width;
        int yRangeStart = this.getPosition().y;
        int yRangeEnd = this.getPosition().y + height;

        return !(point.x < xRangeStart || point.x > xRangeEnd || point.y < yRangeStart || point.y > yRangeEnd);
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

    public void displayCorners() {
        this.setCornersOn(true);

        Point upperLeftCorner = new Point(getPosition().x - 5, getPosition().y - 5);
        Point upperRightCorner = new Point(getPosition().x + width - 5, getPosition().y - 5);
        Point lowerLeftCorner = new Point(getPosition().x - 5, getPosition().y + height - 5);
        Point lowerRightCorner = new Point(getPosition().x + width - 5, getPosition().y + height - 5);

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

                int oldWidth = this.width;
                int oldHeight = this.height;
                this.width = getWidth() - x1_difference;
                this.height = getHeight() - y1_difference;
                while(this.width <= 0)
                {
                    this.width = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.height <= 0)
                {
                    this.height = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                if(width != oldWidth)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x + x1_difference , ULCorner.getPosition().y ));
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x + x1_difference , LLCorner.getPosition().y ));
                }
                if(height != oldHeight)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x , ULCorner.getPosition().y + y1_difference ));
                    URCorner.setPosition(new Point(URCorner.getPosition().x , URCorner.getPosition().y + y1_difference ));
                }
            }

            case 1 -> {
                //top right
                int oldWidth = this.width;
                int oldHeight = this.height;
                this.width = getWidth() + x2_difference;
                this.height = getHeight() - y2_difference;
                this.setPosition(new Point(getPosition().x, point.y));
                while(this.width <= 0)
                {
                    this.width = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.height <= 0)
                {
                    this.height = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                if(width != oldWidth)
                {
                    URCorner.setPosition(new Point(URCorner.getPosition().x + x2_difference , URCorner.getPosition().y ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x + x2_difference , LRCorner.getPosition().y ));
                }
                if(height != oldHeight)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x , ULCorner.getPosition().y + y2_difference ));
                    URCorner.setPosition(new Point(URCorner.getPosition().x , URCorner.getPosition().y + y2_difference ));
                }
            }
                
            case 2 -> {
                //bottom left
                
                this.setPosition(new Point(point.x, getPosition().y));
                int oldWidth = this.width;
                int oldHeight = this.height;
                this.width = getWidth() - x3_difference;
                this.height = getHeight() + y3_difference;
                while(this.width <= 0)
                {
                    this.width = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.height <= 0)
                {
                    this.height = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                if(width != oldWidth)
                {
                    ULCorner.setPosition(new Point(ULCorner.getPosition().x + x3_difference , ULCorner.getPosition().y ));
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x + x3_difference , LLCorner.getPosition().y ));
                }
                if(height != oldHeight)
                {
                    LLCorner.setPosition(new Point(LLCorner.getPosition().x , LLCorner.getPosition().y + y3_difference ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x , LRCorner.getPosition().y + y3_difference ));
                }
            }
                
            case 3 -> {
                //bottom right
                int oldWidth = this.width;
                int oldHeight = this.height;
                this.width = getWidth() + x4_difference;
                this.height = getHeight() + y4_difference;
                while(this.width <= 0)
                {
                    this.width = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                while(this.height <= 0)
                {
                    this.height = 0;
                    this.setPosition(new Point(ULCorner.getPosition().x + 5, ULCorner.getPosition().y + 5));
                    break;
                }
                if(width != oldWidth)
                {
                    URCorner.setPosition(new Point(URCorner.getPosition().x + x4_difference , URCorner.getPosition().y ));
                    LRCorner.setPosition(new Point(LRCorner.getPosition().x + x4_difference , LRCorner.getPosition().y ));
                }
                if(height != oldHeight)
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
        jObj.put("width", getWidth());
        jObj.put("height", getHeight());
        jObj.put("ShapeName", getName());
        return jObj;
    }
    public static Rectangle JsonToShape(JSONObject jObj)
    {
        JSONObject positionJSON = (JSONObject) jObj.get("position");
        Point position = new Point(Integer.parseInt(positionJSON.get("x").toString()), Integer.parseInt(positionJSON.get("y").toString()));
        int width = Integer.parseInt((String) jObj.get("width").toString());
        int height = Integer.parseInt(jObj.get("height").toString());
        Rectangle rectangle = new Rectangle(position, width, height);
        int colorRGB = Integer.parseInt(jObj.get("borderColor").toString());
        int fillColorRGB = Integer.parseInt(jObj.get("fillColor").toString());
        rectangle.setColor(new Color(colorRGB, true));
        rectangle.setFillColor(new Color(fillColorRGB, true));
        return rectangle;
    }
}

