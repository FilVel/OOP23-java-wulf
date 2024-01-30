package javawulf.model.map;

import java.util.List;
import java.util.ArrayList;

import javafx.util.Pair;

public class BiomeImpl implements Biome {
    private final List<Pair<TilePosition, Room>> rooms;
    private final List<Pair<TilePosition, Corridor>> corridors;

    public BiomeImpl() {
        this.rooms = new ArrayList<>();
        this.corridors = new ArrayList<>();
    }

    @Override
    public void addRoom(TilePosition biomePos, Room room) {
        if(biomePos.getX() + room.getWidth() > Biome.SIZE || biomePos.getY() + room.getHeight() > Biome.SIZE ||
        biomePos.getX() < 0 || biomePos.getY() < 0) {
            throw new IllegalArgumentException("Room position & his width" + biomePos + " is out of tile SIZE ("+Biome.SIZE+") biome range");
        }
        this.rooms.add(new Pair<TilePosition, Room>(biomePos, room));
    }

    @Override
    public void addCorridor(TilePosition biomePos, Corridor corridor) {
        if(biomePos.getX() + corridor.getWidth() > Biome.SIZE || biomePos.getY() + corridor.getHeight() > Biome.SIZE ||
        biomePos.getX() < 0 || biomePos.getY() < 0) {
            throw new IllegalArgumentException("Corridor position & his width " + biomePos + " is out of tile SIZE ("+Biome.SIZE+") biome range");
        }
        this.corridors.add(new Pair<TilePosition, Corridor>(biomePos, corridor));
    }

    @Override
    public List<Pair<TilePosition, Room>> getRooms() {
        return new ArrayList<>(this.rooms);
    }

    @Override
    public List<Pair<TilePosition, Corridor>> getCorridors() {
        return new ArrayList<>(this.corridors);
    }

}
