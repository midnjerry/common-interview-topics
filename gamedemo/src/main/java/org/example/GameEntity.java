package org.example;

import java.awt.*;

public class GameEntity {
    Point location;
    Direction direction;
    boolean isAlive;

    public GameEntity(int x, int y, Direction direction){
        location = new Point(x, y);
        this.direction = direction;
        isAlive = true;
    }

    public Point getForwardLocation(){
        int futureX = location.x;
        int futureY = location.y;
        switch(direction){
            case NORTH -> { futureY--;
            }
            case EAST -> { futureX++;
            }
            case SOUTH -> {  futureY++;
            }
            case WEST -> { futureX--;
            }
        }
        return new Point(futureX, futureY);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "location=" + location +
                ", direction=" + direction +
                ", isAlive=" + isAlive +
                '}';
    }
}
