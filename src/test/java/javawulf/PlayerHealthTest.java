package javawulf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javawulf.model.BoundingBox;
import javawulf.model.BoundingBox.CollisionType;
import javawulf.model.BoundingBoxImpl;
import javawulf.model.Entity;
import javawulf.model.player.*;
import javawulf.model.player.PlayerHealth.ShieldStatus;

public class PlayerHealthTest {

    int health = 3;
    int startingX = 12;
    int startingY = 12;
    int startingPoints = 0;
    Player player;
    PlayerHealth hp;

    @BeforeEach
    void createPlayer(){
        this.player = new PlayerImpl(startingX, startingY, health, startingPoints);
        this.hp = this.player.getPlayerHealth();
    }

    @Test
    void testStartingPlayerHealth() {
        assertEquals(health, this.hp.getHealth());
        assertEquals(health, this.hp.getMaxHealth());
        assertEquals(ShieldStatus.NONE, this.hp.getShieldStatus());
    }

    @Test
    void testDamageComingFromPlayer(){
        BoundingBox enemy = new BoundingBoxImpl(startingX, startingY,
            Entity.OBJECT_SIZE, Entity.OBJECT_SIZE, CollisionType.ENEMY);
        this.player.isHit(enemy);
        assertEquals(health-1, this.hp.getHealth());
        assertEquals(health, this.hp.getMaxHealth());
    }

    @Test
    void testHealthChange(){
        this.hp.setHealth(-1);
        assertEquals(health-1, this.hp.getHealth());
        assertEquals(health, this.hp.getMaxHealth());
        this.hp.setHealth(2);
        assertEquals(health, this.hp.getHealth());
        assertEquals(health, this.hp.getMaxHealth());
    }

    @Test
    void testMaxHealthIncrease(){
        this.hp.increaseMaxHealth(1);
        assertEquals(health+1, this.hp.getMaxHealth());
        assertEquals(health, this.hp.getHealth());
    }

    @Test
    void testShield(){
        this.hp.setShieldStatus(ShieldStatus.FULL);
        assertEquals(ShieldStatus.FULL, this.hp.getShieldStatus());

        this.hp.setHealth(-1);
        assertEquals(health, this.hp.getHealth());
        assertEquals(ShieldStatus.HALF, this.hp.getShieldStatus());

        this.hp.setHealth(-1);
        assertEquals(health, this.hp.getHealth());
        assertEquals(ShieldStatus.NONE, this.hp.getShieldStatus());

        this.hp.setHealth(-1);
        assertEquals(health-1, this.hp.getHealth());
        assertEquals(ShieldStatus.NONE, this.hp.getShieldStatus());
    }

    @Test
    void testHealthChangeAndShield(){
        this.hp.setHealth(-1);
        this.hp.setShieldStatus(ShieldStatus.FULL);
        this.hp.setHealth(-1);
        assertEquals(health-1, this.hp.getHealth());
        assertEquals(ShieldStatus.HALF, this.hp.getShieldStatus());
    }
    
}