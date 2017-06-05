package Client;

/**
 * Created by mauri on 6/3/2017.
 */
public class Shared_Data {

    private static Shared_Data instance = null;
    protected Shared_Data() {

    }
    public static synchronized Shared_Data getInstance() {
        if(instance == null) {
            instance = new Shared_Data();
        }
        return instance;
    }
}