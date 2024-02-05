package javawulf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javawulf.model.BoundingBox;
import javawulf.model.Collectable;
import javawulf.model.GameElement;
import javawulf.model.enemy.Pawn;
import javawulf.model.item.AmuletPiece;
import javawulf.model.item.Cure;
import javawulf.model.item.CureMax;
import javawulf.model.item.ExtraHeart;
import javawulf.model.item.GreatSword;
import javawulf.model.item.Shield;
import javawulf.model.GameObject;
import javawulf.model.map.Map;
import javawulf.model.map.factory.MapFactoryImpl;
import javawulf.model.player.Player;
import javawulf.model.player.PlayerImpl;
import javawulf.model.powerup.PowerUp;
import javawulf.view.GamePanel;

/**
 * @see GameLoop
 */
public final class GameLoopImpl implements GameLoop, Runnable {

    private static final int NANOSECONDS = 1_000_000_000;
    private long lastTime;
    private long currentTime;
    private long timer;
    private double delta;
    private double interval;
    private Thread gameLoopThread;
    private final GamePanel gamePanel;
    private Map gameMap;
    private Player gamePlayer;
    private boolean attacking;
    private long swordTime;
    private final PlayerController playerController;
    private final List<Collectable> items;
    private final List<Pawn> pawns;
    private final List<AmuletPiece> pieces;
    private final List<PowerUp> powerUps;
    private boolean playerDead;
    private boolean gameWon;

    /**
     * 
     * @param panel's view.
     */
    public GameLoopImpl(final GamePanel panel) {
        this.items = new ArrayList<>();
        this.pawns = new ArrayList<>();
        this.pieces = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        playerInit();
        mapInit();
        this.playerController = new PlayerControllerImpl();
        this.gamePanel = panel;
    }

    private void mapInit() {
        this.gameMap = new MapFactoryImpl().getDefaultMap1(this.gamePlayer);
        var elements = this.gameMap.getAllElements();
        this.items.addAll(elements.stream()
                .filter(this::isItem)
                .map(e -> (Collectable) e)
                .collect(Collectors.toList()));
        this.pawns.addAll(elements.stream()
                .filter(e -> e instanceof Pawn)
                .map(e -> (Pawn) e)
                .collect(Collectors.toList()));
        this.pieces.addAll(elements.stream()
                .filter(e -> e instanceof AmuletPiece)
                .map(e -> (AmuletPiece) e)
                .collect(Collectors.toList()));
        this.powerUps.addAll(elements.stream()
                .filter(e -> e instanceof PowerUp)
                .map(e -> (PowerUp) e)
                .collect(Collectors.toList()));
    }

    private boolean isItem(GameElement e) {
        return e instanceof Collectable && (e instanceof Cure || e instanceof CureMax
                || e instanceof ExtraHeart || e instanceof GreatSword || e instanceof Shield);
    }

    private void playerInit() {
        this.gamePlayer = new PlayerImpl(Map.MAP_SIZE * GameObject.OBJECT_SIZE / 2,
                Map.MAP_SIZE * GameObject.OBJECT_SIZE / 2, 3, 0);
    }

    @Override
    public void run() {
        this.interval = NANOSECONDS / FPS;
        lastTime = System.nanoTime();

        while (this.gameLoopThread != null && !playerDead && !gameWon) {
            this.gameLoopBody();
        }
        this.gamePanel.resetFrame(gameWon, this.gamePlayer.getScore().getPoints());
    }

    private void gameLoopBody() {
        this.currentTime = System.nanoTime();
        delta += (this.currentTime - this.lastTime) / this.interval;
        this.timer += (this.currentTime - this.lastTime);
        this.lastTime = this.currentTime;

        if (delta >= 1) {
            this.update();
            this.reDraw();
            this.delta--;
        }

        if (this.timer >= NANOSECONDS * 1) {
            this.timer = 0;
            this.pawns.forEach(p -> {
                p.tick();
            });
            this.gamePlayer.reduceStun();
            this.gamePlayer.getPowerUpHandler().update(this.gamePlayer);
            // System.out.println(this.getMap().getPlayerRoom());
        }
    }

    private void update() {
        if (this.playerController.getDirection().isPresent() && !this.attacking) {
            try {
                this.gamePlayer.move(this.playerController.getDirection().get(), this.gameMap);
            } catch (Exception e) {
                System.out.println("There is a wall");
            }
        } else if (this.playerController.isAttack() && !this.attacking) {
            this.gamePlayer.attack();
            this.attacking = true;
            this.swordTime = System.nanoTime();
        }

        if (System.nanoTime() - this.swordTime >= NANOSECONDS / 2 && attacking) {
            this.gamePlayer.getSword().deactivate();
            this.attacking = false;
            this.swordTime = 0;
        }

        // Per ogni nemico nella lista, chiama i suoi metodi di movimento, il tick di
        // aggiornamento ed i suoi controlli, quando muore viene rimosso dalla lista
        this.pawns.forEach(p -> {
            p.move(this.gamePlayer, this.gameMap);
            p.takeHit(this.gamePlayer);
            this.gamePlayer.isHit(p.getBounds());
        });
        this.pawns
                .removeIf(p -> !p.isAlive() && p.getBounds().getCollisionType() == BoundingBox.CollisionType.INACTIVE);

        // Per ogni pezzo di amuleto nella lista, controlla se è stato raccolto e se il
        // giocatore è allineato ad uno dei suoi assi, quando viene raccolto viene
        // rimosso dalla lista
        this.pieces.forEach(p -> {
            p.collect(this.gamePlayer);
            p.isPlayerAligned(this.gamePlayer);
        });
        this.pieces.removeIf(p -> p.getBounds().getCollisionType() == BoundingBox.CollisionType.INACTIVE);

        // Per ogni oggetto collezionabile nella lista, controlla se è stato raccolto,
        // una volta raccolto viene rimosso dalla lista
        this.items.forEach(i -> i.collect(this.gamePlayer));
        this.items.removeIf(i -> i.getBounds().getCollisionType() == BoundingBox.CollisionType.INACTIVE);

        // Qui l'update degli elementi di gioco (giocatore, nemici, ...)

        if (this.gamePlayer.getBounds().getCollisionType().equals(BoundingBox.CollisionType.INACTIVE)) {
            playerDead = true;
        }
        if (this.gamePlayer.hasPlayerWon(gameMap)) {
            gameWon = true;
        }
        this.powerUps.forEach(p -> p.collect(gamePlayer));
        this.powerUps.removeIf(p -> p.getBounds().getCollisionType() == BoundingBox.CollisionType.INACTIVE);
    }

    private void reDraw() {
        // Qui l'update della view...
        this.gamePanel.repaint();

    }

    @Override
    public void startGameLoopThread() {

        this.gameLoopThread = new Thread(this);
        this.gameLoopThread.start();
    }

    @Override
    public Map getMap() {
        return this.gameMap;
    }

    @Override
    public Player getPlayer() {
        return this.gamePlayer;
    }

    @Override
    public PlayerController getPlayerController() {
        return this.playerController;
    }

    @Override
    public List<Collectable> getItems() {
        return this.items;
    }

    @Override
    public List<Pawn> getPawns() {
        return this.pawns;
    }

    @Override
    public List<AmuletPiece> getAmuletPieces() {
        return this.pieces;
    }

    @Override
    public List<PowerUp> getPowerUps() {
        return this.powerUps;
    }

}
