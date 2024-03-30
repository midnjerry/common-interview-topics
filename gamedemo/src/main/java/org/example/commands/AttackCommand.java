package org.example.commands;

import org.example.GameEntity;
import org.example.GameMap;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class AttackCommand implements GameCommand{

    @Override
    public void execute(GameEntity entity, GameMap map, List<GameEntity> entityList) {
        Point forwardLocation = entity.getForwardLocation();
        Optional<GameEntity> victimOptional = getEntityAtLocation(forwardLocation, entityList);
        if (victimOptional.isPresent()){
            victimOptional.get().setAlive(false);
        }
    }

    private Optional<GameEntity> getEntityAtLocation(Point inputLocation, List<GameEntity> entityList) {
        return entityList.stream().filter(entity -> entity.getLocation().equals(inputLocation)).findFirst();
    }

}
