import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView extends Application {


	private final Group root = new Group();
	private Group shapes = new Group();
    private PerspectiveCamera camera;
    private final double sceneWidth = 1000;
    private final double sceneHeight = 1000;
    private final Rotate rotateX = new Rotate(50, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(1, Rotate.Y_AXIS);
    private Timeline timeLine;
    
    

    @Override
    public void start(Stage stage) {

        
        Ball ball = new Ball(50,-50,0,sceneWidth/40);
        ball.init();
        
        timeLine = new Timeline(new KeyFrame(Duration.millis(5), e -> {
        	double dx = 3;
        	double dy = 3;
        	
        	if(ball.getLayoutX() < -440 || ball.getLayoutX() > 430) {
    			//dx = ball.getDeltaX();
    			dx *= -1;
    			//ball.setDeltaX(dx);
    		}
    		if(ball.getLayoutY() < -440 || ball.getLayoutY() > 430) {
    			//dy = ball.getDeltaX();
    			dy *= -1;
    			//ball.setDeltaX(dy);
    		}
    		
    		ball.setLayoutX(ball.getLayoutX() + dx);
    		ball.setLayoutY(ball.getLayoutY() + dy);
    		System.out.println("x = " + ball.getLayoutX() + " y = " + ball.getLayoutY() + " dx: " + dx + " dy: " + dy);
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        
        Paddle paddle = new Paddle(400,400,0,sceneWidth/40);
        paddle.init();
        
    	shapes.getChildren().add(ball);
    	shapes.getChildren().add(paddle);
    	
        GameBoarder boarder = new GameBoarder(sceneWidth,sceneHeight,30);
        boarder.init();
        
        root.getChildren().add(boarder.getBoarder());
        root.getChildren().add(shapes);
        Scene scene = new Scene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.GREY);

        camera = new PerspectiveCamera(true);
        camera.setVerticalFieldOfView(false);
        camera.setNearClip(0.1);
        camera.setFarClip(100000.0);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -2500));
        scene.setCamera(camera);
        
        scene.setOnMousePressed(me -> {
            PickResult pr = me.getPickResult();
            if(pr!=null && pr.getIntersectedNode() instanceof Ball){
//            	ball.picked();
            	timeLine.play();
            }
        });
        
		scene.setOnMouseDragged(me -> {
			paddle.dragged(me.getX(),me.getY());
		});


        stage.setTitle("Brick Breaker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}