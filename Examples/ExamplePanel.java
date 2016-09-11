package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by Eric Vue on 9/11/2016.
 */

public class ExamplePanel extends JPanel {
    private ArrayList<Object> list = new ArrayList<>();
    private Object player;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//this right here prevents "leftovers" of the movement
        for (Object o : list) {
            g.drawOval(o.getX(), o.getY(), o.getSize(), o.getSize());
        }
    }

    public ExamplePanel() {
        list.add(new Object(100, 100, 50, true));
        list.add(new Object(100, 200, 50, false));

        for (Object o : list) {
            if (o.isPlayer()) {
                player = o;
            }
        }

        /*
        Way better than Key Listener

        getInputMap is setting up what a key does, and assigning it a value.

        getActionMap is using that value and setting up and action for it.
         */
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), 1);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), 2);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), 3);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), 4);
        getActionMap().put(1, new MoveAction(player, 1, this));
        getActionMap().put(2, new MoveAction(player, 2, this));
        getActionMap().put(3, new MoveAction(player, 3, this));
        getActionMap().put(4, new MoveAction(player, 4, this));
    }

    private ArrayList<Object> getList() {
        return list;
    }

    public void printLocPlayer() {
        System.out.println("PLAYERX: " + player.getX() + " PLAYERY: " + player.getY());
    }
    /*
    Just a test Object used for drawing circles on JPanel
     */
    public class Object {

        private int x, y, size;
        private boolean isPlayer;

        public Object(int x, int y, int size, boolean isPlayer) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.isPlayer = isPlayer;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        /*
        Basically the radius
         */
        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean collided(ArrayList<Object> list) {
            for (Object o : list) {
                if (!o.isPlayer()) {
                   //Do math later.
                }
            }
            return false;
        }

        /*
        This is just a test, but in the future create 2 different objects that extend the same base. One player, one NPC probably both extend Character
         */
        public boolean isPlayer() {
            return isPlayer;
        }

        public void move(int direction) {
            int moveDistance = 5;
            switch (direction) {
                //up
                case 1:
                    //Important to know that JPanel starts at 0,0 top left so everything is inverted for up and down. Subtract y to go up, add to go down.
                    setY(y - moveDistance);
                    break;
                //down
                case 2:
                    setY(y + moveDistance);
                    break;
                //left
                case 3:
                    setX(x - moveDistance);
                    break;
                //right
                case 4:
                    setX(x + moveDistance);
                    break;
            }
        }
    }
    //Action to help "move" the player object
    public class MoveAction extends AbstractAction {

        private Object object;
        private ExamplePanel panel;
        private int direction;

        public MoveAction(Object object, int direction, ExamplePanel panel) {
            this.object = object;
            this.panel = panel;
            this.direction = direction;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (object.collided(panel.getList())) {
                System.out.println("COLLIDED");
                object.move(direction);
            }
            object.move(direction);
            panel.printLocPlayer();
            panel.revalidate();
            panel.repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setResizable(true);
        ExamplePanel testFrame = new ExamplePanel();
        testFrame.setFocusable(true);
        testFrame.requestFocusInWindow();
        frame.add(testFrame);
    }
}
