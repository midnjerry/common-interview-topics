package org.example.commands;

import org.example.GameEntity;
import org.example.GameMap;

import java.util.List;

public interface GameCommand {
    void execute(GameEntity entity, GameMap map, List<GameEntity> entityList);
}
