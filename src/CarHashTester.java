import java.util.ArrayList;
import java.util.Arrays;

public class CarHashTester {

    public static void main(String[] args) {

        VIN vin = new VIN("1A84V6D2THY947V53");
        VIN vin1 = new VIN("1A74VAD2THV94FV53");

        System.out.println(vin.hashCode());
        System.out.println(vin1.hashCode());


    }
}
