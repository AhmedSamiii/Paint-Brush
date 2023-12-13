import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//Name: Ahmed Sami Abdelfattah
//Track : DM

public class PaintBrush extends Applet {
    public static final int R = 1;
	public static final int L = 2;
	public static final int O = 3;
	public static final int E = 4;
	//Mode of ClearAll function is to prevent Applet form drawing currently drawing shape after pressing clearAll.
	public static final int C = 5;
	public static final int UN = 6;
	public static final int P = 7;

    int Mode;
	// fill shapes (true or false).
    boolean solid = false; 
	//This Flag to check That mouse is pressed and draged before entering relase to prevent saving an object after each press.
	boolean Flag=false ; 
    Color CurrentColor;
    Button rectangle;
	Button Line;
	Button Oval;
	Button Pencil;
	Button Eraser;
	Button Black; // to return to defult color 
    Button Red;
	Button Green;
	Button Blue;
	Button ClearAll;
	Button Undo;
    Checkbox fill;
    // Creating array list for Shapes
    ArrayList<GeoShape> ShapeList = new ArrayList<>();
	// Declare temporary variables for currently drawing shapes
	int X1_T, Y1_T, X2_T, Y2_T; 
	

    // Declaring all Constant that will be loaded once in init
    public void init() {
		//Shapes Buttons
        rectangle = new Button("Rectangle");
		Line = new Button("Line");
		Oval = new Button("Oval");
		Pencil=new Button("Pencil");
		Eraser = new Button("Eraser");
		//Color Buttons
        Red = new Button("Red");
		Green = new Button("Green");
		Blue = new Button("Blue");
		Black = new Button("Black");
		//Edit Buttons
		ClearAll = new Button("CrearAll");
		Undo =new Button("Undo");
        fill = new Checkbox("Fill");
		//Creating Objects form ActionListeners
        Press_Release p = new Press_Release();
        Drag d = new Drag();
        SolidCheck f = new SolidCheck();
        RecButton rb = new RecButton();
		LineButton lb = new LineButton();
		EraserButton er = new EraserButton();
		PencilButton pe = new PencilButton();
		OvalButton o = new OvalButton();
		ClearAllButton c= new ClearAllButton();
		UndoButton un = new UndoButton();
        RedColor r = new RedColor();
		GreenColor g = new GreenColor();
		BlueColor b = new BlueColor();
		BlackColor bl = new BlackColor();
		//Assigning every button to its correspoding button
        this.addMouseListener(p);
        this.addMouseMotionListener(d);
        fill.addItemListener(f);
        rectangle.addActionListener(rb);
		Oval.addActionListener(o);
		Pencil.addActionListener(pe);
		Eraser.addActionListener(er);
		ClearAll.addActionListener(c);
		Undo.addActionListener(un);
		Line.addActionListener(lb);
        Red.addActionListener(r);
		Green.addActionListener(g);
		Blue.addActionListener(b);
		Black.addActionListener(bl);
        add(rectangle);
		add(Oval);
		add(Line);
		add(Pencil);
		add(Eraser);
		add(ClearAll);
		add(Undo);
        add(Red);
		add(Green);
		add(Blue);
		add(Black);
        add(fill);	
    }
	
