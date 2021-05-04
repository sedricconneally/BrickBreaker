 import java.util.ArrayList;

import javafx.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
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

public class ThreeDSpaceB extends Application {

    private final Group root = new Group();
    private PerspectiveCamera camera;
    private final double sceneWidth = 800;
    private final double sceneHeight = 600;
    private double side = 150;
    private boolean cC = true;
    private Color c = null;
	private ArrayList<Rectangle> rec = new ArrayList<>();
	private ArrayList<Sphere> sph = new ArrayList<>();
	
    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private final Rotate rotateX = new Rotate(75, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(-1, Rotate.Y_AXIS);

    private volatile boolean isPicking=false;
    private Point3D vecIni, vecPos;
    private double distance;
    private Sphere s;
    
    

    @Override
    public void start(Stage stage) {
    	
    	
    	for(int y = 1; y <= 8; y++)
    	{
    		for(int x = 1; x <= 8; x++ )
    		{
    			if(cC) c = Color.WHITE;
    		    else			c = Color.BLACK;	
	    		rec(side, (x)*side, y*side, rec, c);
		    	cC =! cC; 
    		}
	    	cC =! cC;  
    	}
    	Group bb = new Group();
    	bb.getChildren().addAll(rec);
    	bb.getChildren().addAll(sph);
    	bb.setTranslateX(-150);
    	bb.setTranslateY(-150);
    	//bb.setTranslateZ(250);
        Box floor = new Box(1500, 50, 1500);
        floor.setMaterial(new PhongMaterial(Color.GRAY));
        floor.setTranslateY(150);
        root.getChildren().add(bb);

         
        
        Scene scene = new Scene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.web("3d3d3d"));

        camera = new PerspectiveCamera(true);
        camera.setVerticalFieldOfView(false);

        camera.setNearClip(0.1);
        camera.setFarClip(100000.0);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -3000));

        PointLight light = new PointLight(Color.GAINSBORO);
        root.getChildren().add(light);
        root.getChildren().add(new AmbientLight(Color.WHITE));
        scene.setCamera(camera);

        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            PickResult pr = me.getPickResult();
            if(pr!=null && pr.getIntersectedNode() != null && pr.getIntersectedNode() instanceof Sphere){
                distance=pr.getIntersectedDistance();
                s = (Sphere) pr.getIntersectedNode();
                isPicking=true;
                vecIni = unProjectDirection(mousePosX, mousePosY, scene.getWidth(),scene.getHeight());
            }
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            if(isPicking){
                vecPos = unProjectDirection(mousePosX, mousePosY, scene.getWidth(),scene.getHeight());
                Point3D p=vecPos.subtract(vecIni).multiply(distance);
                s.getTransforms().add(new Translate(p.getX(),p.getY(),p.getZ()));
                vecIni=vecPos;
                PickResult pr = me.getPickResult();
                if(pr!=null && pr.getIntersectedNode() != null && pr.getIntersectedNode()==s){
                    distance=pr.getIntersectedDistance();
                } else {
                    isPicking=false;
                }
            } else {
                rotateX.setAngle(rotateX.getAngle()-(mousePosY - mouseOldY));
                rotateY.setAngle(rotateY.getAngle()+(mousePosX - mouseOldX));
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
            }
        });
        scene.setOnMouseReleased((MouseEvent me)->{
            if(isPicking){
                isPicking=false;
            }
        });

        stage.setTitle("3D Dragging");
        stage.setScene(scene);
        stage.show();
    }
    
	private void rec(double side, double x, double y, ArrayList<Rectangle> rec, Paint color)
	{
	//	System.out.println("x is: " + x + "  y is: " + y + " size is: " + side);
	Rectangle r = new Rectangle();
	r.setX(x-600);
	r.setY(y-600);
	r.setHeight(side);
	r.setWidth(side);
	r.setFill(color);
	rec.add(r);
	
    Sphere sphere = new Sphere(side/3);
    sphere.setMaterial(new PhongMaterial(Color.RED));
    sphere.setTranslateX(x-545);
    sphere.setTranslateY(y-525);
    sphere.setTranslateZ(-55);
    sph.add(sphere);
	
	}

    public Point3D unProjectDirection(double sceneX, double sceneY, double sWidth, double sHeight) {
        double tanHFov = Math.tan(Math.toRadians(camera.getFieldOfView()) * 0.5f);
        Point3D vMouse = new Point3D(tanHFov*(2*sceneX/sWidth-1), tanHFov*(2*sceneY/sWidth-sHeight/sWidth), 1);

        Point3D result = localToSceneDirection(vMouse);
        return result.normalize();
    }

    public Point3D localToScene(Point3D pt) {
        Point3D res = camera.localToParentTransformProperty().get().transform(pt);
        if (camera.getParent() != null) {
            res = camera.getParent().localToSceneTransformProperty().get().transform(res);
        }
        return res;
    }

    public Point3D localToSceneDirection(Point3D dir) {
        Point3D res = localToScene(dir);
        return res.subtract(localToScene(new Point3D(0, 0, 0)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}