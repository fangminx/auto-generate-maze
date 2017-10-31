import java.util.ArrayList;

public class RandomQueue<E> {

    private ArrayList<E> queue = new ArrayList<>();

    public void add(E e){
        queue.add(e);
    }

    public E remove(){
        if(queue.size()==0)
            throw new IllegalArgumentException("随机队列长度为0");
        int randIndex = (int)(Math.random()*queue.size());//范围[0,size)
        E randElement = queue.get(randIndex);
        queue.set(randIndex,queue.get(queue.size()-1));//删除该随机元素，将最后一个元素移到被删除的地方
        queue.remove(queue.size()-1);
        return  randElement;
    }

    public int size(){
        return queue.size();
    }
    public boolean isEmpty(){
        return queue.size()==0;
    }
}
