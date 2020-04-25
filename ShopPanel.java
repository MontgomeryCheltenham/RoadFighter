import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ShopPanel extends JPanel {
	static int shopX, shopY, shopW, shopH;
	int frmW = RFGame.frmW; int frmH = RFGame.frmH;
	Image rightArrow, serviceStationImg, mechImg, gunsImg, drugGangImg,
		signMech, signFuel, signParking, carRightImg, carLeftImg;
	
	public ShopPanel(){
		shopW=60; shopH=30; //arrow dim
		shopX = frmW/2+RFGame.roadW/2-shopW;
		shopY = -((int)(Math.random()*10)+1)*100; //TESTING - 100 / game 1000
		rightArrow = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/rightarrow.png");
		serviceStationImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/service_station.png");
		mechImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/mechanic.png");
		gunsImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/gundealer.png");
		drugGangImg = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/druggang.png");
		signMech = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/sign_mech.png");
		signFuel = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/sign_fuel.png");
		signParking = Toolkit.getDefaultToolkit().getImage("C:/Users/Utilizador/Desktop/road fighter pix/sign_parking.png");
	}
	public void update(){
		if(RFGame.isRunning()){							//TESTING!! back to 1000
			if(shopY<=frmH){ shopY++; } else {shopY = -((int)(Math.random()*10)+1)*100; }
		}
		else if(RFGame.shopping){
			if((shopY>frmH-130)&&(shopY<frmH-110)) { shopY = frmH-120; }
			else if(shopY<frmH-120) { shopY+=4; }
			else if(shopY>frmH-120) { shopY-=4; }
		}
	}
	public int getX(){ return shopX; }
	public int getY(){ return shopY; }
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.drawImage(rightArrow, shopX, shopY, this);
		g.setColor(Color.BLACK);
		g.fillRect(frmW/2+RFGame.roadW/2, shopY-11, 40, 44); //side road
		g.fillRect(frmW/2+RFGame.roadW/2+40, shopY-11, 120, 120); //parking
		g.drawImage(signMech, frmW/2+RFGame.roadW/2+40, shopY-51, this);
		g.drawImage(signFuel, frmW/2+RFGame.roadW/2+80, shopY-51, this);
		g.drawImage(signParking, frmW/2+RFGame.roadW/2+120, shopY-51, this);
		
		int shopLeftBorder = frmW/2+RFGame.roadW/2+14;
		if(RFGame.serviceStation)/*||(leavingShop)*/{
			g.drawImage(serviceStationImg, shopLeftBorder, 20, this);
			g.setColor(Color.BLACK); g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString("Whatchya need bro?", shopLeftBorder, 110);
			g.drawString("1 - Mechanic", shopLeftBorder, 125);
			g.drawString("2 - Arms", shopLeftBorder, 140);
			g.drawString("3 - Drugs", shopLeftBorder, 155);
			g.drawString("0 - Hit the road!", shopLeftBorder, 170);
		}
		if(RFGame.mechanic){
			g.drawImage(mechImg, shopLeftBorder, 20, this);
			g.setColor(Color.BLACK); g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString("Whatchya need bro?", shopLeftBorder, 110);
			g.drawString("1 - Quick fix + 50 ($"+RFGame.mechPrice*50+")", shopLeftBorder, 125);
			/*fix max health*/g.drawString("2 - Top up to 500 ($"+RFGame.mechPrice+"/unit)", shopLeftBorder, 140);
			g.drawString("0 - Back to service station!", shopLeftBorder, 155);
		}
		if(RFGame.arms){
			g.drawImage(gunsImg, shopLeftBorder, 20, this);
			g.setColor(Color.BLACK); g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString("Whatchya need bro?", shopLeftBorder, 110);
			g.drawString("1 - rifle $500", shopLeftBorder, 125);
			g.drawString("2 - magnum $2000", shopLeftBorder, 140);
			g.drawString("0 - Back to service station!", shopLeftBorder, 155);
		}
		if(RFGame.drugs){
			g.drawImage(drugGangImg, shopLeftBorder, 20, this);
			g.setColor(Color.BLACK); g.setFont(new Font("Tahoma", Font.BOLD, 12));
			g.drawString("Sorry, bro!", shopLeftBorder, 110);
			g.drawString("We ain't deal with this", shopLeftBorder, 125);
			g.drawString("kind of shite here.", shopLeftBorder, 140);
			g.drawString("0 - Back to service station!", shopLeftBorder, 155);
		}
	}
}
