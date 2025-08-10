// Basic bitmap manipulation in Java.
// Copyright (C) 2025  Avalyn Baldyga
// 
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as
// published by the Free Software Foundation, either version 3 of the
// License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
// 
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.avalyn.photogen;
import java.nio.*;
import com.avalyn.photogen.Pixel;

public class Bitmap {
    public final int hdr_size = 14;
    public final int info_hdr_size = 40;
    int width;
    int height;
    public Pixel[][] pixels;
    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new Pixel[height][width];
    }
    public void fill(Pixel color) {
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                pixels[y][x] = color;
            }
        }
    } 
    public void fill(int r, int g, int b) {
        Pixel color = new Pixel((byte)r, (byte)g, (byte)b); 
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                pixels[y][x] = color;
            }
        }
    }
    public void overlay(Bitmap image, int xo, int yo, float opacity) {
        for (int y = 0; y < image.height; y++) {
            if (y + yo > image.height) break;
            for (int x = 0; x < image.width; x++) {
                if (x + xo > image.width) break;
                pixels[y+yo][x+xo] = Pixel.avg(
                    pixels[y+yo][x+xo], 
                    image.pixels[y][x],
                    opacity
                );
            }
        }
    }
    public ByteBuffer create() {
        int file_size = hdr_size + info_hdr_size + (width * height * 3);
        ByteBuffer buf = ByteBuffer.allocate(file_size);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.put((byte)0x42); // B
        buf.put((byte)0x4D); // M
        
        buf.putInt(file_size);
        buf.putInt(0); // resvd
        buf.putInt(hdr_size + info_hdr_size); // DataOffset
        
        buf.putInt(info_hdr_size);
        buf.putInt(width);
        buf.putInt(height);
        buf.putChar((char)1); // planes
        buf.putChar((char)24); // 24BPP RGB
        buf.putInt(0); // no compression
        buf.putInt(0); // Imagesize(no compression)
        buf.putInt(2835); // pix/m
        buf.putInt(2835); // pix/m
        buf.putInt(0);
        for (Pixel[] line : pixels) {
            for (Pixel pixel : line) {
                buf.put(pixel.r);
                buf.put(pixel.g);
                buf.put(pixel.b);
            }
        }
        return buf;
    }
}
