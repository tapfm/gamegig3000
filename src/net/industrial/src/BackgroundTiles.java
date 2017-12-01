package net.industrial.src;

import net.industrial.grassland.graphics.Graphics;
import net.industrial.grassland.graphics.Vector3f;

import java.util.Random;

public class BackgroundTiles {
    private int heightLevel;

    private int[][][] tiles;
    private static final int SIZE = 8;

    public BackgroundTiles(int heightLevel) {
        this.heightLevel = heightLevel;
        tiles = new int[SIZE][SIZE][SIZE];
        if (this.heightLevel == 0) generateBase();
        else generateLevel();
    }

    public void generateLevel() {
        Random r = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tiles[i][j][k] = 0;
                    if (r.nextInt(10) == 0) tiles[i][j][k] = 1;
                }
            }
        }
    }

    public void generateBase() {
        Random r = new Random();
        tiles[0][SIZE - 1][0] = -1;
        tiles[SIZE - 1][SIZE - 1][0] = -1;
        tiles[SIZE - 1][SIZE - 1][SIZE - 1] = -1;
        tiles[0][SIZE - 1][SIZE - 1] = -1;

        int layer = 0;
        for (int j = SIZE - 2; j >= 0; j--) {
            for (int i = 0; i < SIZE; i++) {
                for (int k = 0; k < SIZE; k++) {
                    if (k >= layer / 2 && i >= layer / 2 &&
                            k <= SIZE - layer / 2 && i <= SIZE - layer / 2) {
                        if (tiles[i][j + 1][k] == -1) tiles[i][j][k] = -1;
                        else if (r.nextInt(4) == 0) tiles[i][j][k] = -1;
                    } else tiles[i][j][k] = -1;
                }
            }
            layer++;
        }
    }

    public void render(Graphics graphics) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    if (tiles[i][j][k] != -1) graphics.fillCuboid(new Vector3f(
                                    (- (float) SIZE / 2f + i) * Main.BLOCK_SIZE,
                                    (- (float) SIZE / 2f + j) * Main.BLOCK_SIZE + (heightLevel - 1) * 8f * 0.05f,
                                    (- (float) SIZE / 2f + k) * Main.BLOCK_SIZE),
                            new Vector3f(1f, 0f, 0f),
                            new Vector3f(0f, 0f, -1f),
                            Main.BLOCK_SIZE,
                            Main.BLOCK_SIZE,
                            Main.BLOCK_SIZE,
                            GameWorld.TILES.getSprite(tiles[i][j][k], 0));
                }
            }
        }
    }
}
