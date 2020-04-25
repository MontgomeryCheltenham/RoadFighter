import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Others extends JPanel{
	private int x, y, othW, othH; //othW n H 40, 60 not set programatically!!
	private int rn, range, maxX, minX, health, type, value, gunPower;
	int frmW = RFGame.frmW; int frmH = RFGame.frmH; int roadW = RFGame.roadW; 
	Image genImg, angryDriverImg, angryBikerImg, angryDriver2Img, 
		bikeImg, cyanImg, goldImg, goldBackImg, darkBikerImg, silverBackImg;
	private boolean drives, drawWarning, goesRight, hitYourCar;
	
	public Others(int type){
		this.type = type;
		othW = 40; othH = 60; // set programatically!
		maxX = frmW/2 + roadW/2 - othW; 
		minX = frmW/2 - roadW/2; 
		range = maxX - minX;
		rn = (int)(Math.random()*range)+minX;
		x = rn; y = -(int)(Math.random()*300)+10;
		angryDriverImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/angrydriver.png");
		angryDriver2Img = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/angrydriver2.png");
		angryBikerImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/angryBiker.png");
		bikeImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/bike.png");
		cyanImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/othercar1.png");
		goldImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/othercar2.png");
		goldBackImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/othercar2back.png");
		silverBackImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/othercar3back.png");
		darkBikerImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/darkBiker.png");
		drives = true; goesRight = true;
		if(type==1){ genImg = bikeImg; health = 1; value = 10; othW = 15; othH = 25; } //bike
		if(type==2){ genImg = cyanImg; health = 30; value = 100;} //cyan car
		if(type==3){ genImg = goldImg; health = 100; value = 250; } //gold car
		if(type==4){ genImg = goldBackImg; health = 100; value = 300; gunPower = 50;} //gold car shooting at ya, driving opposite
		if(type==5){ genImg = darkBikerImg; health = 1; value = 2000; othW = 15; othH = 25;  //dark biker mission1, not too close to road edge fi hard fi dead
					x = (int)(Math.random()*((frmW/2 + roadW/2 - othW-30)-(frmW/2 - roadW/2+30)))+(frmW/2 - roadW/2+30);
					gunPower = 50;}
		if(type==6){ genImg = silverBackImg; health = 200; value = 2000; gunPower = 50;} //silver car mission 2
	}
	
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	public int getX() {return x; }
	public int getY() {return y; }
	public int getW() {return othW; }
	public int getH() {return othH; }
	public int getHealth() {return health; }
	public int getType() {return type; }
	public int getValue() {return value; }
	public int getGunPower() {return gunPower; }
	public boolean isDriving() { if(drives) {return true; } return false;}
	public boolean isShooting() { if(gunPower>0){return true;} {return false;}}
	public void setHitYourCar(boolean hit) {this.hitYourCar = hit;}
	public boolean getHitYourCar(){return hitYourCar;}
	
	public void update(){
		if(!RFGame.isRunning()){  //if shopping or mission quest
			if(type==4||type==5||type==6){y++; 
				if(y>frmH){
					if(type==5){x = (int)(Math.random()*((frmW/2 + roadW/2 - othW-30)-(frmW/2 - roadW/2+30)))+(frmW/2 - roadW/2+30);}
					x = (int)(Math.random()*((frmW/2 + roadW/2 - othW)-(frmW/2 - roadW/2))+(frmW/2 - roadW/2));
					y = -(int)(Math.random()*300)+10;
				}
			}
			else {y--; 
				if(y<-othH){
					x = (int)(Math.random()*((frmW/2 + roadW/2 - othW)-(frmW/2 - roadW/2))+(frmW/2 - roadW/2));
					y = (int)(Math.random()*300)+frmH+10;
				}
			}
		} else //not shopping or mission
		if(y<=frmH){ 
			if(type==3){ 
				if(!goesRight){ x--; y++; }
				if(x<=frmW/2-roadW/2+20) { goesRight = true; }
				if(goesRight) {x++; y++; }
				if(x>=frmW/2+roadW/2-othW-20) {goesRight = false; }
			}else if(type==4||type==5){
				y+=2;
			}else{ y++; } 
		} else { 
			reborn();
		}
	}
	public void damage (int damage){
		this.health -= damage;
	}
	public void withinBounds(){
		if(x<=frmW/2-roadW/2){
			if(type==1||type==5) { health = 0; } else {x = frmW/2-roadW/2+20; damage(10); }
		}
		if(x>=frmW/2+roadW/2-othW){ 
			if(type==1||type==5) { health = 0; } else {x = frmW/2+roadW/2-othW-20; damage(10); }
		}
		if(health<=0){ blowUp(); }
	}
	public void col(String dir, int speed){
		if(drives){ //no col with blowup
			if(dir.equals("left")) { 
				if(type==1){x+=speed*3+15; }
				if(type==2){x+=speed*2+10; damage(speed*2); }
				if(type==3||type==4||type==6){x+=speed*2+10; damage(10); }
				if(type==5){x+=speed*2+5; }
			}
			if(dir.equals("right")) { 
				if(type==1){x-=(speed*3+15); }
				if(type==2){x-=(speed*2+10); damage(speed*2); }
				if(type==3||type==4||type==6){x-=(speed*2+10); damage(10); }
				if(type==5){x-=(speed*2+5); }
			}
			if(dir.equals("front")) { 
				if(type==1){y+=speed*3+10; }
				if(type==2){y+=speed*2+10; damage(speed*2);}
				if(type==3||type==4||type==6){y+=speed*2+10; damage(20); }
				if(type==5){y+=speed*2+5; }
			}
			if(dir.equals("rear")) { 
				if(type==1){y-=(speed*3+10);}
				if(type==2){y-=(speed*2+20); damage(speed*2);}
				if(type==3||type==4||type==6){y-=(speed*2+20); damage(10); }
				if(type==5){y-=(speed*2+5); }
			}
		}
		withinBounds();
	}
	public void othHitBullet(){
		if(drives&&type!=5){ //type 5 unshootable, haffi push off road
			damage(RFGame.gunPower());
			if(health<=0){ blowUp(); }
		}
	}
	public void blowUp(){ 
		RFGame.score = true; 
		if(type==1){ genImg = RFGame.smallCrashImg; } else {
		genImg = RFGame.crashImg; drives=false; hostileWarning(); }
		Thread localThread = new Thread(new Runnable(){
			public void run(){ 
				try { Thread.sleep(500); reborn(); Thread.sleep(2000); 
				if(type==5){ RFGame.mission1comp = true; type=1;}
				if(type==6){ RFGame.mission2comp = true; type=4;}}
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		});
		localThread.start(); 
	}
	public void reborn(){
		x = (int)(Math.random()*range)+minX; y = -(int)(Math.random()*300)+10;
		if(type==1) {health = 1; genImg = bikeImg; } 
		if(type==2) {health = 30; genImg = cyanImg; }
		if(type==3) {health = 100; genImg = goldImg; }
		if(type==4) {health = 100; genImg = goldBackImg; }
		//type 5 not reborn - end of mission 1
		//type 6 - end of mission 2
		drives = true;
	}
	public void hostileWarning(){
		if(type!=1){
			Thread localThread = new Thread(new Runnable(){
				public void run(){
					drawWarning = true;
					try { Thread.sleep(2000); drawWarning = false; }
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			});
			localThread.start();
		}
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.drawImage(genImg, x, y, this);
		if(drawWarning){ 
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(frmW/2+RFGame.roadW/2+12, 12, 148, 90);
			if(type==2||type==3||type==4) { g.drawImage(angryDriverImg, frmW/2+RFGame.roadW/2+14, 14, this); }
			if(type==5) { g.drawImage(angryBikerImg, frmW/2+RFGame.roadW/2+14, 14, this); }
			if(type==6) { g.drawImage(angryDriver2Img, frmW/2+RFGame.roadW/2+14, 14, this); }
			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahome", Font.BOLD, 14));
			g.drawString("Imma end you punk!", frmW/2+RFGame.roadW/2+14, 96);
		}
	}
}
