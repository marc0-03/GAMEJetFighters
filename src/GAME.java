import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


/**
 * Created 2021-04-27
 *
 * @author
 */

public class GAME extends Canvas implements Runnable {

    private Thread thread;
    int fps = 60;
    private boolean isRunning;

    private BufferStrategy bs;

    private int i, Score1, Score2, Timer;
    private double ShipX, ShipY, ShipAngle, ShipSpeed;
    private double ShipX2, ShipY2, ShipAngle2, ShipSpeed2;
    private ArrayList<Shot> ShotsShip1 = new ArrayList<Shot>();
    private ArrayList<Shot> ShotsShip2 = new ArrayList<Shot>();
    private Boolean R1, L1, R2, L2;
    private Boolean Mov1, Mov12, Mov2, Mov22, Firing1, Firing2;

    public GAME() {
        JFrame frame = new JFrame("Breakout");
        this.setSize(1200,900);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        Reset();
        Mov1=false;
        Mov12=false;
        Mov2=false;
        Mov22=false;
    }

    private void Reset() {
        ShipX = 100;
        ShipY = 400;
        ShipX2 = 1100;
        ShipY2 = 400;
        ShipAngle = 0;
        ShipSpeed = 0;
        ShipAngle2 = Math.PI;
        ShipSpeed2 = 0;
        R1=false;
        L1=false;
        R2=false;
        L2=false;
        Firing1=false;
        Firing2=false;
        Score1=0;
        Score2=0;
        ShotsShip1.clear();
        ShotsShip2.clear();
    }

    public void update() {

        if (R1 && !L1) {
        ShipAngle += 0.1;
        } else if (L1 && !R1) {
            ShipAngle -= 0.1;
        }

        if (R2 && !L2) {
            ShipAngle2 += 0.1;
        } else if (L2 && !R2) {
            ShipAngle2 -= 0.1;
        }

        if (Timer==5) {
            if (Firing1 && ShotsShip1.size() <= 15) {
                ShotsShip1.add(new Shot((ShipX + 20 * Math.cos(ShipAngle)), (ShipY + 20 * Math.sin(ShipAngle)), ShipAngle, 240, 6));
            }

            if (Firing2 && ShotsShip2.size() <= 15) {
                ShotsShip2.add(new Shot((ShipX2 + 20 * Math.cos(ShipAngle2)), (ShipY2 + 20 * Math.sin(ShipAngle2)), ShipAngle2, 240, 6));
            }
            Timer=0;
        } else {
            Timer++;
        }

        ShipX += ShipSpeed*Math.cos(ShipAngle);
        ShipY += ShipSpeed*Math.sin(ShipAngle);

        ShipX2 += ShipSpeed2*Math.cos(ShipAngle2);
        ShipY2 += ShipSpeed2*Math.sin(ShipAngle2);

        if (ShipY<-10) {
            ShipY=810;
        } else if (ShipY>810) {
            ShipY=-10;
        } else if (ShipX<-10) {
            ShipX = 1210;
        } else if (ShipX>1210) {
            ShipX = -10;
        }

        if (ShipY2<-10) {
            ShipY2=810;
        } else if (ShipY2>810) {
            ShipY2=-10;
        } else if (ShipX2<-10) {
            ShipX2 = 1210;
        } else if (ShipX2>1210) {
            ShipX2 = -10;
        }

        Rectangle Ship1 = new Rectangle((int) ShipX-10, (int) ShipY-10, 20, 20);
        Rectangle Ship2 = new Rectangle((int) ShipX2-10, (int) ShipY2-10, 20, 20);

        for (i=0; i<ShotsShip1.size(); i++) {
            ShotsShip1.get(i).X += ShotsShip1.get(i).Xv;
            ShotsShip1.get(i).Y += ShotsShip1.get(i).Yv;
            ShotsShip1.get(i).Life++;

            if (ShotsShip1.get(i).Y<-10) {
                ShotsShip1.get(i).Y=810;
            } else if (ShotsShip1.get(i).Y>810) {
                ShotsShip1.get(i).Y=-10;
            } else if (ShotsShip1.get(i).X<-10) {
                ShotsShip1.get(i).X = 1210;
            } else if (ShotsShip1.get(i).X>1210) {
                ShotsShip1.get(i).X = -10;
            }

            if (ShotsShip1.get(i).Life>=ShotsShip1.get(i).LifeExpec){
                ShotsShip1.remove(i);
            } else if (Ship2.intersects(new Rectangle((int) ShotsShip1.get(i).X-5, (int) ShotsShip1.get(i).Y-5, 10,10))) {
                ShotsShip1.remove(i);
                Score1++;
            }
        }

        for (i=0; i<ShotsShip2.size(); i++) {
            ShotsShip2.get(i).X += ShotsShip2.get(i).Xv;
            ShotsShip2.get(i).Y += ShotsShip2.get(i).Yv;
            ShotsShip2.get(i).Life++;

            if (ShotsShip2.get(i).Y<-10) {
                ShotsShip2.get(i).Y=810;
            } else if (ShotsShip2.get(i).Y>810) {
                ShotsShip2.get(i).Y=-10;
            } else if (ShotsShip2.get(i).X<-10) {
                ShotsShip2.get(i).X = 1210;
            } else if (ShotsShip2.get(i).X>1210) {
                ShotsShip2.get(i).X = -10;
            }

            if (ShotsShip2.get(i).Life>=ShotsShip2.get(i).LifeExpec){
                ShotsShip2.remove(i);
            } else if (Ship1.intersects(new Rectangle((int) ShotsShip2.get(i).X-5, (int) ShotsShip2.get(i).Y-5, 10,10))) {
                ShotsShip2.remove(i);
                Score2++;

            }
        }




    }

