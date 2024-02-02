package javawulf.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javawulf.model.item.Cure;
import javawulf.model.item.CureMax;
import javawulf.model.item.ExtraHeart;
import javawulf.model.item.GreatSword;
import javawulf.model.item.Shield;

public final class ItemDrawer implements Drawer {

    private BufferedImage curePic;
    private BufferedImage cureMaxPic;
    private BufferedImage extraHeartPic;
    private BufferedImage greatswordPic;
    private BufferedImage shieldPic;

    private Cure cure;
    private CureMax cureMax;
    private ExtraHeart extraHeart;
    private GreatSword greatsword;
    private Shield shield;

    public ItemDrawer(final Cure cure, final CureMax cureMax, final ExtraHeart extraHeart, final GreatSword greatsword,
            final Shield shield) {
        this.cure = cure;
        this.cureMax = cureMax;
        this.extraHeart = extraHeart;
        this.greatsword = greatsword;
        this.shield = shield;
        try {
            this.curePic = ImageIO.read(getClass().getResourceAsStream(ImagePath.CURE.getPath()));
            this.cureMaxPic = ImageIO.read(getClass().getResourceAsStream(ImagePath.CURE_MAX.getPath()));
            this.extraHeartPic = ImageIO.read(getClass().getResourceAsStream(ImagePath.EXTRA_HEART.getPath()));
            this.greatswordPic = ImageIO.read(getClass().getResourceAsStream(ImagePath.GREAT_SWORD.getPath()));
            this.shieldPic = ImageIO.read(getClass().getResourceAsStream(ImagePath.SHIELD_ITEM.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        if (this.cure != null) {
            graphics.drawImage(this.curePic, this.cure.getPosition().getX() * GamePanel.scale,
                    this.cure.getPosition().getY() * GamePanel.scale, GamePanel.tileSize, GamePanel.tileSize, null);
        }
        if (this.cureMax != null) {
            graphics.drawImage(this.cureMaxPic, this.cureMax.getPosition().getX() * GamePanel.scale,
                    this.cureMax.getPosition().getY() * GamePanel.scale, GamePanel.tileSize, GamePanel.tileSize,
                    null);
        }
        if (this.extraHeart != null) {
            graphics.drawImage(this.extraHeartPic, this.extraHeart.getPosition().getX() * GamePanel.scale,
                    this.extraHeart.getPosition().getY() * GamePanel.scale, GamePanel.tileSize, GamePanel.tileSize,
                    null);
        }
        if (this.greatsword != null) {
            graphics.drawImage(this.greatswordPic, this.greatsword.getPosition().getX() * GamePanel.scale,
                    this.greatsword.getPosition().getY() * GamePanel.scale, GamePanel.tileSize, GamePanel.tileSize,
                    null);
        }
        if (this.shield != null) {
            graphics.drawImage(this.shieldPic, this.shield.getPosition().getX() * GamePanel.scale,
                    this.shield.getPosition().getY() * GamePanel.scale, GamePanel.tileSize, GamePanel.tileSize,
                    null);
        }
    }

}
