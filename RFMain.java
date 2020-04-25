import java.awt.EventQueue;


public class RFMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new RFGame();
			}
		});
	}
}
