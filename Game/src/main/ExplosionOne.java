package main;

import java.awt.image.BufferedImage;

public record ExplosionOne(BufferedImage image) {

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * 64) - 64 + 170, row - 1, width, height);
    }
}
