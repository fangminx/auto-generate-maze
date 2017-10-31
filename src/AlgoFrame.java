import java.awt.*;
import javax.swing.*;

public class AlgoFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){
        super(title);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public AlgoFrame(String title){
        this(title, 1024, 768);
    }
    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // TODO: 设置自己的数据,替换data类型为自定义类型
    private MazeData data;
    public void render(MazeData data){
        this.data = data;
        repaint();
    }
    private class AlgoCanvas extends JPanel{
        public AlgoCanvas(){
            super(true);// 双缓存
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // TODO： 绘制自己的数据data
            int w = canvasWidth/data.getM(); //一个小格所占长
            int h = canvasHeight/data.getN(); //一个小格所占高

            for(int i=0;i<data.getN();i++){
                for (int j=0;j< data.getM();j++){
                    if(data.inMist[i][j]){
                        AlgoVisHelper.setColor(g2d,AlgoVisHelper.Black);
                    }else if(data.maze[i][j] == MazeData.WALL){
                        AlgoVisHelper.setColor(g2d,AlgoVisHelper.Cyan);
                    }else if(data.maze[i][j] == MazeData.ROAD){
                        if(data.path[i][j]) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Yellow);
                        }else {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        }
                    }
                    AlgoVisHelper.fillRectangle(g2d,j*w,i*h,w,h);
                }
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}


