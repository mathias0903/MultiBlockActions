package io.github.mathias0903.MultiBlockActions.misc;

import javafx.scene.transform.Rotate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RelativeLocation {
    private int y;
    private int x;
    private int z;

    public RelativeLocation(int x, int y, int z) {
        this.y = y;
        this.x = x;
        this.z = z;
    }
    public RelativeLocation rotateCW() {
        return new RelativeLocation(this.z, this.y, -this.x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "RelativeLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelativeLocation that = (RelativeLocation) o;
        return y == that.y &&
                x == that.x &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x, z);
    }

}
