package net.industrial.src;

import net.industrial.grassland.Game;
import net.industrial.grassland.GameState;
import net.industrial.grassland.GrasslandException;
import net.industrial.grassland.graphics.Graphics;

public class ScoreState extends GameState {
    private int score;
    public ScoreState(int score) {
        this.score = score;
    }

    @Override
    public void init(Game game) throws GrasslandException {

    }

    @Override
    public void update(Game game, int i) throws GrasslandException {

    }

    @Override
    public void render(Game game, Graphics graphics) throws GrasslandException {
        String scoreString = Integer.toString(score);
        if (scoreString.length() == 1) scoreString = "0" + scoreString;
        graphics.drawString(GameWorld.FONT_LARGE, scoreString, game.getWidth() / 2 - 120, game.getHeight() / 2 - 11 * 5);
        graphics.drawString(GameWorld.FONT, "GAME OVER", game.getWidth() / 2 - 90, game.getHeight() / 2 - 85);
    }

    @Override
    public int getId() {
        return 2;
    }
}
