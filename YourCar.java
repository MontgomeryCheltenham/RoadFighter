import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class YourCar extends JPanel{
	private int carX, carY, dX, dY, carRightX, carLeftX,
		health, maxHealth, speed, topSpeed, points;
	static int carW, carH;
	public static Image carImg, carRightImg, carLeftImg;
	ShopPanel shop = new ShopPanel();
	MissionPanel mission = new MissionPanel();
	int roadW = RFGame.roadW; int frmW = RFGame.frmW; int frmH = RFGame.frmH;
	public static boolean gameOver, enteringShop, leavingShop, enteringMission, leavingMission;
	
	public YourCar(){
		carW = 40; carH = 60; //FIX set programatically
		carX = frmW/2 - carW/2; carY = frmH*3/4;
		dX = 10; dY = 10; speed = 1; topSpeed = 6;
		maxHealth = 500; health = maxHealth; points = 0;
		carImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/car.png");
		carRightImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/carright.png");
		carLeftImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/carleft.png");
		
	}
	public int getX() {return carX; } 
	public int getY() {return carY; }
	public int getHealth() { if(health>=0) {return health; } else { return 0; }}
	public int getMaxHealth() {return maxHealth; }
	public int getSpeed() {return speed; } public int getTopSpeed() {return topSpeed; }
	public int getPoints() {return points; }
	
	public void speedUp(){ if(speed<topSpeed){ speed++; }}
	public void slowDown(){ if(speed>0){ speed--; }}
	
	public boolean isGameOver(){ return gameOver; }
	
	public void damage (int damage){
		this.health -= damage;
	}
	public void addPoints(int pts){
		this.points += pts;
	}
	public void update(){
		withinBounds(); 
		if(health<=0){ gameOver = true; carImg = RFGame.crashImg; }
		else if(!gameOver){
			if(enteringShop) { 
				if(carRightX<frmW/2+roadW/2+40){
					carX = frmW+10; //rendered out of sight
					carRightX++; 
				}else {
					carX = frmW/2+roadW/2+40; //car park offroad
					carY = shop.getY();
					enteringShop = false;
				}
			}
			if(leavingShop) { 
				if(carLeftX>frmW/2+roadW/2-30){
					carX = frmW+10; //rendered out of sight
					carLeftX--; 
				}else{
					carX = shop.getX()+15;	//car back on the road
					carY = shop.getY()-carH;
					leavingShop = false;
					RFGame.shopping = false; //very important!
				}
			}
			if(enteringMission){
				if(carLeftX>frmW/2-roadW/2-80){
					carX = frmW+10; //rendered out of sight
					carLeftX--; 
				}else {
					carX = frmW/2-roadW/2-80; //car park offroad
					carY = mission.getY();
					enteringMission = false;
				}
			}
			if(leavingMission) { 
				if(carRightX<frmW/2-roadW/2+15){
					carX = frmW+10; //rendered out of sight
					carRightX++; 
				}else{
					carX = mission.getX()+15;	//car back on the road
					carY = mission.getY()-carH;
					leavingMission = false;
					RFGame.missionShopping = false; //important for car to start, game running
				}
			}
		}
	}
	public void enterShop(){
		carRightX = frmW/2+roadW/2-30;
		carLeftX = frmW/2+roadW/2+40;
		enteringShop = true;
	}
	public void leaveShop(){ 
		leavingShop = true;
	}
	public void enterMission(){
		carLeftX = frmW/2-roadW/2-30;
		carRightX = frmW/2-roadW/2-80;
		enteringMission = true;
	}
	public void leaveMission(){ 
		leavingMission = true;
	}
	public void col(String dir, int type){ //nothing happens to your car when hits a bike
		if(dir.equals("right")){
			if(type==2){ carX-=(speed*2); damage(speed); }
			if(type==3||type==4){ carX-=(speed*2+10); damage(speed*2); }
			if(type==6) { carX-=(speed*2+10); damage(50); }
		}
		if(dir.equals("left")){
			if(type==2){carX+=speed*2; damage(speed); }
			if(type==3||type==4){carX+=speed*2+10; damage(speed*2); }
			if(type==6){carX+=speed*2+10; damage(50); }
		}
		if(dir.equals("rear")){
			if(type==2){carY-=(speed*2); damage(speed); }
			if(type==3||type==4){carY-=(speed*2+10); damage(speed*2); }
			if(type==6){carY-=(speed*2+10); damage(50); }
		}
		if(dir.equals("front")){
			if(type==2) { carY+=(speed*2); damage(speed*2); }
			if(type==3||type==4) { carY+=(speed*2+10); damage(speed*3); }//mo damage on front col
			if(type==6) { carY+=(speed*2+10); damage(100); }
			
		}
	}
	public void move(String dir){
		if(dir.equals("left")){ carX-=dX; }
		if(dir.equals("right")){ carX+=dX; }
		if(dir.equals("up")){ carY-=dY; }
		if(dir.equals("down")){ carY+=dY; }
	}
	public void withinBounds(){
		if(RFGame.isRunning()){
			if(carX<=frmW/2-roadW/2){ carX = frmW/2-roadW/2+20; //car bounces off curb
			damage(speed); }
			if(carX>=frmW/2+roadW/2-carW){ carX = frmW/2+roadW/2-carW-20; //car bounces off curb
			damage(speed); }
			if(carY<=0) { carY = 0; }
			if(carY>=frmH-carH) { carY = frmH-carH; }
		}
	}
	public void paint(Graphics g){
		g.drawImage(carImg, carX, carY, this);
		if(enteringShop){ g.drawImage(carRightImg, carRightX, shop.getY(), this); }
		if(leavingShop){ g.drawImage(carLeftImg, carLeftX, shop.getY(), this); }
		if(enteringMission){ g.drawImage(carLeftImg, carLeftX, mission.getY(), this); }
		if(leavingMission){ g.drawImage(carRightImg, carRightX, mission.getY(), this); }
	}
}
