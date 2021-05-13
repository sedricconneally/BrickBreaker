import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Bricks {
	private Group bricks;
	
	
	public Bricks() {
		bricks = new Group();
		
	}
	
	public void init() {
	for(double y = 0; y < 3; y++) {
			for(double x = 0; x < 9; x++ ) {
	    		createBrick(x, y,0.0);
			}
		}
	}
	
	private void createBrick(double x, double y, double z) {
		Box brick = new Box(100, 40, 20);
		brick.relocate(x-460+(100*x),y-440+(40*y));
//		brick.setTranslateX(x-500);
//		brick.setTranslateY(y-800);
		brick.setMaterial(new PhongMaterial(Color.GREEN));
	    bricks.getChildren().add(brick);
	}
	
	public Group getBricks() {
		return bricks;
	}
}