package net.industrial.src.objects;

import net.industrial.grassland.CollidableGameObject;
import net.industrial.grassland.Game;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Animation;
import net.industrial.grassland.resources.SpriteSheet;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;
import org.lwjgl.input.Keyboard;

public class Player extends CollidableGameObject {
    private SpriteSheet playerSheet;
    private Vector3f velocity;
    private GameWorld world;
    private Animation idleLeft, idleRight, runLeft, runRight, currentAnimation;
    private boolean right = true, moving;

    public Player(Vector3f position, GameWorld world)
            throws GrasslandException {
        setPosition(position);
        playerSheet = new SpriteSheet("res/player.png", 24, 24);
        velocity = new Vector3f();
        this.world = world;

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
        if (game.getInput().isKeyPressed(Keyboard.KEY_SPACE))
            velocity = velocity.add(new Vector3f(0, 0.004f, 0));
        Vector3f acceleration = GameWorld.GRAVITY.add(new Vector3f(
                velocity.x * - 0.015f * delta,
                velocity.y * -0.005f * delta, 0f));

        if (game.getInput().isKeyDown(Keyboard.KEY_D)) {
            acceleration = acceleration.add(new Vector3f(0.000075f, 0, 0));
            right = true;
            moving = true;
        } else if (game.getInput().isKeyDown(Keyboard.KEY_A)) {
            acceleration = acceleration.add(new Vector3f(-0.000075f, 0, 0));
            right = false;
            moving = true;
        } else moving = false;

        velocity = velocity.add(acceleration);
        setPosition(getPosition().add(velocity.scale(delta)));
        if (getPosition().y < 0)
            setPosition(new Vector3f(getPosition().x, 0, getPosition().y));
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.fillQuad(getPosition(),
                world.getCamera().lookVector(),
                world.getCamera().axisVector(),
                Main.BLOCK_SIZE * 1.5f,
                Main.BLOCK_SIZE * 1.5f,
                currentAnimation.currentFrame());
    }
}