    public void draw() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        update();

        g.setColor(Color.black);
        g.fillRect(0,0,1200,800);

        g.setColor(Color.CYAN);                                              //Cyan Ship
        g.fillOval((int) ShipX-10, (int) ShipY-10, 20, 20);   //Cyan Ship
        int[] Xp = new int[] {(int) (ShipX+Math.cos(ShipAngle+(Math.PI/2))*10),(int) (ShipX-Math.cos(ShipAngle+(Math.PI/2))*10), (int) (ShipX+30*Math.cos(ShipAngle))};
        int[] Yp = new int[] {(int) (ShipY+Math.sin(ShipAngle+(Math.PI/2))*10),(int) (ShipY-Math.sin(ShipAngle+(Math.PI/2))*10), (int) (ShipY+30*Math.sin(ShipAngle))};
        g.fillPolygon(Xp,Yp,3);

        for (i=0; i<ShotsShip1.size(); i++) {
            g.fillOval((int) ShotsShip1.get(i).getX()-5, (int) ShotsShip1.get(i).getY()-5, 10,10);
        }

        g.setColor(Color.RED);                                               //Red Ship
        g.fillOval((int) ShipX2-10, (int) ShipY2-10, 20, 20); //Red Ship
        Xp = new int[] {(int) (ShipX2+Math.cos(ShipAngle2+(Math.PI/2))*10),(int) (ShipX2-Math.cos(ShipAngle2+(Math.PI/2))*10), (int) (ShipX2+30*Math.cos(ShipAngle2))};
        Yp = new int[] {(int) (ShipY2+Math.sin(ShipAngle2+(Math.PI/2))*10),(int) (ShipY2-Math.sin(ShipAngle2+(Math.PI/2))*10), (int) (ShipY2+30*Math.sin(ShipAngle2))};
        g.fillPolygon(Xp,Yp,3);

        for (i=0; i<ShotsShip2.size(); i++) {
            g.fillOval((int) ShotsShip2.get(i).getX()-5, (int) ShotsShip2.get(i).getY()-5, 10,10);
        }

        g.setColor(new Color(65, 68, 94));
        g.fillRect(0,800,1200,100);
        g.setColor(new Color(87, 92, 111, 255));
        g.fillRect(5,805,1190, 90);
        g.setColor(new Color(23, 24, 31));
        g.fillRect(630,815,400,70);
        g.fillRect(170,815,400,70);
        g.setFont(new Font("Monospaced", Font.BOLD, 60));
        g.setColor(new Color(125, 125, 205));
        g.drawString(Score1+"", 190, 870);
        g.setColor(new Color(205, 125, 125));
        g.drawString(Score2+"", 650, 870);

        g.dispose();
        bs.show();
    }


    public static void main(String[] args) {
        GAME painting = new GAME();
        painting.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0 / fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now - lastTime > deltaT) {
                draw();
                lastTime = now;
            }
        }
        stop();
    }

    private class KL implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                R1=true;
            }
            if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
            L1=true;
            }
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
                if (!Mov1) {
                    ShipSpeed += 4;
                    Mov1=true;
                }
            }
            if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
                if (!Mov12){
                ShipSpeed-=4;
                Mov12=true;
                }
            }

            if (e.getKeyChar() == 'l' || e.getKeyChar() == 'L') {
                R2=true;
            }
            if (e.getKeyChar() == 'j' || e.getKeyChar() == 'J') {
                L2=true;
            }
            if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I') {
                if (!Mov2) {
                    ShipSpeed2 += 4;
                    Mov2=true;
            }
            }
            if (e.getKeyChar() == 'k' || e.getKeyChar() == 'K') {
                if (!Mov22) {
                ShipSpeed2-=4;
                Mov22=true;
                }
            }

            if (e.getKeyChar() == 'c' || e.getKeyChar() == 'c') {
                Firing1=true;
            }
            if (e.getKeyChar() == 'm' || e.getKeyChar() == 'M') {
                Firing2=true;
            }

            if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
                Reset();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                R1=false;
            }
            if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                L1=false;
            }
            if (e.getKeyChar() == 'l' || e.getKeyChar() == 'L') {
                R2=false;
            }
            if (e.getKeyChar() == 'j' || e.getKeyChar() == 'J') {
                L2=false;
            }


            if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
                ShipSpeed -= 4;
                Mov1=false;
            }
            if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
                ShipSpeed += 4;
                Mov12=false;
            }
            if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I') {
                    ShipSpeed2 -= 4;
                    Mov2=false;
            }
            if (e.getKeyChar() == 'k' || e.getKeyChar() == 'K') {
                ShipSpeed2 += 4;
                Mov22=false;
            }

            if (e.getKeyChar() == 'c' || e.getKeyChar() == 'C') {
                Firing1=false;
            }
            if (e.getKeyChar() == 'm' || e.getKeyChar() == 'M') {
                Firing2=false;
            }
    }

}
}
