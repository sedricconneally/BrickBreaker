import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Platform extends Box{
	private double x;
	private double y;
	private double z;
	
	public Platform(double x, double y, double z) {
		super(x,y,z);
		this.x = x;
		this.y = y;
		this.z = z;
		System.out.print(this.getTranslateX() + ", " + this.getTranslateY() + ", " + this.getTranslateZ());
		this.setMaterial(new PhongMaterial(Color.BLACK));
	}
	
	public void Init(double x, double y) {
		this.setTranslateX(x);
		this.setTranslateY(y);
	}
	
	this.
	
}