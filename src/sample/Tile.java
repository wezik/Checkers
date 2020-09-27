package sample;

import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece() {
        return piece!=null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public static double size;

    public Tile(boolean light, int x,int y) {
        double tileSize = (double)Main.height/ Checkers.boardSize;
        size = tileSize;
        setWidth(tileSize);
        setHeight(tileSize);

        relocate(x*tileSize,y*tileSize);

        setFill(light ? Color.valueOf("#D69F7E") : Color.valueOf("#774936"));
    }

}
