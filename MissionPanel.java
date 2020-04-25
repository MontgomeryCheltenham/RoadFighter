import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MissionPanel extends JPanel {
	static int missionX, missionY, missionW, missionH;
	int frmW = RFGame.frmW; int frmH = RFGame.frmH; int roadW = RFGame.roadW;
	Image leftArrow, bikerMissionImg, driverMissionImg, mission2retImg;
	
	public MissionPanel(){
		missionW=60; missionH=30; //arrow dim
		missionX = frmW/2-roadW/2;
		missionY = -((int)(Math.random()*10)+1)*100; //TESTING - 100 / game 1000
		leftArrow = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/leftArrow.png");
		bikerMissionImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/bikerMission.png");
		driverMissionImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/driver2.png");
		mission2retImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/bikers.png");
	}
	public void update(){
		if(RFGame.isRunning()){							//TESTING!! set to 1000
			if(missionY<=frmH){ missionY++; } else {missionY = -((int)(Math.random()*10)+1)*100; }
		}
		else if(RFGame.missionShopping){
			if(missionY>frmH-130&&missionY<frmH-110) { missionY = frmH-120; }
			else if(missionY<frmH-120) { missionY+=4; }
			else if (missionY>frmH-120) { missionY-=4; }
		}
	}
	public int getX(){ return missionX; }
	public int getY(){ return missionY; }
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.drawImage(leftArrow, missionX, missionY, this);
		g.setColor(Color.BLACK);
		g.fillRect(frmW/2-roadW/2-40, missionY-11, 40, 44); //side road
		g.fillRect(frmW/2-roadW/2-160, missionY-11, 120, 120); //parking
		
		int missionLeftBorder = 10;
		if(RFGame.mission1setup){
			g.drawImage(bikerMissionImg, missionLeftBorder, 64, this);
			g.setColor(Color.BLACK); g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString("Yo, partner!", missionLeftBorder, 145);
			g.drawString("There's this punk ass biker.", missionLeftBorder, 160);
			g.drawString("$2,000 if you off his ass.", missionLeftBorder, 175);
			g.drawString("1 - Accept Mission", missionLeftBorder, 190);
			g.drawString("0 - Hit the Road!", missionLeftBorder, 205);
		}
		if(RFGame.mission2setup){
			g.drawImage(driverMissionImg, missionLeftBorder, 64, this);
			g.setColor(Color.BLACK); g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString("Yo, partner!", missionLeftBorder, 145);
			g.drawString("This mofo needs to go down.", missionLeftBorder, 160);
			g.drawString("$2,000 if you off his ass.", missionLeftBorder, 175);
			g.drawString("1 - Accept Mission", missionLeftBorder, 190);
			g.drawString("0 - Hit the Road!", missionLeftBorder, 205);
		}
		if(RFGame.mission2ret){
			g.setColor(Color.DARK_GRAY);
			g.fillRect(frmW/2+RFGame.roadW/2+12, 12, 148, 90);
			g.drawImage(mission2retImg, frmW/2+RFGame.roadW/2+16, 14, this);
			g.setColor(Color.GREEN); g.setFont(new Font("Tahome", Font.BOLD, 14));
			g.drawString("R E T A L I A T I O N !", frmW/2+RFGame.roadW/2+16, 96);
		}
	}
}
