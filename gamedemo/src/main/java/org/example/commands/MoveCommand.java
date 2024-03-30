package org.example.commands;

import org.example.GameEntity;
import org.example.GameMap;
import org.example.Tile;

import java.awt.*;
import java.util.List;

public class MoveCommand implements GameCommand{

    @Override
    public void execute(GameEntity entity, GameMap map, List<GameEntity> entityList) {
        Point futureLocation = entity.getForwardLocation();
        Tile tile = map.getTile(futureLocation);
        if (tile == Tile.FLOOR && isTileUnoccupied(futureLocation, entityList)){
            entity.setLocation(futureLocation);
        }
    }

    private boolean isTileUnoccupied(Point inputLocation, List<GameEntity> entities) {
        return entities.stream().noneMatch(entity->entity.getLocation().equals(inputLocation));
    }
}
