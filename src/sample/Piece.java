package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {
    private PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public PieceType getType() {
        return type;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(Tile.size*0.3125, Tile.size * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(Tile.size*0.03);

        bg.setTranslateX((Tile.size - Tile.size * 0.3125 * 2)/2);
        bg.setTranslateY((Tile.size - Tile.size * 0.26 * 2)/2 + Tile.size * 0.07);

        Ellipse ellipse = new Ellipse(Tile.size*0.3125, Tile.size * 0.26);
        ellipse.setFill(type == PieceType.RED ? Color.valueOf("#9D6B53") : Color.valueOf("#EDC4B3"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(Tile.size*0.03);

        ellipse.setTranslateX((Tile.size - Tile.size * 0.3125 * 2)/2);
        ellipse.setTranslateY((Tile.size - Tile.size * 0.26 * 2)/2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }
    public void move(int x, int y) {
        oldX = x * Tile.size;
        oldY = y * Tile.size;
        relocate(oldX,oldY);
    }

    public void abortMove() {
        relocate(oldX,oldY);
    }
}
