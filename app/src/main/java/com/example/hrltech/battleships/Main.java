package com.example.hrltech.battleships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    private Player playerOne;
    private Player playerTwo;

    private boolean playerOnesTurn;
    private boolean placingBoatsPhase;

    private Button actionButton;
    private Button rightButton;
    private Button downButton;
    private Button upButton;
    private Button leftButton;
    private Button orientationButton;
    private Button switchPlayersButton;

    private TextView notificationTextView;
    private TextView firingBoardTextView;
    private TextView shipBoardTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boards_screen);
        playerOnesTurn = true;
        placingBoatsPhase = true;
        findViewsById();
        setOnActionListeners();
        playerOne = new Player();
        playerTwo = new Player();
        refreshPlayerBoards(playerOne);
        switchPlayersButton.setVisibility(View.INVISIBLE);
    }

    protected void setOnActionListeners() {
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnesTurn) {
                    playerOne.changeCurrentLocation('r');
                    refreshPlayerBoards(playerOne);
                }
                else {
                    playerTwo.changeCurrentLocation('r');
                    refreshPlayerBoards(playerTwo);
                }
            }
        });

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnesTurn) {
                    playerOne.changeCurrentLocation('l');
                    refreshPlayerBoards(playerOne);
                }
                else {
                    playerTwo.changeCurrentLocation('l');
                    refreshPlayerBoards(playerTwo);
                }
            }
        });

        downButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnesTurn) {
                    playerOne.changeCurrentLocation('d');
                    refreshPlayerBoards(playerOne);
                }
                else {
                    playerTwo.changeCurrentLocation('d');
                    refreshPlayerBoards(playerTwo);
                }
            }
        });

        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnesTurn) {
                    playerOne.changeCurrentLocation('u');
                    refreshPlayerBoards(playerOne);
                }
                else {
                    playerTwo.changeCurrentLocation('u');
                    refreshPlayerBoards(playerTwo);
                }
            }
        });

        actionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (placingBoatsPhase) {
                    if (playerOnesTurn) {
                        if (playerOne.setCurrentBoatAtCurrentLocation()) {
                            refreshPlayerBoards(playerOne);
                            playerOne.decreaseBoatCount();
                            if (playerOne.getCurrentBoatCount() == -1) {
                                refreshPlayerBoardsWithoutCursors(playerOne);
                                showSwitchPlayersButton();
                            }
                        }
                        else {
                            notificationTextView.setText(playerOne.getCurrentBoatInfo() + "\nNotification: Invalid placement of boat!");
                        }
                    }
                    else {
                        if (playerTwo.setCurrentBoatAtCurrentLocation()) {
                            refreshPlayerBoards(playerTwo);
                            playerTwo.decreaseBoatCount();
                            if (playerTwo.getCurrentBoatCount() == -1) {
                                refreshPlayerBoardsWithoutCursors(playerTwo);
                                placingBoatsPhase = false;
                                actionButton.setText("FIRE");
                                showSwitchPlayersButton();
                                orientationButton.setVisibility(View.INVISIBLE);
                            }
                        }
                        else {
                            notificationTextView.setText(playerTwo.getCurrentBoatInfo() + "\nNotification: Invalid placement of boat!");
                        }
                    }
                }
                else {
                    if (playerOnesTurn) {
                        int first = playerOne.getCurrentLocation().getFirst();
                        int second = playerOne.getCurrentLocation().getSecond();
                        if (!playerTwo.hasAlreadyBeenShot(first, second)) {
                            if (playerTwo.isHit(first, second)) {
                                playerOne.setFiringBoardValue(first, second, 'H');
                                playerTwo.setShipBoardValue(first, second, 'H');
                            } else {
                                playerOne.setFiringBoardValue(first, second, 'M');
                                playerTwo.setShipBoardValue(first, second, 'M');
                            }
                            refreshPlayerBoardsWithoutCursors(playerOne);
                            showSwitchPlayersButton();
                        }
                        else {
                            notificationTextView.setText("Notification: coordinates have already been fired at");
                        }
                    }
                    else {
                        int first = playerTwo.getCurrentLocation().getFirst();
                        int second = playerTwo.getCurrentLocation().getSecond();
                        if (!playerOne.hasAlreadyBeenShot(first, second)) {
                            if (playerOne.isHit(first, second)) {
                                playerTwo.setFiringBoardValue(first, second, 'H');
                                playerOne.setShipBoardValue(first, second, 'H');
                            } else {
                                playerTwo.setFiringBoardValue(first, second, 'M');
                                playerOne.setShipBoardValue(first, second, 'M');
                            }
                            refreshPlayerBoardsWithoutCursors(playerTwo);
                            showSwitchPlayersButton();
                        }
                        else {
                            notificationTextView.setText("Notification: coordinates have already been fired at");
                        }
                    }
                    if (!playerOne.hasHealth()) {
                        notificationTextView.setText("Notification: Player one wins!!!");
                    }
                    else if (!playerTwo.hasHealth()) {
                        notificationTextView.setText("Notification: Player two wins!!!");
                    }
                }
            }
        });

        orientationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnesTurn) {
                    playerOne.changeCurrentBoatOrientation();
                    refreshPlayerBoards(playerOne);
                }
                else {
                    playerTwo.changeCurrentBoatOrientation();
                    refreshPlayerBoards(playerTwo);
                }
            }
        });

        switchPlayersButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnesTurn) {
                    playerOnesTurn = false;
                    playerTwo.setCurrentLocation(0, 0);
                    refreshPlayerBoards(playerTwo);
                    switchPlayersButton.setVisibility(View.INVISIBLE);
                }
                else {
                    playerOnesTurn = true;
                    playerOne.setCurrentLocation(0, 0);
                    refreshPlayerBoards(playerOne);
                    switchPlayersButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void showSwitchPlayersButton() {
        firingBoardTextView.setText("");
        shipBoardTextView.setText("");
        switchPlayersButton.setVisibility(View.VISIBLE);
    }

    protected void findViewsById() {
        notificationTextView = (TextView) findViewById(R.id.notificationTextView);
        firingBoardTextView = (TextView) findViewById(R.id.firingBoardTextView);
        shipBoardTextView = (TextView) findViewById(R.id.shipBoardTextView);
        actionButton = (Button) findViewById(R.id.actionButton);
        rightButton = (Button) findViewById(R.id.rightButton);
        downButton = (Button) findViewById(R.id.downButton);
        upButton = (Button) findViewById(R.id.upButton);
        leftButton = (Button) findViewById(R.id.leftButton);
        orientationButton = (Button) findViewById(R.id.orientationButton);
        switchPlayersButton = (Button) findViewById(R.id.switchPlayersButton);
    }

    protected void refreshPlayerBoardsWithoutCursors(Player player) {
        BoardToString boardToString = new BoardToString(player);
        firingBoardTextView.setText(boardToString.boardToString(player.getFiringBoard(), false));
        shipBoardTextView.setText(boardToString.boardToString(player.getShipBoard(), false));
        notificationTextView.setText("Notification: switching players");
    }

    protected void refreshPlayerBoards(Player player) {
        BoardToString boardToString = new BoardToString(player);
        if (placingBoatsPhase) {
            notificationTextView.setText(player.getCurrentBoatInfo() + "\nNotification: ");
            firingBoardTextView.setText(boardToString.boardToString(player.getFiringBoard(), false));
            shipBoardTextView.setText(boardToString.boardToString(player.getShipBoard(), true));
        }
        else {
            notificationTextView.setText("Notification ");
            firingBoardTextView.setText(boardToString.boardToString(player.getFiringBoard(), true));
            shipBoardTextView.setText(boardToString.boardToString(player.getShipBoard(), false));
        }
    }
}