package javawulf.model;

public abstract class Collectable extends GameObject {

    private Integer points;

    public Collectable(PositionOnMap position, Integer points) {
        super(position);
        this.points = points;
    }
    
    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
  
    public void collect(Player p){
        if (p.getPosition().equals(this.position)) {
            this.applyEffect(p);
        }
    };

    public abstract void applyEffect(Player p);

}