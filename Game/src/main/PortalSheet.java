package main;

import java.awt.image.BufferedImage;

public record PortalSheet(BufferedImage image) {

    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * 205) - 205, row, width, height);
    }

}
