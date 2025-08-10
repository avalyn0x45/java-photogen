// Randomly generate a bitmap image.
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
import com.avalyn.photogen.Bitmap;
import com.avalyn.photogen.Pixel;
import java.nio.*;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        int xsize = 128;
        int ysize = 128;
        if (args.length >= 2) {
            xsize = Integer.parseInt(args[0]);
            ysize = Integer.parseInt(args[1]);
        }
        
        Random rng = new Random();
        if (args.length == 3) {
            rng = new Random(Long.parseLong(args[2]));
        }

        Bitmap bmp = new Bitmap(xsize, ysize); 
        for (int y = 0; y < bmp.pixels.length; y++) {
            for (int x = 0; x < bmp.pixels[y].length; x++) {
                byte[] bytes = new byte[3];
                rng.nextBytes(bytes);
                bmp.pixels[y][x] = new Pixel(bytes[0], bytes[1], bytes[2]);
            }
        }

        ByteBuffer buf = bmp.create();
        System.out.write(buf.array());
    }
}
