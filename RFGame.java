import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class RFGame {
    JFrame frm = new JFrame("Road Fighter");
	JPanel pnl = new Panel(); 
	ShopPanel shop = new ShopPanel(); MissionPanel mission = new MissionPanel();
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	Graphics g;
	static Image carImg, crashImg, smallCrashImg;
	static int W = dim.width; static int H = dim.height;
	static int frmW, frmH, roadW, mechPrice; 
	private int lineW, lineH, lineX, lineY; //road marks
	private int buX, buY; 	private int timeCounter;
	
	private boolean addOth, addShop, addMission, helpPanel; 
	static boolean shopping, serviceStation, mechanic, arms, drugs,
		missionShopping, onMission, mission1, mission2, mission2ret,
		mission1setup, mission2setup, mission1comp, mission2comp, mission2retcomp;
	static boolean rifle, magnum, onCoke, shotFired, drawOthBullet, drawBullet, bulletBlowUp, othBulletBlowUp;
	public static boolean score;
	
	public YourCar car;
	public Others other1 = new Others(1); public Others other2 = new Others(2);
	public Others other1_1 = new Others(1); public Others other2_1 = new Others(2); //game beginning setup 2 bikes, 2 cyan cars
	public Others other3 = new Others(3); public Others other4 = new Others(4);
	public Others other5 = new Others(5); public Others other6 = new Others(6); // assigned acc. to types
	public Others other5_1 = new Others(5); public Others other5_2 = new Others(5); public Others other5_3 = new Others(5);
	public ArrayList<Others> others; 
	public ArrayList<Bullets> bullets;
	
	public RFGame(){
		@SuppressWarnings("static-access")
		InputMap im = pnl.getInputMap(pnl.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "X");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "Z");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), "Zero");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "One");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "Two");
	    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "Three");
	    ActionMap am = pnl.getActionMap();
	    am.put("LeftArrow", new KeyPressedAction("LeftArrow"));
	    am.put("RightArrow", new KeyPressedAction("RightArrow"));
	    am.put("UpArrow", new KeyPressedAction("UpArrow"));
	    am.put("DownArrow", new KeyPressedAction("DownArrow"));
	    am.put("X", new KeyPressedAction("X"));
	    am.put("Z", new KeyPressedAction("Z"));
	    am.put("A", new KeyPressedAction("A"));
	    am.put("F1", new KeyPressedAction("F1"));
	    am.put("Zero", new KeyPressedAction("Zero"));
	    am.put("One", new KeyPressedAction("One"));
	    am.put("Two", new KeyPressedAction("Two"));
	    am.put("Three", new KeyPressedAction("Three"));
	    
	    car = new YourCar();
	    others = new ArrayList<Others>();
	    others.add(other2); others.add(other2_1); //adding cars n bikes
	    others.add(other1); others.add(other1_1);
	    others.add(other3); others.add(other4);	 
	    bullets = new ArrayList<Bullets>();
		
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setLocation(W/8, H/8);
		frm.add(pnl);
		frm.setResizable(false); frm.setFocusable(true); frm.requestFocus();
		frm.pack();
		frm.setVisible(true);
	}
	@SuppressWarnings("serial")
	public class Panel extends JPanel {
		public Panel(){
			frmW = 600; frmH = 500; roadW = 240;
			setPreferredSize(new Dimension(frmW, frmH));
			carImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/car.png");
			crashImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/crash.png");
			smallCrashImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/crash_small.png");
			mechPrice = 2;
			lineW = 6; lineH = 30; lineX = frmW/2-lineW/2; lineY=0;
			buX = frmW; buY = frmH; // your bullets out of sight/reach
			helpPanel = true; 
			//magnum = true; //fi test
			
			Timer timer = new Timer(10, new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(isRunning()){
						lineY = lineY+car.getSpeed();
						timeCounter++; 
						if(Math.floorMod(timeCounter, 50)==0){
							othShoot();
						}
					}
					car.update();
					if(car.isGameOver()){ pnl.setEnabled(false); }
					addOth = true; addShop = true; addMission = true;
					shotReady(); overlap();
					collision(); enterShop(); enterMission();
					onMission();
					repaint();
					if(timeCounter==500){ helpPanel = false; }
					//if(timeCounter==3000){ addMission = true; }
					if(timeCounter>10000&&mission2comp){ mission2ret = true;}
					else if(Math.floorMod(timeCounter-10000, 2000)==0 &&mission2comp){mission2ret = true;}
				}
			});
			timer.start();
		}
		public void addPts(int value){
			if(score){ 
				car.addPoints(value);
			score = false; } //oth blows up, add pts only once #michelle of the resistance
		}
		public void overlap(){ //FIX mek it real
			for(int i = 1; i<others.size(); i++){
		    	int otherX = others.get(i).getX(); int otherY = others.get(i).getY(); 
		    	int otherX_1 = others.get(i-1).getX(); int otherY_1 = others.get(i-1).getY();
		    	if(((otherX_1<=otherX+others.get(i).getW())&&(otherX_1+others.get(i-1).getW()>=otherX))
						&&((otherY_1+others.get(i-1).getH()>=otherY)&&(otherY_1<=otherY+others.get(i).getH()))){
		    		others.get(i).setY(others.get(i).getY()+10); others.get(i).withinBounds();
		    	}
		    }
		}
		public void collision(){ //col w bikes n cars
			int carX = car.getX(); int carY = car.getY();
			int carW = 40; int carH =  60;
			for(int i = 0; i<others.size(); i++){
				int otherX = others.get(i).getX(); 
				int otherY = others.get(i).getY(); int otherW=others.get(i).getW(); int otherH=others.get(i).getH(); 
				if((others.get(i).getHealth()>0)&&isRunning()){ //no col with blowup or when shopping
					if(((carX<=otherX+otherW)&&(carX+carW>=otherX))
							&&((carY+carH>=otherY)&&(carY<=otherY+otherH))){
							
						if((carX<=otherX+otherW)&&(carX>=otherX+otherW/2)){ 
							others.get(i).col("right", car.getSpeed()); car.col("left", others.get(i).getType());  }
						if((carX+carW>=otherX)&&(carX+carW/2<=otherX)){ 
							others.get(i).col("left", car.getSpeed()); car.col("right", others.get(i).getType());  }
						if((carY<=otherY+otherH)&&(carY>=otherY+otherH/3)){ 
							others.get(i).col("rear", car.getSpeed()); car.col("front", others.get(i).getType());  }
						if((carY+carH>=otherY)&&((carY+carH/3<=otherY))){ 
							others.get(i).col("front", car.getSpeed()); car.col("rear", others.get(i).getType());  }
					} //col bullet-other
					if(((buX<=otherX+otherW)&&(buX>=otherX))&&((buY<=otherY+otherH)&&(buY>=otherY))&&(shotFired)){
							others.get(i).othHitBullet(); 
							drawBullet = false; shotFired = false;
							if(others.get(i).isDriving()){ 
								bulletBlowUp = true;
								Thread localThread = new Thread(new Runnable(){
									public void run(){ 
										try { Thread.sleep(50); bulletBlowUp = false; }
										catch (InterruptedException e) { e.printStackTrace(); }
									}
								});
								localThread.start(); 
							}
					} //col bullet - your car
					for(int j=0; j<bullets.size(); j++){
						int othBuX = bullets.get(j).getX(); int othBuY = bullets.get(j).getY();
						if(((othBuX<=carX+carW)&&(othBuX>=carX))&&((othBuY<=carY+carH)&&(othBuY>=carY))){ 
							if(others.get(i).isShooting()){
								others.get(i).setHitYourCar(true);
								car.damage(others.get(i).getGunPower());
								bullets.get(j).setX(frmW); bullets.get(j).setY(0); //fi bullet invisible
							}
							if(!car.isGameOver()){ 
								othBulletBlowUp = true;
								Thread localThread = new Thread(new Runnable(){
									public void run(){ 
										try { Thread.sleep(100); othBulletBlowUp = false; 
										Thread.sleep(500); }
										catch (InterruptedException e) { e.printStackTrace(); }
									}
								}); localThread.start(); 
							}
						}
					}
				}addPts(others.get(i).getValue());
			}
		}
		public void enterShop(){ //col car-shop
			int carX = car.getX(); int carY = car.getY();  int carW = 40;
			int shopX = shop.getX(); int shopY = shop.getY();
			if(((carX>shopX)&&(carX+carW<shopX+shop.shopW))&&(carY<shopY)&&(carY>shopY*10/11)){
				shopping = true; car.enterShop();
				serviceStation = true;
			}
		}
		public void enterMission(){ //col car-mission
			int carX = car.getX(); int carY = car.getY();  int carW = 40;
			int missionX = mission.getX(); int missionY = mission.getY();
			if(((carX>missionX)&&(carX+carW<missionX+mission.missionW))&&(carY<missionY)&&(carY>missionY*10/11)
					&&(!onMission)){
				missionShopping = true;
				if(!mission1comp){mission1setup=true;}else{mission2setup=true;}
				car.enterMission(); 
			}
		}
		public void onMission(){
			if(mission1){ others.set(2, other5); onMission = true;}
			if(mission1comp) { mission1 = false; onMission = false; others.set(2, other1); }
			if(mission2){ others.set(5, other6); onMission = true; }
			if(mission2comp) { mission2 = false; onMission = false; others.set(5, other4); }
			if(mission2ret){ others.set(2, other5); others.set(3, other5_1); others.set(4, other5_2); 
				others.set(5, other5_3); onMission = true; }
		}
		public void shotFired(){
			buY-=5*car.getSpeed(); 
			if(buY<0){buX=frmW; buY=frmH; drawBullet=false; shotFired=false;}
		}
		public void shotReady(){
			if(shotFired) { drawBullet=true; shotFired();}
		}
		public void othShoot(){
	//reload them guns
			
			for(int i=0; i<others.size(); i++){
				if(others.get(i).isShooting()){
					bullets.add(new Bullets(others.get(i).getX()+18, others.get(i).getY()+45, 1));
					for(int j=0; j<bullets.size(); j++){
						drawOthBullet = true; 
						if(bullets.get(j).getY()>frmH||others.get(i).getHitYourCar()){
							bullets.remove(j); 
						}
					}
				}
			}
		}
	    
		public void paint(Graphics g){
			super.paintComponent(g);
			g.setColor(new Color(200, 160, 80)); //background
			g.fillRect(0,0, frmW, frmH);
			g.setColor(Color.BLACK); //road
			g.fillRect(frmW/2 - roadW/2, 0, roadW, frmH);
			for(int i = 0; i<10; i++){
				if(lineY>frmH-lineH){ lineY = 0; }
				g.setColor(Color.WHITE);
				g.fillRect(lineX, lineY+i*60, lineW, lineH);
			}
			for(int i = 0; i<10; i++){
				if(lineY>frmH-lineH){ lineY = 0; }
				g.setColor(Color.WHITE);
				g.fillRect(lineX, lineY-i*60, lineW, lineH);
			}
			if(addShop){
				shop.paint(g); shop.update();
			}
			if(addMission){
				mission.paint(g); mission.update();
			}
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0,0, frmW/2 - roadW/2, 64);
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			if(car.getHealth()<100){g.setColor(new Color(255, 180, 180)); } else {g.setColor(Color.LIGHT_GRAY);}
			g.drawString("Health: " + car.getHealth(), 10, 20);
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Speed: " + car.getSpeed(), 10, 36);
			g.setColor(Color.BLUE);
			g.drawString("Points: " + car.getPoints() + " $", 10, 52);
			car.paint(g);
			
			if(helpPanel){
				g.setColor(Color.DARK_GRAY);
				g.fillRect(frmW/2+roadW/2, 0, frmW-(frmW/2+roadW/2), 64);
				g.setColor(Color.GREEN);
				g.drawString("X - speed up", frmW/2+roadW/2+10, 20);
				g.drawString("Z - slow down", frmW/2+roadW/2+10, 36);
				g.drawString("A - shoot (when armed)", frmW/2+roadW/2+10, 52);
			}
			if(addOth){
				for(int i=0; i<others.size(); i++){
					others.get(i).paint(g);
					others.get(i).update();
				}
			}
			if(drawBullet){
				if(rifle){ g.setColor(Color.GRAY); g.fillOval(buX, buY, 4, 6); }
				if(magnum){ g.setColor(Color.YELLOW); g.fillOval(buX, buY, 4, 6); }
			}
			if(drawOthBullet){
				for(int i=0; i<bullets.size(); i++){
					bullets.get(i).paint(g);
					bullets.get(i).update();
				}
			}
			if(bulletBlowUp){
				g.drawImage(smallCrashImg, buX, buY, this);
			}
			if(othBulletBlowUp){ 
				g.drawImage(smallCrashImg, car.getX()+car.carW/2, car.getY(), this);	
			}
			
			if(car.isGameOver()){
				g.setColor(Color.RED);
				g.drawString(" G A M E   O V E R ", frmW/2-50, frmH/3);
			}
		}
	}
	public static boolean isRunning() {if(shopping||missionShopping){
		return false;
	} else return true;}
	
	public static int gunPower(){
		if(rifle){ return 10; }
		else if(magnum){ return 50; }
		else { return 0; }
	}
	
	@SuppressWarnings("serial")
	public class KeyPressedAction extends AbstractAction{
		private String cmd;
		public KeyPressedAction(String cmd){ this.cmd = cmd; }		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(isRunning()){
				if(cmd.equals("LeftArrow")){
					car.move("left");
				} else if (cmd.equals("RightArrow")){
					car.move("right");
				} else if (cmd.equals("UpArrow")){
					car.move("up");
				} else if (cmd.equals("DownArrow")){
					car.move("down");
				} 
				if(cmd.equals("X")){
					if(car.getSpeed()<=car.getTopSpeed()) { car.speedUp(); }
				}
				if(cmd.equals("Z")){
					if(car.getSpeed()>1) { car.slowDown(); }
				}
				if(cmd.equals("A")){ int carW = 40; //cant fire when bullet in air, cant feel at high speed
					if(((rifle)||(magnum))&&(!drawBullet)) { buX = car.getX()+carW/2; buY = car.getY()+5; shotFired = true; }
				}
				if(cmd.equals("F1")){ //fix - mek elegant
					helpPanel = true;
					Thread localThread = new Thread(new Runnable(){
						public void run(){ 
							try { Thread.sleep(2500); helpPanel = !helpPanel;}
							catch (InterruptedException e) { e.printStackTrace(); }
						}
					}); localThread.start();
				}
			}else if(shopping){ 
				int health = car.getHealth(); int maxHealth = car.getMaxHealth();
				int points = car.getPoints();
				if(serviceStation){
					if(cmd.equals("One")){ mechanic = true; serviceStation = false; }
					if(cmd.equals("Two")){ arms = true; serviceStation = false; }
					if(cmd.equals("Three")){ drugs = true; serviceStation = false; }
					if(cmd.equals("Zero")){
						serviceStation = false; mechanic = false;
						arms = false; drugs = false;
						car.leaveShop();
					}
				}else if(mechanic){
					serviceStation = false; arms = false; drugs = false;
					if(cmd.equals("One")){ 
						if((health<=maxHealth-50)&&(points>=mechPrice*50)){
							car.damage(-50);
							car.addPoints(-50*mechPrice); }
					}
					if(cmd.equals("Two")){
						int diff = maxHealth - health;
						if((health<=maxHealth)&&(points>=diff*mechPrice)){
							car.damage(-diff);
							car.addPoints(-diff*mechPrice); }
					}
					if(cmd.equals("Zero")){
						mechanic = false; serviceStation = true;
					}
				}else if(arms){
					int priceRifle = 500; int priceMagnum = 2000;
					serviceStation = false;
					if(cmd.equals("One")){ 
						if((points>=priceRifle)&&(!rifle)&&(!magnum)){
							car.addPoints(-priceRifle);
							rifle = true;
						}
					}
					if(cmd.equals("Two")){ 
						if((points>=priceMagnum)&&(!magnum)){
							car.addPoints(-priceMagnum);
							rifle = false; magnum = true;
						}
					}
					if(cmd.equals("Zero")){
						arms = false; serviceStation = true;
					}
				}else if(drugs){
					serviceStation = false;
					if(cmd.equals("One")){  }
					if(cmd.equals("Zero")){
						drugs = false; serviceStation = true;
					}
				}
			} else if(missionShopping){
				if(mission1setup){
					if(cmd.equals("One")){ 
						mission1 = true; mission1setup = false; car.leaveMission();
					}
				}else if(mission2setup){
					if(cmd.equals("One")){ 
						mission2 = true; mission2setup = false; car.leaveMission();
					}
				}
				if(cmd.equals("Zero")){ mission1setup = false; mission2setup = false; 
					car.leaveMission(); }
			}
		}
	}
}
