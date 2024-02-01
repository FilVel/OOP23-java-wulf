package javawulf.model.map;

import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;

/**
 * An Utility class that creates a HashMap<TilePosition, TileType> from four
 * biomes (and generating central biome, too).
 * The HashMap associates for each position a tile-type, thus composing the game
 * map.
 * This class will be exclusively used by
 * MapImpl for the sole purpose of making, internally, the map.
 */
public final class MapTilesBuilder {

    /**
     * Each element in BiomeQuadrant represent one of four biome of the map. Used
     * for get their offset positions.
     */
    enum BiomeQuadrant {
        /**
         * First biome (upper-left).
         */
        FIRST(0, new TilePosition(0, 0)),
        /**
         * Second biome (upper-right).
         */
        SECOND(1, new TilePosition(Biome.SIZE + Map.WIDTH_CENTRAL_BIOME, 0)),
        /**
         * Third biome (downer-right).
         */
        THIRD(2, new TilePosition(Biome.SIZE + Map.WIDTH_CENTRAL_BIOME, Biome.SIZE + Map.WIDTH_CENTRAL_BIOME)),
        /**
         * Fourth biome (downer-left).
         */
        FOURTH(3, new TilePosition(0, Biome.SIZE + Map.WIDTH_CENTRAL_BIOME));

        private final int pos;
        private final TilePosition offset;

        BiomeQuadrant(final int pos, final TilePosition offset) {
            this.pos = pos;
            this.offset = offset;
        }

        public TilePosition getOffset() {
            return this.offset;
        }

        public int getPos() {
            return this.pos;
        }
    }

    private MapTilesBuilder() {
        throw new UnsupportedOperationException("This class cannot be instantiated (Utility class)");
    }

    /**
     * 
     * @param biomes is a list that have four biomes of tiles map to be build.
     * @return a built map tiles.
     */
    public static HashMap<TilePosition, TileType> buildTiles(final List<Biome> biomes) {
        return build(biomes);
    }

    private static HashMap<TilePosition, TileType> build(final List<Biome> biomes) {
        final HashMap<TilePosition, TileType> tiles = new HashMap<>();
        for (var biomeOffSet : BiomeQuadrant.values()) {
            buildSpacesBiome(tiles, biomeOffSet, biomes.get(biomeOffSet.getPos()).getRooms(), Room.DEFAULT_TYPE);
            buildSpacesBiome(tiles, biomeOffSet, biomes.get(biomeOffSet.getPos()).getCorridors(),
                    Corridor.DEFAULT_TYPE);
        }
        buildCentralBiome(tiles);
        return tiles;
    }

    /**
     * Used to build spaces in a specific biome.
     * @param tiles HashMap where build (w*h) spaces
     * @param biomeQuadrant used for take the specific position quadrant offset
     * @param spaces a list of Pair (SpacePosition, Space) to build in the HashMap
     * @param defaultSpaceTile to use for build spaces
     */
    private static void buildSpacesBiome(final HashMap<TilePosition, TileType> tiles, 
            final BiomeQuadrant biomeQuadrant, final List<Pair<TilePosition, Space>> spaces,
            final TileType defaultSpaceTile) {
        for (var space : spaces) {
            buildSpace(tiles, new TilePosition(space.getKey().getX() + biomeQuadrant.getOffset().getX(), space.getKey().getY() + biomeQuadrant.getOffset().getY()), space.getValue(), defaultSpaceTile);
        }
    }

    /**
     * Used to build a specific space inside the whole map.
     * @param tiles
     * @param posToStart where build Space inside map
     * @param space
     * @param defaultSpaceTile to use for build space
     */
    private static void buildSpace(final HashMap<TilePosition, TileType> tiles, 
    TilePosition posToStart, Space space,
    final TileType defaultSpaceTile) {
        for (int y = posToStart.getY(); y < posToStart.getY() + space.getHeight(); y++) {
            for (int x = posToStart.getX(); x < posToStart.getX() + space.getWidth(); x++) {
                tiles.put(new TilePosition(x, y), defaultSpaceTile);
            }
        }
    }

    private static void buildCentralBiome(final HashMap<TilePosition, TileType> tiles) {
        buildSpace(tiles, new TilePosition(Biome.SIZE, 3), new Corridor(Map.WIDTH_CENTRAL_BIOME, 2), TileType.CORRIDOR);
        buildSpace(tiles, new TilePosition(Biome.SIZE, 15), new Corridor(Map.WIDTH_CENTRAL_BIOME, 2), TileType.CORRIDOR);
        buildSpace(tiles, new TilePosition(Biome.SIZE, Biome.SIZE + Map.WIDTH_CENTRAL_BIOME + 3), new Corridor(Map.WIDTH_CENTRAL_BIOME, 2), TileType.CORRIDOR);
        buildSpace(tiles, new TilePosition(Biome.SIZE, Biome.SIZE + Map.WIDTH_CENTRAL_BIOME + 15), new Corridor(Map.WIDTH_CENTRAL_BIOME, 2), TileType.CORRIDOR);

        buildSpace(tiles, new TilePosition(3, Biome.SIZE), new Corridor(2, Map.WIDTH_CENTRAL_BIOME), TileType.CORRIDOR);
        buildSpace(tiles, new TilePosition(15, Biome.SIZE), new Corridor(2, Map.WIDTH_CENTRAL_BIOME), TileType.CORRIDOR);
        buildSpace(tiles, new TilePosition(Biome.SIZE + Map.WIDTH_CENTRAL_BIOME + 3, Biome.SIZE), new Corridor(2, Map.WIDTH_CENTRAL_BIOME), TileType.CORRIDOR);
        buildSpace(tiles, new TilePosition(Biome.SIZE + Map.WIDTH_CENTRAL_BIOME + 15, Biome.SIZE), new Corridor(2, Map.WIDTH_CENTRAL_BIOME), TileType.CORRIDOR);

        buildSpace(tiles, new TilePosition(Biome.SIZE + 2, Biome.SIZE + 2), new Room(Map.WIDTH_CENTRAL_BIOME-2*2, Map.WIDTH_CENTRAL_BIOME-2*2), TileType.CENTRAL_ROOM);
        buildSpace(tiles, new TilePosition(Biome.SIZE + Map.WIDTH_CENTRAL_BIOME / 2 - 1, Biome.SIZE - 3), new Room(2, 5), TileType.CENTRAL_ROOM);
        buildSpace(tiles, new TilePosition(Biome.SIZE + Map.WIDTH_CENTRAL_BIOME / 2 - 1, Biome.SIZE + 8), new Room(2, 5), TileType.CENTRAL_ROOM);
        buildSpace(tiles, new TilePosition(Biome.SIZE - 3, Biome.SIZE + Map.WIDTH_CENTRAL_BIOME / 2 - 1), new Room(5, 2), TileType.CENTRAL_ROOM);
        buildSpace(tiles, new TilePosition(Biome.SIZE + 8, Biome.SIZE + Map.WIDTH_CENTRAL_BIOME / 2 - 1), new Room(5, 2), TileType.CENTRAL_ROOM);
    }
}
