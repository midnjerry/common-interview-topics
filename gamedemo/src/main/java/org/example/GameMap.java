package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.Tile.FLOOR;
import static org.example.Tile.WALL;

public class GameMap {
    List<List<Tile>> tileMap = new ArrayList<>();
    int width;
    int height;

    public GameMap() {
        tileMap = List.of(
                List.of(WALL, WALL, WALL, WALL, WALL),
                List.of(WALL, FLOOR, FLOOR, FLOOR, WALL),
                List.of(WALL, FLOOR, FLOOR, FLOOR, WALL),
                List.of(WALL, FLOOR, FLOOR, FLOOR, WALL),
                List.of(WALL, WALL, WALL, WALL, WALL)
        );
        width = tileMap.get(0).size();
        height = tileMap.size();
    }

    public Tile getTile(Point point){
        return getTile(point.x, point.y);
    }

    public Tile getTile(int x, int y){
        if ( x < 0 || x > width){
            return WALL;
        }
        if ( y < 0 || y > height) {
            return WALL;
        }
        try {
            List<Tile> row = tileMap.get(y);
            return row.get(x);
        }catch (Exception e){
            return WALL;
        }
    }
}
