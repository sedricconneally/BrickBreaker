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

        
        Ball ball = new Ball(0,100,0,sceneWidth/40);
        ball.init();
        Paddle paddle = new Paddle(0,300,0,sceneWidth/40);
        paddle.init();
        GameBoarder boarder = new GameBoarder(sceneWidth,sceneHeight,30);
        boarder.init();
    	shapes.getChildren().addAll(ball,paddle);
        shapes.getChildren().addAll(boarder.getBoarder().getChildren());
        root.getChildren().add(shapes);
        

        timeLine = new Timeline(new KeyFrame(Duration.millis(3), new EventHandler<ActionEvent>() {

            double dx = 2;
            double dy = 2;
            double vx =1;
            double vy =1;
            int o = 0;
            @Override
            public void handle(final ActionEvent t) {  		
        		ball.setLayoutX(ball.getLayoutX() + (dx*vy));
        		ball.setLayoutY(ball.getLayoutY() + dy*vx);
        		for (Node n : shapes.getChildren()) {
        			if(!(n instanceof Ball)) {
        				o++;
        				Bounds item = n.getBoundsInParent();
        				Bounds circ = ball.getBoundsInParent();
        				if(circ.intersects(item)) {
        					double th = +Math.atan((item.getCenterY()-circ.getCenterY())/(item.getCenterX()-circ.getCenterX()));
        					System.out.println("ball x:" + circ.getCenterX() +"  y= " + circ.getCenterY());
        					System.out.println("item x:" + item.getCenterX() +"  y= " + item.getCenterY());        					
        					double dh = circ.getHeight()/2 + item.getHeight()/2;
        					double xh = Math.abs(item.getCenterY()) -Math.abs(circ.getCenterY());
        					double dw = circ.getWidth()/2 + item.getWidth()/2;
        					double xw = Math.abs(item.getCenterX()) -Math.abs(circ.getCenterX());
        					if(dh%xh < 4) {
        						dy*= -1;
        						System.out.println("y fliped");
        					}
        					if(dw%xw < 4) {
        						dx*= -1;
        						System.out.println("x fliped");
        					}
        					System.out.println("dw = " +dw+ " xw = " +xw);
        					System.out.println("dh = " +dh+ " xh = " +xh);
        				}
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
       // camera.relocate(500, 500);
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