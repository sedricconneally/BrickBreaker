import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
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
    private final Rotate rotateX = new Rotate(1, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(1, Rotate.Y_AXIS);
    private Timeline timeLine;
    
    

    @Override
    public void start(Stage stage) {

        
        Ball ball = new Ball(500,500,0,sceneWidth/40);
        ball.init();
        Paddle paddle = new Paddle(500,900,0,sceneWidth/40);
        paddle.init();
        GameBoarder boarder = new GameBoarder(sceneWidth,sceneHeight,30);
        boarder.init();
    	shapes.getChildren().addAll(ball,paddle);
        shapes.getChildren().addAll(boarder.getBoarder().getChildren());
        root.getChildren().add(shapes);
        

        timeLine = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

            double dx = 3;
            double dy = 3;
            int o = 0;
            @Override
            public void handle(final ActionEvent t) {
//            	if(ball.getLayoutX() < -440 || ball.getLayoutX() > 430) dx *= -1;
//        		if(ball.getLayoutY() < -440 || ball.getLayoutY() > 430) dy *= -1;
//        		
        		ball.setLayoutX(ball.getLayoutX() + dx);
        		ball.setLayoutY(ball.getLayoutY() + dy);
        		for (Node n : shapes.getChildren()) {
        			
        			//System.out.println(n + ": " + n.getBoundsInParent());
        			//System.out.println(n + ": " + n.getBoundsInLocal());
        			if(!(n instanceof Ball)) {
        				Bounds item = n.getBoundsInParent();
        				Bounds circ = ball.getBoundsInParent();
        				if(circ.intersects(item)) {
        					System.out.println("Collision between ball and item");
        					if(ball.getLayoutX() <= n.getLayoutX() || ball.getLayoutX() > n.getLayoutX()) {
        						dx *= -1;
        					}
        					
        					if(ball.getLayoutY() <= n.getLayoutY() || ball.getLayoutY() > n.getLayoutY()) {
        						dy *= -1;
        					}
        				}
        				
        				
        				
//        				o++;
//    					//System.out.println("ball " +o+ ball.localToScreen(n.getBoundsInLocal()));
//        				if(ball.localToScreen(n.getBoundsInLocal()).intersects(n.localToScreen(n.getBoundsInLocal()))) {
//        					if(ball.getLayoutX() <= n.getLayoutX() || ball.getLayoutX() > n.getLayoutX()) {
//        						
//        						dx *= -1;
//        					}
//        					if(ball.getLayoutY() <= n.getLayoutY() || ball.getLayoutY() > n.getLayoutY()) {
//        						
//        						dy *= -1;       					
//        					}
//        					
//        					//System.out.println(n + " " +n.localToScreen(n.getBoundsInLocal()));
//        					System.out.println("o"+"ball " + ball.localToScreen(n.getBoundsInLocal()));
//        				}
        			}
        		}
            }
        }));

        timeLine.setCycleCount(Timeline.INDEFINITE);

        Scene scene = new Scene(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.GREY);

        camera = new PerspectiveCamera(true);
        camera.setVerticalFieldOfView(false);
        camera.setNearClip(0.1);
        camera.setFarClip(100000.0);
        camera.getTransforms().addAll (rotateX, rotateY, new Translate(0, 0, -2500));
        camera.relocate(500, 500);
        scene.setCamera(camera);
        
        scene.setOnMousePressed(me -> {
            PickResult pr = me.getPickResult();
            if(pr!=null && pr.getIntersectedNode() instanceof Ball){
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