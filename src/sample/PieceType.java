package sample;

public enum PieceType {
    RED(1),
    WHITE(-1);
    int direction;

    PieceType(int direction) {
        this.direction = direction;
    }
}
