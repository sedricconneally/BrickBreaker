import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Ball extends Sphere{
	private double x;
	private double y;
	private double z;
	private double radius;
	
	public Ball(int x, int y, int z, double radius) {
		super(radius);
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setMaterial(new PhongMaterial(Color.RED));
	}
	
	public void Init(double x, double y) {
		this.setTranslateX(x);
		this.setTranslateY(y);
	}
	
	
}
