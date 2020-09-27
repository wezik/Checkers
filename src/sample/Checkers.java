package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Checkers {
    private PlayerType player;

    public Checkers(PlayerType player) {
        this.player = player;
    }

    private final Pane paneMain = new Pane();
    private final Pane paneBoard = new Pane();
    private final Pane paneSide = new Pane();
    private final HBox hBox = new HBox();

    public void start() {
        System.out.println(player);
        paneMain.setPrefSize(Main.width,Main.height);
        paneBoard.setPrefSize(Main.height,Main.height);
        createBoard();
        paneSide.setPrefSize(Main.width-Main.height-3,Main.height);
        createOptions();
        paneMain.getChildren().add(hBox);
        Pane hspacer = new Pane();
        hspacer.setPrefSize(3,Main.height);
        hspacer.setStyle("-fx-background-color: black");
        hBox.getChildren().addAll(paneBoard,hspacer,paneSide);
        Main.primaryStage.setScene(new Scene(paneMain,Main.width,Main.height));
    }

    private void createBoard() {
        createBoard(8);
    }

    private final Group tileGroup = new Group();
    private final Group pieceGroup = new Group();
    private Tile[][] board;
    public static int boardSize;

    private void createBoard(int size) {
        boardSize = size;
        paneBoard.getChildren().addAll(tileGroup, pieceGroup);
        board = new Tile[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y);
                } else if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }
                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        lastMove = board[1][0].getPiece();
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type,x,y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result = tryMove(piece,newX,newY);

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE -> piece.abortMove();
                case NORMAL -> {
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    swapTurn(piece);
                }
                case KILL -> {
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                }
            }
        });

        return piece;
    }

    private void swapTurn(Piece piece) {
        lastMove = piece;
    }

    private int toBoard(double pixel) {
        return (int) ((pixel+Tile.size/2)/Tile.size);
    }

    private Piece lastMove;

    private MoveResult tryMove(Piece piece, int newX, int newY) {

        if (newX<0||newY<0||newX>=board.length||newY>=board.length) return new MoveResult(MoveType.NONE);

        if (piece.getType() == lastMove.getType()) {
            return new MoveResult(MoveType.NONE);
        }

        if (board[newX][newY].hasPiece() || (newX+newY) %2 == 0) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().direction) {
            lastMove = piece;
            return new MoveResult(MoveType.NORMAL);
        } else if(Math.abs(newX - x0) == 2) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private void createOptions() {
        VBox vBox = new VBox();
        paneSide.setStyle("-fx-background-color: #D69F7E");
        int optionsWidth = Main.width-Main.height-3;
        vBox.setPrefSize(optionsWidth,Main.height);
        paneSide.getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
        Pane topPane = new Pane();
        int paneHeight = Main.height/3;
        topPane.setPrefSize(optionsWidth,paneHeight);
        Pane midPane = new Pane();
        midPane.setPrefSize(optionsWidth,paneHeight);
        Pane bottomPane = new Pane();
        bottomPane.setPrefSize(optionsWidth,paneHeight);
        vBox.getChildren().addAll(topPane,midPane,bottomPane);
        HBox topHBox = new HBox();
        HBox midHBox = new HBox();
        HBox bottomHBox = new HBox();
        topHBox.setPrefSize(optionsWidth,paneHeight);
        midHBox.setPrefSize(optionsWidth,paneHeight);
        bottomHBox.setPrefSize(optionsWidth,paneHeight);
        topPane.getChildren().add(topHBox);
        midPane.getChildren().add(midHBox);
        bottomPane.getChildren().add(bottomHBox);

        VBox topLeftVBox = new VBox();
        topHBox.getChildren().add(topLeftVBox);
        topHBox.setAlignment(Pos.TOP_LEFT);

        //Clock
        long startTime = System.currentTimeMillis();
        Label timerLabel = new Label();

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                long elapsedMillis = System.currentTimeMillis() - startTime;
                timerLabel.setText("Time elapsed: "+(Long.toString(elapsedMillis/1000)));
            }
        }.start();

        timerLabel.setStyle("-fx-text-fill: white");
        timerLabel.setFont(Font.font("Lato",15));

        //Versus label
        Label versusLabel = new Label();
        String playerName;
        if(player == PlayerType.HUMAN) playerName="2nd Player";
        else playerName="BOT";
        versusLabel.setText("Enemy: "+playerName);
        versusLabel.setFont(Font.font("Lato",15));
        versusLabel.setStyle("-fx-text-fill: white");

        topLeftVBox.getChildren().addAll(timerLabel,versusLabel);
        topLeftVBox.setSpacing(10);

        //Back to menu
        Button backToMenu = new Button();
        backToMenu.setStyle("-fx-background-color: transparent");
        backToMenu.setTextFill(Color.WHITE);
        backToMenu.setFont(Font.font("Lato",25));
        backToMenu.setText("Back to main menu!");
        backToMenu.setOnAction(e -> new MainMenu().run());
        backToMenu.setOnMouseEntered(e-> backToMenu.setTextFill(Color.BROWN));
        backToMenu.setOnMouseExited(e -> backToMenu.setTextFill(Color.WHITE));

        bottomHBox.getChildren().add(backToMenu);
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
    }
}
