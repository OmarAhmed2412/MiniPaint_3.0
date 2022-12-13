/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BackEnd;

import java.awt.Point;

/**
 *
 * @author moham
 */
public interface Moveable {
    
    
    public void setDraggingPoint(Point point);
    
    
    public Point getDraggingPoint();
    
    
    public boolean contains(Point point);
    
    
    public void moveTo(Point point);
}
