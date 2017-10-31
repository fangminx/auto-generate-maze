import java.util.LinkedList;

public class RandomStackAndQueue<E> {

    private LinkedList<E> queue = new LinkedList<>();

    public void add(E e){
        if(Math.random()<0.5)
            queue.addFirst(e);
        else
            queue.addLast(e);
    }

    public E remove(){
        if (queue.size()==0)
            throw new IllegalArgumentException("队列为空");
        else if(Math.random()<0.5)
            return queue.removeFirst();
        else
            return queue.removeLast();
    }

    public int size(){
        return queue.size();
    }

    public boolean empty(){
        return size()==0;
    }
}
