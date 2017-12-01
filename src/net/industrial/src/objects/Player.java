package net.industrial.src.objects;

import net.industrial.grassland.CollidableGameObject;
import net.industrial.grassland.Game;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector2f;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Animation;
import net.industrial.grassland.resources.SpriteSheet;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Player extends CollidableGameObject {
    private SpriteSheet playerSheet;
    private Vector2f velocity;
    private GameWorld world;
    private Animation idleLeft, idleRight, runLeft, runRight, currentAnimation;
    private boolean right = true, moving;

    public Player(Vector3f position, GameWorld world)
            throws GrasslandException {
        setPosition(position);
        playerSheet = new SpriteSheet("res/player.png", 24, 24);
        velocity = new Vector2f();
        this.world = world;
        setSize(Main.BLOCK_SIZE, Main.BLOCK_SIZE, Main.BLOCK_SIZE);

        idleRight = new Animation(playerSheet, 0, 0, 5, 0, true, 12);
        idleLeft = new Animation(playerSheet, 0, 1, 5, 1, true, 12);
        runRight = new Animation(playerSheet, 0, 2, 3, 2, true, 12);
        runLeft = new Animation(playerSheet, 0, 3, 3, 3, true, 12);
        currentAnimation = idleRight;
    }

    public void updateAnimations(int delta) {
        if (!moving && right && currentAnimation != idleRight) {
            currentAnimation = idleRight;
            idleRight.start();
        } else if (!moving && !right && currentAnimation != idleLeft) {
            currentAnimation = idleLeft;
            idleLeft.start();
        } else if (moving && right && currentAnimation != runRight) {
            currentAnimation = runRight;
            runRight.start();
        } else if (moving && !right && currentAnimation != runLeft) {
            currentAnimation = runLeft;
            runLeft.start();
        }

        if (!currentAnimation.isPlaying()) currentAnimation.start();
        currentAnimation.update(delta);
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        updateAnimations(delta);
        Vector3f axis = world.getCamera().axisVector();

        if (game.getInput().isKeyPressed(Keyboard.KEY_SPACE))
            velocity = velocity.add(new Vector2f(0, 0.003f));
        Vector2f acceleration = GameWorld.GRAVITY.add(new Vector2f(
                velocity.x * - 0.015f * delta,
                velocity.y * -0.01f * delta));

        if (game.getInput().isKeyDown(Keyboard.KEY_D) && !world.locked()) {
            acceleration = acceleration.add(new Vector2f(0.000075f, 0));
            right = true;
            moving = true;
        } else if (game.getInput().isKeyDown(Keyboard.KEY_A) && !world.locked()) {
            acceleration = acceleration.add(new Vector2f(-0.000075f, 0));
            right = false;
            moving = true;
        } else moving = false;

        velocity = velocity.add(acceleration);
        Vector3f worldVelocity = new Vector3f(0, velocity.y, 0).add(axis.scale(velocity.x));
        setPosition(getPosition().add(worldVelocity.scale(delta)));

        List<Tile> below = world.tilesAround(tileX(), tileY() - 1, tileZ());
        for (Tile tile : below) {
            if (collidingWith(tile))
                setPosition(new Vector3f(getX(), tile.getY() + Main.BLOCK_SIZE, getZ()));
        }

        Tile left = world.tileAt(tileX() - 1, tileY(), tileZ());
        if (left != null && collidingWith(left)) {
            setPosition(new Vector3f(left.getX() + Main.BLOCK_SIZE, getY(), getZ()));
            velocity.x = 0f;
        }
        Tile right = world.tileAt(tileX() + 1, tileY(), tileZ());
        if (right != null && collidingWith(right)) {
            setPosition(new Vector3f(right.getX() - Main.BLOCK_SIZE, getY(), getZ()));
            velocity.x = 0f;
        }
        Tile front = world.tileAt(tileX(), tileY(), tileZ() + 1);
        if (front != null && collidingWith(front)) {
            setPosition(new Vector3f(getX(), getY(), front.getZ() - Main.BLOCK_SIZE));
            velocity.x = 0f;
        }
        Tile back = world.tileAt(tileX(), tileY(), tileZ() - 1);
        if (back != null && collidingWith(back)) {
            setPosition(new Vector3f(getX(), getY(), back.getZ() + Main.BLOCK_SIZE));
            velocity.x = 0f;
        }
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.fillQuad(getPosition().add(world.getCamera().lookVector().scale(Main.BLOCK_SIZE)),
                world.getCamera().lookVector(),
                world.getCamera().axisVector(),
                Main.BLOCK_SIZE * 1.5f,
                Main.BLOCK_SIZE * 1.5f,
                currentAnimation.currentFrame());
        graphics.drawCuboid(Main.ORIGIN.add((new Vector3f(tileX(), tileY(), tileZ()).scale(Main.BLOCK_SIZE))), Main.BLOCK_SIZE, Main.BLOCK_SIZE, Main.BLOCK_SIZE);
    }

    public int tileX() {
        return (int) Math.round(getPosition().sub(Main.ORIGIN).x / Main.BLOCK_SIZE);
    }

    public int tileY() {
        return (int) Math.ceil(getPosition().sub(Main.ORIGIN).y / Main.BLOCK_SIZE);
    }

    public int tileZ() {
        return (int) Math.round(getPosition().sub(Main.ORIGIN).z / Main.BLOCK_SIZE);
    }
}
