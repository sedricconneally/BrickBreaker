import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

public class Paddle extends Cylinder{
	private double x;
	private double y;
	private double z;
	
	public Paddle(int x, int y, int z, double radius) {
		super(40,radius);
		this.x = x;
		this.y = y;
		this.z = z;
		this.setMaterial(new PhongMaterial(Color.AQUA));
	}
	
	
	public void init() {
		this.setTranslateX(x);
		this.setTranslateY(y);
	}


	public void dragged(double mouseX, double mouseY) {
		System.out.println("x = " + x + " y = " + y + " mouseX = " + mouseX + " mouseY = " + mouseY);
        
		if(mouseX > -440 && mouseX < 430)this.setTranslateX(mouseX);
		
	}
}
