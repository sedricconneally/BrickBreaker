 import java.util.ArrayList;

import javafx.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class ThreeDSpace extends Application {

    private final Group root = new Group();
    private PerspectiveCamera camera;
    private final double sceneWidth = 800;
    private final double sceneHeight = 1000;
    private double side = 150;
    private boolean cC = true;
    private Color c = null;
	private ArrayList<Box> boxes = new ArrayList<>();
	private ArrayList<Sphere> sph = new ArrayList<>();

    private final Rotate rotateX = new Rotate(1, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(1, Rotate.Y_AXIS);
    
    

    @Override
    public void start(Stage stage) {
    	
    	
    	for(int y = 1; y <= 3; y++)
    	{
    		for(int x = 1; x <= 5; x++ )
    		{
    			if(cC) c = Color.WHITE;
    		    else			c = Color.BLACK;	
	    		rec(side, (x)*250, y*side, boxes, c);
		    	cC =! cC; 
    		}
	    	cC =! cC;  
    	}
    	
    	
    	
        Sphere sphere = new Sphere(side/3);
        sphere.setMaterial(new PhongMaterial(Color.RED));
        sphere.setTranslateX(500);
        sphere.setTranslateY(500);
       // sphere.setTranslateZ(-55);
        sph.add(sphere);
    	
    	Group shapes = new Group();
    	shapes.getChildren().addAll(boxes);
    	shapes.getChildren().addAll(sph);
    	shapes.setTranslateX(-150);
    	shapes.setTranslateY(-150);
        root.getChildren().add(shapes);

         
        
        Scene scene = new Scene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.GRAY);

        camera = new PerspectiveCamera(true);
        camera.setVerticalFieldOfView(false);
        camera.setNearClip(0.1);
        camera.setFarClip(100000.0);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -3000));

        PointLight light = new PointLight(Color.ANTIQUEWHITE);
        root.getChildren().add(light);
        root.getChildren().add(new AmbientLight(Color.WHITE));
        scene.setCamera(camera);


        stage.setTitle("Brick Breaker");
        stage.setScene(scene);
        stage.show();
    }
    
	private void rec(double side, double x, double y, ArrayList<Box> boxes, Color color)
	{
	//	System.out.println("x is: " + x + "  y is: " + y + " size is: " + side);
		Box box = new Box(side/.9, side/2, side/2);
		box.setTranslateX(x-600);
		box.setTranslateY(y-800);
		//box.translateZProperty().set(side);
		box.setMaterial(new PhongMaterial(Color.GREEN));
	    boxes.add(box);
	
//    Sphere sphere = new Sphere(side/3);
//    sphere.setMaterial(new PhongMaterial(Color.RED));
//    sphere.setTranslateX(x-530);
//    sphere.setTranslateY(y-530);
//    sphere.setTranslateZ(-55);
//    sph.add(sphere);
	
	}

    public static void main(String[] args) {
        launch(args);
    }
}