import java.awt.*;

public class Shot {
    public double X,Y, Xv, Yv;
    public int Life, LifeExpec;
    private Rectangle hitBox;

    public Shot(double x, double y, double Angel, int Long, int Speed) {
        X=x;
        Y=y;
        Xv = Speed*Math.cos(Angel);
        Yv = Speed*Math.sin(Angel);
        Life = 0;
        LifeExpec = Long;

    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

}