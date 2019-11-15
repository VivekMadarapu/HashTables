import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CarHashTester {

    public static void main(String[] args) throws IOException {

        File output = new File("results.csv");
        int version = 0;
        while (output.exists()){
            version++;
            output = new File("results" + version + ".csv");
        }
        output.createNewFile();
        FileWriter writer = new FileWriter(output);

        ArrayList<Double> putTimeAverages = new ArrayList<>();
        ArrayList<Double> getSuccessfulTimeAverages = new ArrayList<>();
        ArrayList<Double> getUnsuccessfulTimeAverages = new ArrayList<>();
        ArrayList<Double> putCollisionAverages = new ArrayList<>();
        ArrayList<Double> getSuccessfulProbeAverages = new ArrayList<>();
        ArrayList<Double> getUnsuccessfulProbeAverages = new ArrayList<>();

//        StdDraw.setCanvasSize(500, 500);

        for(double i = 0.1;i < 1.0;i+=0.1) {
            Scanner input = new Scanner(new File("Large Data Set.txt"));
            HashTable table = new HashTable((int) Math.round((50000/i)));
            ArrayList<String> inputData = new ArrayList<>();
            while (input.hasNext()) {
                inputData.add(input.nextLine());
            }

            System.out.print("Load factor: ");
            System.out.printf("%.1f", i);
            System.out.println();
            long start = System.currentTimeMillis();
            for (String in:inputData) {
                table.put(new VIN(in.substring(0, 17).trim()), new Car(Integer.parseInt(in.substring(18, 21).trim()), in.substring(23, 66).trim(), in.substring(67).trim()));
            }
            long end = System.currentTimeMillis();
            System.out.println("Time to put: " + ((end - start)/(50000/i)) + " ms");
            System.out.println("Collisions: " + (((double) table.collisions)/50000.0));
            putTimeAverages.add((end - start)/(50000/i));
            putCollisionAverages.add(((double) table.collisions)/50000.0);

            System.out.println(table.size);

            input = new Scanner(new File("Successful Search.txt"));
            inputData.clear();
            while (input.hasNext()) {
                inputData.add(input.nextLine());
            }

            start = System.currentTimeMillis();
            for (String in:inputData) {
                table.get(new VIN(in.substring(0, 17).trim()));
            }
            end = System.currentTimeMillis();
            System.out.println("Time to get successful: " + ((end - start)/(50000/i)) + " ms");
            System.out.println("Probes: " + (((double) table.probes)/50000.0));
            getSuccessfulTimeAverages.add((end - start)/(50000/i));
            getSuccessfulProbeAverages.add(((double) table.probes)/50000.0);

            table.probes = 0;

            input = new Scanner(new File("Unsuccessful Search.txt"));
            inputData.clear();
            while (input.hasNext()) {
                inputData.add(input.nextLine());
            }

            start = System.currentTimeMillis();
            for (String in:inputData) {
                table.get(new VIN(in.substring(0, 17).trim()));
            }
            end = System.currentTimeMillis();
            System.out.println("Time to get unsuccessful: " + ((end - start)/(50000/i)) + " ms");
            System.out.println("Probes: " + (((double) table.probes)/50000.0));
            getUnsuccessfulTimeAverages.add((end - start)/(50000/i));
            getUnsuccessfulProbeAverages.add(((double) table.probes)/50000.0);
            System.out.println();
        }
        writer.write("Load Factor,Time\n");
        writer.flush();
        for (int i = 0;i < putTimeAverages.size();i++) {
            writer.write((((double) i+1.0)/10) + "," + putTimeAverages.get(i)+"\n");
            writer.flush();
        }
        writer.write("\n\n\n\n\n\nLoad Factor,Time\n");
        writer.flush();
        for (int i = 0;i < getSuccessfulTimeAverages.size();i++) {
            writer.write((((double) i+1.0)/10) + "," + getSuccessfulTimeAverages.get(i)+"\n");
//            if(i < getSuccessfulTimeAverages.size()-1) {
//                StdDraw.line(i / 10.0, getSuccessfulTimeAverages.get(i) * 1000, (i + 1) / 10.0, getSuccessfulTimeAverages.get(i + 1) * 1000);
//            }
//            StdDraw.filledCircle(i/10.0, getSuccessfulTimeAverages.get(i)*1000, 0.005);

            writer.flush();
        }
        writer.write("\n\n\n\n\n\nLoad Factor,Time\n");
        writer.flush();
        for (int i = 0;i < getUnsuccessfulTimeAverages.size();i++) {
            writer.write((((double) i+1.0)/10) + "," + getUnsuccessfulTimeAverages.get(i)+"\n");
            writer.flush();
        }
        writer.write("\n\n\n\n\n\nLoad Factor,Collisions\n");
        writer.flush();
        for (int i = 0;i < putCollisionAverages.size();i++) {
            writer.write((((double) i+1.0)/10) + "," + putCollisionAverages.get(i)+"\n");
            writer.flush();
        }
        writer.write("\n\n\n\n\n\nLoad Factor,Probes\n");
        writer.flush();
        for (int i = 0;i < getSuccessfulProbeAverages.size();i++) {
            writer.write((((double) i+1.0)/10) + "," + getSuccessfulProbeAverages.get(i)+"\n");
            writer.flush();
        }
        writer.write("\n\n\n\n\n\nLoad Factor,Probes\n");
        writer.flush();
        for (int i = 0;i < getUnsuccessfulProbeAverages.size();i++) {
            writer.write((((double) i+1.0)/10) + "," + getUnsuccessfulProbeAverages.get(i)+"\n");
            writer.flush();
        }

    }
}
