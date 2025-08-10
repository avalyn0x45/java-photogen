// Basic pixel class.
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

public class Pixel {
    public byte r;
    public byte g;
    public byte b;
    public Pixel(byte r, byte g, byte b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public static Pixel avg(Pixel a, Pixel b, float weight) {
        return new Pixel(
            Float.valueOf((weight * a.r) + ((1 - weight) * b.r)).byteValue(),
            Float.valueOf((weight * a.g) + ((1 - weight) * b.g)).byteValue(),
            Float.valueOf((weight * a.b) + ((1 - weight) * b.b)).byteValue()
        );
    }
}
