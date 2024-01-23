package javawulf.model.map;

public class TileImpl implements Tile {

    private final TileType type;
    
    public TileImpl(TileType type) {
        this.type = type;
    }

    @Override
    public TileType getType() {
        return this.type;
    }

}
