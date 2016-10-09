package cs349.jsketch;

/**
 * Created by simon on 2016-07-05.
 */
import android.util.Log;
import java.util.Observable;
import java.util.Observer;


public class Model extends Observable {

    Model(){

    }

    public void changeShape(){
        setChanged();
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer){
        super.addObserver(observer);
    }
    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    public void notifyObservers() {
        Log.d("MVC", "Model: Observers notified");
        super.notifyObservers();
    }

    @Override
    protected void setChanged() {
        super.setChanged();
    }

    @Override
    protected void clearChanged() {
        super.clearChanged();
    }
}
