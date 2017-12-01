package net.industrial.src.objects;

import net.industrial.grassland.CollidableGameObject;
import net.industrial.grassland.Game;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;
import net.industrial.grassland.resources.Animation;
import net.industrial.src.GameWorld;
import net.industrial.src.Main;

public class Bat extends CollidableGameObject {
    private GameWorld world;
    private Animation batLeft, batRight, currentAnimation;

    public Bat(GameWorld world, Vector3f position) {
        this.world = world;
        setSize(Main.BLOCK_SIZE, Main.BLOCK_SIZE, Main.BLOCK_SIZE);
        setPosition(position);
        batLeft = new Animation(GameWorld.BAT, 0, 0, 2, 0, true, 12);
        batRight = new Animation(GameWorld.BAT, 0, 1, 2, 1, true, 12);
        batLeft.start();
        batRight.start();
        currentAnimation = batLeft;
        for (int i = 0; i < 20; i++) world.addObject(new Smoke(world, position));
    }

    @Override
    public void update(Game game, int delta) throws GrasslandException {
        Vector3f diff = world.getPlayer().getPosition().sub(getPosition()).normalise();
        setPosition(getPosition().add(diff.scale(0.00015f * delta)));
        if (diff.dot(world.getCamera().axisVector()) < 0) currentAnimation = batRight;
        else currentAnimation = batLeft;

        if (collidingWith((CollidableGameObject) world.getPlayer())) {
            kill();
            world.decrementScore();
        }

        batLeft.update(delta);
        batRight.update(delta);
    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        graphics.fillQuad(getPosition().add(world.getCamera().lookVector().scale(0.1f)),
                world.getCamera().lookVector(),
                world.getCamera().axisVector(),
                Main.BLOCK_SIZE, Main.BLOCK_SIZE, currentAnimation.currentFrame());
    }

    @Override
    public void kill() {
        for (int i = 0; i < 20; i++) world.addObject(new Smoke(world, getPosition()));
        world.addObject(new Explosion(world, getPosition()));
        super.kill();
    }
}
