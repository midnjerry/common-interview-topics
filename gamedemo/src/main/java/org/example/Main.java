package org.example;

import org.example.commands.AttackCommand;
import org.example.commands.GameCommand;
import org.example.commands.MoveCommand;

import java.util.*;

public class Main {
    public static void main(String[] args) {
       MoveCommand moveCommand = new MoveCommand();
        AttackCommand attackCommand = new AttackCommand();

        /*
        Map<Integer, GameCommand> commandMap = new HashMap<>();
        commandMap.put(0, new MoveCommand());
        commandMap.put(1, new AttackCommand());

        GameCommand command = commandMap.get(0);
        */




        GameMap gameMap = new GameMap();


        GameEntity entity = new GameEntity(1, 1, Direction.WEST);

        GameEntity victim = new GameEntity(2, 1, Direction.NORTH);
        List<GameEntity> entityList = Arrays.asList(entity, victim);


        System.out.println(entityList);
        moveCommand.execute(entity, gameMap, entityList);
        System.out.println(entityList);

        entity.setDirection(Direction.EAST);
        moveCommand.execute(entity, gameMap, entityList);
        System.out.println(entityList);
        moveCommand.execute(entity, gameMap, entityList);
        System.out.println(entityList);
        moveCommand.execute(entity, gameMap, entityList);
        System.out.println(entityList);

        System.out.println(entityList);
        attackCommand.execute(entity, gameMap, entityList);

        System.out.println(entityList);


    }
}