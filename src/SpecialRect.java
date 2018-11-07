
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chuan
 */
public class SpecialRect {
    private final Rectangle2D rect;
    private final Line2D l1;
    private final Line2D l2;
    
    public SpecialRect(Rectangle2D rect, Line2D l1, Line2D l2){
        this.rect = rect;
        this.l1 = l1;
        this.l2 = l2;
    }
    public Rectangle2D getRect(){
        return rect;
    }
    public Line2D getL1(){
        return l1;
    }
    public Line2D getL2(){
        return l2;
    }
}
