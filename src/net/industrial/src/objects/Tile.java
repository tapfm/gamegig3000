package net.industrial.src.objects;

import net.industrial.grassland.CollidableGameObject;
import net.industrial.grassland.Game;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Sprite;
import net.industrial.grassland.resources.SpriteSheet;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;

public class Tile extends CollidableGameObject {

    private int tileX;
    private int tileY;
    private int tileZ;
    private int heightLevel;

    private Sprite sprite;
    private GameWorld world;

    public static final int SIZE = 8;

    public Tile(int x, int y, int z, int h, GameWorld world) throws GrasslandException {
        this.world = world;
        sprite = GameWorld.TILES.getSprite(3, 0);

        tileX = x;
        tileY = y;
        tileZ = z;

        if (((x == 0 && z == 0) || (x == 0 && z == 9) || (x == 9 && z == 0) || (x == 9 && z == 9)) && y != 0)
            sprite = GameWorld.TILES.getSprite(2, 0);
        setPosition((new Vector3f(tileX, tileY, tileZ)).scale(Main.BLOCK_SIZE).add(Main.ORIGIN));

        heightLevel = h;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public int getTileZ() {
        return tileZ;
    }

    public int getHeightLevel() {
        return heightLevel;
    }


    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.fillCuboid(getPosition(), new Vector3f(1f,0f,0f), new Vector3f(0f,0f,-1f), Main.BLOCK_SIZE, Main.BLOCK_SIZE, Main.BLOCK_SIZE, sprite);
    }


    public void update(Game game, int delta) throws GrasslandException {

    }


}
