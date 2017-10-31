import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Stack;

public class AlgoVisualizer {
    private static int DELAY = 1;
    private static int blockSize = 8;
    private static final int d[][] = {{-1,0},{0,1},{1,0},{0,-1}}; //表示当前点从上开始顺时针对x和y偏移量

    // TODO: 创建自己的数据
    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图

    //用户传入的屏幕宽和高
    public AlgoVisualizer(int n, int m){

        // TODO: 初始化数据
        data = new MazeData(n,m);
        int sceneHeight = data.getN()*blockSize;
        int sceneWidth = data.getM()*blockSize;
        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("迷宫base", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
            frame.addKeyListener(new AlgoKeyListener());
//            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    //将x,y设置为ROAD，并刷新
    private void setData(int x, int y){
        if(data.inArea(x,y))
            data.maze[x][y] = MazeData.ROAD;
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    // 动画逻辑
    private void run(){
        // TODO: 编写自己的动画逻辑
        setData(-1,-1);
//        go(data.getEnterX(),data.getEnterY()+1); //根据算法特点，y要加1作为起点
//        goWithStack(data.getEnterX(),data.getEnterY()+1);
//        goWithQueue(data.getEnterX(),data.getEnterY()+1);
//        goWithRandomQueue(data.getEnterX(),data.getEnterY()+1);
        goWithRandomStackAndQueue(data.getEnterX(),data.getEnterY()+1);

        setData(-1,-1);
    }
    //方式1：以x,y作为起点开始，递归遍历
    private void go(int x, int y) {
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("坐标不在迷宫内");
        data.visited[x][y] = true;
        data.openMist(x,y);//2.起点位置遍历不到要另加
        //4个方向，取方向数组的偏移量，计算出新x和y的坐标
        for(int i =0;i<4;i++){
            int newX = x + d[i][0]*2; //ROAD的位置
            int newY = y + d[i][1]*2; //ROAD的位置
            //递归终结条件：该if语句防止无穷递归
            if(data.inArea(newX,newY) && !data.visited[newX][newY]){
                setData(x +d[i][0],y+d[i][1]);//x，y和newX，newY之间墙的位置
                go(newX,newY);
            }
        }
    }

    //方式2：以x,y作为起点，使用栈进行深度遍历
    private void goWithStack(int x,int y){
        Stack<Position> stack = new Stack<>();
        Position start = new Position(x,y);
        stack.push(start);
        data.visited[start.getX()][start.getY()] = true;
        data.openMist(start.getX(),start.getY());//2.起点位置遍历不到要另加

        while (!stack.empty()){
            Position curPos = stack.pop();
            for (int i=0;i<4;i++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;
                if(data.inArea(newX,newY) && !data.visited[newX][newY]){
                    stack.push(new Position(newX,newY));
                    data.visited[newX][newY] =true;
                    data.openMist(newX,newY);//1.打开迷雾
                    setData(curPos.getX()+d[i][0],curPos.getY()+d[i][1]);
                }
            }
        }
    }

    //方式3：以x,y作为起点，使用队列进行广度遍历
    private void goWithQueue(int x,int y){
        LinkedList<Position> queue = new LinkedList<>();
        Position start = new Position(x,y);
        queue.addLast(start);
        data.visited[start.getX()][start.getY()] = true;
        data.openMist(start.getX(),start.getY());//2.起点位置遍历不到要另加

        while (queue.size()!=0){
            Position curPos = queue.removeFirst();
            for (int i=0;i<4;i++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;
                if(data.inArea(newX,newY) && !data.visited[newX][newY]){
                    queue.addLast(new Position(newX,newY));
                    data.visited[newX][newY] =true;
                    data.openMist(newX,newY);//1.打开迷雾
                    setData(curPos.getX()+d[i][0],curPos.getY()+d[i][1]);
                }
            }
        }
    }

    //方式4：以x,y作为起点，使用自定义的随机队列进行遍历
    private void goWithRandomQueue(int x,int y){
        RandomQueue<Position> queue = new RandomQueue<>();
        Position start = new Position(x,y);
        queue.add(start);
        data.visited[start.getX()][start.getY()] = true;
        data.openMist(start.getX(),start.getY());//2.起点位置遍历不到要另加

        while (queue.size()!=0){
            Position curPos = queue.remove();
            for (int i=0;i<4;i++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;
                if(data.inArea(newX,newY) && !data.visited[newX][newY]){
                    queue.add(new Position(newX,newY));
                    data.visited[newX][newY] =true;
                    data.openMist(newX,newY);//1.打开迷雾
                    setData(curPos.getX()+d[i][0],curPos.getY()+d[i][1]);
                }
            }
        }
    }

    //方式5：以x,y作为起点，使用自定义的更加随机队列进行遍历
    private void goWithRandomStackAndQueue(int x,int y){
        RandomStackAndQueue<Position> queue = new RandomStackAndQueue<>();
        Position start = new Position(x,y);
        queue.add(start);
        data.visited[start.getX()][start.getY()] = true;
        data.openMist(start.getX(),start.getY());//2.起点位置遍历不到要另加

        while (queue.size()!=0){
            Position curPos = queue.remove();
            for (int i=0;i<4;i++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;
                if(data.inArea(newX,newY) && !data.visited[newX][newY]){
                    queue.add(new Position(newX,newY));
                    data.visited[newX][newY] =true;
                    data.openMist(newX,newY);//1.打开迷雾
                    setData(curPos.getX()+d[i][0],curPos.getY()+d[i][1]);
                }
            }
        }
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyChar() == ' '){
                //清空已访问记录数组，走迷宫要用到
                for(int i=0;i<data.getN();i++)
                    for(int j=0;j<data.getM();j++)
                        data.visited[i][j] = false;

                new Thread(()->{
                    go(data.getEnterX(), data.getEnterY());
                }).start();
            }
        }

        private boolean go(int x, int y){
            if(!data.inArea(x,y))
                throw new IllegalArgumentException("数据越界");
            data.visited[x][y] = true;
            setPathData(x, y, true);
            if(x == data.getExitX() && y == data.getExitY())
                return true;
            for(int i = 0 ; i < 4 ; i ++){
                int newX = x + d[i][0];
                int newY = y + d[i][1];
                if(data.inArea(newX, newY) && data.maze[newX][newY] == MazeData.ROAD
                        && !data.visited[newX][newY])
                    if(go(newX, newY))
                        return true;
            }
            // 回溯
            setPathData(x, y, false);
            return false;
        }
        private void setPathData(int x, int y, boolean isPath){
            if(data.inArea(x, y))
                data.path[x][y] = isPath;
            frame.render(data);
            AlgoVisHelper.pause(DELAY);
        }
    }



    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(101, 101);
    }

    private class Position {
        int x;
        int y;
        Position(int x,int y){
            this.x = x;
            this.y = y;
        }
        public int getX() {return x; }
        public int getY() {return y; }
    }
}
