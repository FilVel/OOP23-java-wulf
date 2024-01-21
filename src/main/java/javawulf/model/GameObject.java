package javawulf.model;

public abstract class GameObject implements GameElement {

    private BoundingBox collision;
    private PositionOnMap position;

    public GameObject(BoundingBox collision, PositionOnMap position) {
        this.collision = collision;
        this.position = position;
    }

    public BoundingBox getBounds() {
        return this.collision;
    }

    public PositionOnMap getPosition() {
        return this.position;
    }

    public void setBounds(BoundingBox b) {
        this.collision = b;
    }

    public void setPosition(PositionOnMap p) {
        this.position = p;
    }

}