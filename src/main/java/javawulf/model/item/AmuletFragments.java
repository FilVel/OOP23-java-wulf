package javawulf.model.item;

import javawulf.model.Collectable;
import javawulf.model.Coordinate;
import javawulf.model.player.Player;

public class AmuletFragments extends Collectable implements Item {

    public AmuletFragments(Coordinate position, Integer points) {
        super(position, points);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void applyEffect(Player p) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyEffect'");
    }

}