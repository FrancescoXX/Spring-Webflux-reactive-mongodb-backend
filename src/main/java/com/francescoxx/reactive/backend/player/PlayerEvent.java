package com.francescoxx.reactive.backend.player;


import java.util.Date;

public class PlayerEvent {

    private Player player;
    private Date date;

    public PlayerEvent(Player player, Date date) {
        this.player = player;
        this.date = date;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
