package javawulf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javawulf.model.BoundingBox;
import javawulf.model.BoundingBox.CollisionType;
import javawulf.model.BoundingBoxImpl;
import javawulf.model.Coordinate;
import javawulf.model.CoordinateImpl;
import javawulf.model.Direction;
import javawulf.model.Entity;
import javawulf.model.GameObject;
import javawulf.model.player.*;
import javawulf.model.player.Sword.SwordType;

public class SwordTest {

    int health = 3;
    int startingX = 12;
    int startingY = 12;
    int startingPoints = 0;
    Sword sword;
    Player player;
    Coordinate test;
    Direction startDirection = Direction.DOWN;

    @BeforeEach
    void createPlayer(){
        this.test = new CoordinateImpl(startingX + (int)(startDirection.getX()*GameObject.OBJECT_SIZE),
            startingY + (int)(startDirection.getY()*GameObject.OBJECT_SIZE));
        this.player = new PlayerImpl(startingX, startingY, health, startingPoints);
        this.sword = this.player.getSword();
    }

    @Test
    void testStartingSwordStatistics() {
        BoundingBox expectBox = new BoundingBoxImpl(startingX + (int)(startDirection.getX()*GameObject.OBJECT_SIZE),
            startingY + (int)(startDirection.getY()*GameObject.OBJECT_SIZE), GameObject.OBJECT_SIZE,
            GameObject.OBJECT_SIZE, CollisionType.STUNNED);
        assertEquals(test.getPosition(), this.sword.getPosition().getPosition());
        assertEquals(expectBox.getCollisionArea(), this.sword.getBounds().getCollisionArea());
        assertEquals(1, this.sword.getSwordStrength());
        assertEquals(SwordType.NORMAL, this.sword.getSwordType());
        assertNotEquals(CollisionType.SWORD, this.sword.getBounds().getCollisionType());
    }

    @Test
    void testSwordMovement() {
        BoundingBox expectBox = new BoundingBoxImpl(startingX + (int)(startDirection.getX()*GameObject.OBJECT_SIZE),
            startingY + (int)(startDirection.getY()*GameObject.OBJECT_SIZE), GameObject.OBJECT_SIZE,
            GameObject.OBJECT_SIZE, CollisionType.STUNNED);
        Direction movementDirection = Direction.DOWN_LEFT;
        int delta = Entity.MOVEMENT_DELTA;
        this.player.move(movementDirection);
        assertNotEquals(test.getPosition(), this.sword.getPosition().getPosition());
        assertNotEquals(expectBox.getCollisionArea(), this.sword.getBounds().getCollisionArea());
    }

}
