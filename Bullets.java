import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Bullets extends JPanel{
	private int x, y, speed, type;
	
	public Bullets(int x, int y, int type){
		this.type = type;
		this.x = x; this.y = y;
		speed = 4;
		// type 1 assigned to gold car and mission2 silver car - types 4 & 6
	}
	public void update(){
		if(RFGame.isRunning()){
			y+=speed*2;
		}else{x=RFGame.frmW; y=RFGame.frmH;}
	}
	public void setX(int x) {this.x = x; }
	public void setY(int y) {this.y = y; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.fillOval(x, y, 4, 6);
	}
}
