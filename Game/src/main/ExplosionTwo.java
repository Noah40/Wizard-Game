package main;

import java.awt.image.BufferedImage;

public record ExplosionTwo(BufferedImage image) {

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * 68) - 68, row, width, height);
    }
}

