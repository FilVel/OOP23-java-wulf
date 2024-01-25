package javawulf.model.enemy;

import javawulf.model.GameElement;
import javawulf.model.player.Player;

public interface Enemy extends GameElement {
    public void move();

    public void inflictDamage(Player p);

    public void takeHit(Player p);
}
