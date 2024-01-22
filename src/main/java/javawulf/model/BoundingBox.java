package javawulf.model;

import java.awt.Rectangle;

public interface BoundingBox {

    public enum CollisionType{
        PLAYER,
        ENEMY,
        COLLECTABLE,
        SWORD,
        STUNNED; //could get renamed in the future
    }

    public boolean isCollidingWith(Rectangle box);

    public CollisionType getCollisionType();

    public void changeCollisionType(CollisionType type);
}
