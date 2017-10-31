public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int n; //迷宫高,在数组中表示第n+1行
    private int m; //迷宫长，在数组中表示第m+1列
    public char[][] maze; //用二维数组表示迷宫，值为WALL或ROAD
    private int enterX,enterY; //入口在数组中的位置
    private int exitX,exitY; //出口口在数组中的位置
    public boolean[][] visited; //坐标是否被访问过
    public boolean[][] inMist; //迷雾
    public boolean[][] path; //走迷宫使用

    public MazeData(int n,int m){
        //迷宫长宽不能是奇数
        if(n%2 ==0 || m%2==0)
            throw new IllegalArgumentException("迷宫的长或宽只能是奇数");
        this.n = n;
        this.m = m;

        //构造迷宫对象时就初始化maze数组，偶数行且偶数列的地方为ROAD
        maze = new char[n][m];
        visited = new boolean[n][m];
        inMist = new boolean[n][m];
        path = new boolean[n][m]; //走迷宫记录数组
        for(int i=0;i<n;i++) {
            for (int j = 0; j < m; j++) {
                if(i%2 ==1 && j%2==1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                visited[i][j] = false;
                inMist[i][j] = true;
                path[i][j] = false; //是否已走过迷宫
            }
        }
        enterX = 1;
        enterY = 0;
        exitX = n-2;
        exitY = m-1;
        //第2行第1列为起点，第n-1行m列为终点
        maze[enterX][enterY] =ROAD;
        maze[exitX][exitY] = ROAD;
    }
    public int getN(){return n;}
    public int getM(){return m;}
    public int getEnterX() {return enterX;}
    public int getEnterY() {return enterY;}
    public int getExitX() {return exitX;}
    public int getExitY() {return exitY;}
    public boolean inArea(int x,int y){
        return x>=0 && x<n && y>=0 && y<m;
    }

    public void openMist(int x, int y) {
        if(!inArea(x,y))
            throw new IllegalArgumentException("迷雾坐标越界");
        for(int i=x-1;i<=x+1;i++)
            for(int j=y-1;j<=y+1;j++)
                if(inArea(i,j))
                inMist[i][j]=false;
    }
}