	public void paint(Graphics g) {
		//This for loop to keep drawing the previous shapes that already drawn.
        for (GeoShape Shape : ShapeList) {
            Shape.drawShape(g);
        }
		
		//This Switch of Mode for drawing the currently drawing shapes before theay are saved in the array list after releasing the mouse
		switch (Mode) {
                case R:
                    (new Rectangle (X1_T,Y1_T,X2_T,Y2_T,CurrentColor,solid)).drawShape(g);	
                    break;
				case L:
					(new Line (X1_T,Y1_T,X2_T,Y2_T,CurrentColor)).drawShape(g);
                    break;
				case O:
                    (new Oval (X1_T,Y1_T,X2_T,Y2_T,CurrentColor,solid)).drawShape(g);
                    break;
				case E:
                    (new Eraser (X1_T,Y1_T,10,10,Color.white)).drawShape(g);
                    break;
				case P:
                    (new Pencil (X1_T,Y1_T,6,6,CurrentColor)).drawShape(g);
                    break;	
				
            }
    }
	
//ActionListeners
    class RedColor implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            CurrentColor = Color.red;
        }
    }
	
	class GreenColor implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            CurrentColor = Color.green;
        }
    }
	
	class BlueColor implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            CurrentColor = Color.blue;
        }
    }
	
	class BlackColor implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            CurrentColor = Color.black;
        }
    }

    class RecButton implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Mode = R;
        }
    }
	
	class OvalButton implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            Mode = O;
        }
    }
	
	class LineButton implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            Mode = L;
        }
    }
	
	class EraserButton implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Mode = E;
        }
    }
	
	class PencilButton implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            Mode = P;
        }
    }
	
	class ClearAllButton implements ActionListener {
			public void actionPerformed(ActionEvent ev) {
				Mode = C; 
				ShapeList.clear();
				repaint();
				
			}
		}
		
	class UndoButton implements ActionListener {
			public void actionPerformed(ActionEvent ev) {
				Mode = UN; //need to select the shape again after undo.
				if(ShapeList.size()>0){
				ShapeList.remove(ShapeList.size()-1);
				repaint();
				}
				
			}
		}
	
    class SolidCheck implements ItemListener{
		
		public void itemStateChanged(ItemEvent e){
			if(solid==false)
			solid = true ;
			else if(solid==true){
			solid=false;
			}
			
		}
	} 
	
	// Mouse ActionListeners
	class Drag extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
			if (Mode==P){
			X1_T = e.getX();
            Y1_T = e.getY();
			ShapeList.add(new Pencil (X1_T,Y1_T,6,6,CurrentColor));
			repaint();
			
			}
			if (Mode==E){
			X1_T = e.getX();
            Y1_T = e.getY();
			ShapeList.add(new Eraser (X1_T,Y1_T,10,10,Color.white));
			repaint();
			
			}
            X2_T = e.getX();
            Y2_T = e.getY();
            repaint();
			Flag=true;
        }
    }
	

    class Press_Release extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
			//In eraser and pencil press shall do nothing to make the eraser effect doesnt depend on every click.
			if(Mode==P){
				
			}
			if(Mode==E){	
			}
			X1_T = e.getX();
            Y1_T = e.getY();
        }
		
		public void mouseReleased(MouseEvent e) {
		//checking flag true to ensure that user has pressed and dragged the mouse.	
		if(Flag==true){
		switch (Mode) {
                case R:
                  ShapeList.add(new Rectangle (X1_T,Y1_T,X2_T,Y2_T,CurrentColor,solid));
                    break;
				case L:
					ShapeList.add(new Line (X1_T,Y1_T,X2_T,Y2_T,CurrentColor));
                    break;	
				case O:
                  ShapeList.add(new Oval (X1_T,Y1_T,X2_T,Y2_T,CurrentColor,solid));
                    break;
				
				
				}
				Flag=false;
			}
		}	
    }
	
    // Creating Abstract Class for GeoShapes
    abstract class GeoShape {
        int x1, y1, x2, y2;
		Color c ;
		
        public GeoShape(int _x1, int _y1, int _x2, int _y2, Color _c) {
            x1 = _x1;
            y1 = _y1;
            x2 = _x2;
            y2 = _y2;
			c=_c;
        }
        public abstract void drawShape(Graphics g);	
    }

    // Creating Child Classes for every shape
    class Rectangle extends GeoShape {
		boolean solid;
		private int X_Small;
		private int Y_Small;
		
        public Rectangle(int _x1, int _y1, int _x2, int _y2, Color _c, boolean _solid) {
			
            super(_x1, _y1, _x2, _y2,_c);
			solid=_solid;
			 
        }

        public void drawShape(Graphics g) {
            if(x2<x1){X_Small=x2;}
				else{X_Small=x1;}
				if(y2<y1){Y_Small=y2;}
				else{Y_Small=y1;}
			if (solid == false) {
				
				g.setColor(c);
                g.drawRect(X_Small, Y_Small, Math.abs(x2 - x1),Math.abs( y2 - y1));
            } else {
				
				g.setColor(c);
                g.fillRect(X_Small, Y_Small, Math.abs(x2 - x1),Math.abs( y2 - y1));
            }
        }
    }
	
	class Oval extends GeoShape {
		boolean solid;
		private int X_Small;
		private int Y_Small;
		
        public Oval(int _x1, int _y1, int _x2, int _y2, Color _c, boolean _solid) {
			
            super(_x1, _y1, _x2, _y2,_c);
			solid=_solid;
        }

        public void drawShape(Graphics g) {
			//as we always want start point to be at the top-left corner which the smaller values 
            if(x2<x1){X_Small=x2;}
				else{X_Small=x1;}
				if(y2<y1){Y_Small=y2;}
				else{Y_Small=y1;}
			if (solid == false) {
				
				g.setColor(c);
                g.drawOval(X_Small, Y_Small, Math.abs(x2 - x1),Math.abs( y2 - y1));
            } else {
				
				g.setColor(c);
                g.fillOval(X_Small, Y_Small, Math.abs(x2 - x1),Math.abs( y2 - y1));
            }
        }
    }
	
	class Line extends GeoShape {
		

        public Line(int _x1, int _y1, int _x2, int _y2, Color _c) {
            super(_x1, _y1, _x2, _y2,_c);
        }
		
        public void drawShape(Graphics g) {
				g.setColor(c);
                g.drawLine(x1, y1, x2, y2); 
        }
    }
	
	class Eraser extends GeoShape {

        public Eraser(int _x1, int _y1, int _x2, int _y2,Color _c) {
            super(_x1, _y1, _x2, _y2,_c);
			
			x2=_x2;
			y2=_y2;
        }

        public void drawShape(Graphics g) {
                g.setColor(c);
                g.fillRect(x1, y1,x2,y2);
             
            }
			
    }
	
	class Pencil extends GeoShape {

        public Pencil(int _x1, int _y1, int _x2, int _y2,Color _c) {
            super(_x1, _y1, _x2, _y2,_c);
			
			x2=_x2;
			y2=_y2;
        }

        public void drawShape(Graphics g) {
                g.setColor(c);
                g.fillRect(x1, y1,x2,y2);
             
            }
			
    }

}