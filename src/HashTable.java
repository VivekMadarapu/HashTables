import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("WeakerAccess")
public class HashTable {

    Entry[] table;
    int size;
    int collisions;
    int probes;

    public HashTable(){
        table = new Entry[101];
        collisions = 0;
        probes = 0;
    }

    public HashTable(int initCap){
        table = new Entry[initCap+1];
        collisions = 0;
        probes = 0;
    }

    public Object put(Object key, Object value){
        if(size == table.length){
            throw new IllegalStateException("Hashtable is full");
        }
        int hash = key.hashCode();
        int index = hash%table.length;
        Entry prev = table[index];
        if(prev == null){
            table[index] = new Entry(key, value);
            size++;
            return null;
        }
        else if(!prev.removed){
            if(prev.key.equals(key)) {
                table[index] = new Entry(key, value);
                return prev.value;
            }
            else{
                for (int i = index+1; i < table.length-1; i++) {

                    if(table[i] == null){
                        table[i] = new Entry(key, value);
                        size++;
                        return null;
                    }
                    else if(table[i].removed){
                        table[i] = new Entry(key, value);
                        size++;
                        while (table[i] != null){
                            if(table[i].key.equals(key)){
                                table[i].removed = true;
                                size--;
                                return table[i].value;
                            }
                            i++;
                        }
                        return null;
                    }
                    if(i == table.length-2){
                        i = 0;
                    }
                    collisions++;
                }
            }
        }
        return prev.value;
    }

    public Object putH(Object key, Object value){
        if(size == table.length){
            throw new IllegalStateException("Hashtable is full");
        }
        int hash = key.hashCode();
        int index = hash%table.length;
        Entry prev = table[index];
        if(prev == null){
            table[index] = new Entry(key, value);
            size++;
            return null;
        }
        else if(!prev.removed){
            if(prev.key.equals(key)) {
                table[index] = new Entry(key, value);
                return prev.value;
            }
            else{
                for (int i = index+1; i < table.length-1; i += hashIndex(i)) {
                    if(i > table.length-2) {
                        i = i - (table.length-2);
                    }
                    if(table[i] == null){
                        table[i] = new Entry(key, value);
                        size++;
                        return null;
                    }
                    else if(table[i].removed){
                        table[i] = new Entry(key, value);
                        size++;
                        while (table[i] != null){
                            if(table[i].key.equals(key)){
                                table[i].removed = true;
                                size--;
                                return table[i].value;
                            }
                            i++;
                        }
                        return null;
                    }
                    collisions++;
                }
            }
        }
        return prev.value;
    }

    public Object get(Object key){
        int hash = key.hashCode();
        int index = hash%table.length;
        if(table[index] == null){
            probes++;
            return null;
        }
        else if(!table[index].removed && table[index].key.equals(key)){
            probes++;
            return table[index].value;
        }
        for (int i = index+1; i < table.length-1; i++) {
            if(table[i] == null){
                probes++;
                return null;
            }
            else if(!table[i].removed && table[i].key.equals(key)){
                probes++;
                return table[i].value;
            }
            if(i == index){
                return null;
            }
            if(i == table.length-2) {
                i = 0;
            }
            probes++;
        }
        probes++;
        return null;
    }

    public Object getH(Object key){
        int hash = key.hashCode();
        int index = hash%table.length;
        if(table[index] == null){
            probes++;
            return null;
        }
        else if(!table[index].removed && table[index].key.equals(key)){
            probes++;
            return table[index].value;
        }
        for (int i = index+1; i < table.length-1; i += hashIndex(i)) {
            if(i > table.length-2) {
                i = i - (table.length-2);
            }
            if(table[i] == null){
                probes++;
                return null;
            }
            else if(!table[i].removed && table[i].key.equals(key)){
                probes++;
                return table[i].value;
            }
            if(i == index){
                return null;
            }
            probes++;
        }
        probes++;
        return null;
    }

    public Object remove(Object key){
        int hash = key.hashCode();
        int index = hash%table.length;
        if(table[index] == null){
            return null;
        }
        else if(!table[index].removed){
            table[index].removed = true;
            size--;
            return table[index].value;
        }
        for (int i = index; i < table.length; i++) {
            if(table[i] == null){
                return null;
            }
            else if(!table[i].removed && table[i].key.equals(key)){
                table[i].removed = true;
                size--;
                return table[i].value;
            }
        }
        return null;
    }

    public Object removeH(Object key){
        int hash = key.hashCode();
        int index = hash%table.length;
        if(table[index] == null){
            return null;
        }
        else if(!table[index].removed){
            table[index].removed = true;
            size--;
            return table[index].value;
        }
        for (int i = index+1; i < table.length; i += hashIndex(i)) {
            if(table[i] == null){
                return null;
            }
            else if(!table[i].removed && table[i].key.equals(key)){
                table[i].removed = true;
                size--;
                return table[i].value;
            }
        }
        return null;
    }

    private int hashIndex(int index){

        String hashString = Integer.toBinaryString(index);
        char[] hashChars = hashString.toCharArray();
        char[] newHashChars = new char[hashChars.length];
        newHashChars[0] = hashChars[hashChars.length-1];
        for (int i = 0; i < hashChars.length-1; i++) {
            newHashChars[i+1] = hashChars[i];
        }

        hashString = "";

        for (char c:newHashChars) {
            hashString += c;
        }

        return Integer.parseInt(hashString, 2);
    }


    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Entry entry:table) {
            out.append(entry).append("\n");
        }
        return out.toString();
    }

    private static class Entry {
        Object key;
        Object value;
        boolean removed;

        Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
            removed = false;
        }

        @Override
        public String toString() {
            if(removed){
                return "dummy";
            }
            else {
                return key + " : " + value;
            }
        }
    }

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
            Scanner input = new Scanner(new File("sampledata500k.txt"));
            HashTable table = new HashTable((int) Math.round((500000/i)));
            ArrayList<String> inputData = new ArrayList<>();
            while (input.hasNext()) {
                inputData.add(input.nextLine());
            }

            System.out.print("Load factor: ");
            System.out.printf("%.1f", i);
            System.out.println();
            long start = System.currentTimeMillis();
            for (String in:inputData) {
                table.putH(Integer.parseInt(in.substring(0, 8).trim()), in.substring(8).trim());
            }
            long end = System.currentTimeMillis();
            System.out.println("Time to put: " + ((end - start)/(500000/i)) + " ms");
            System.out.println("Collisions: " + (((double) table.collisions)/500000.0));
            putTimeAverages.add((end - start)/(500000/i));
            putCollisionAverages.add(((double) table.collisions)/500000.0);

            System.out.println(table.size);
            start = System.currentTimeMillis();
            for (String in:inputData) {
                table.getH(Integer.parseInt(in.substring(0, 8).trim()));
            }
            end = System.currentTimeMillis();
            System.out.println("Time to get successful: " + ((end - start)/(500000/i)) + " ms");
            System.out.println("Probes: " + (((double) table.probes)/500000.0));
            getSuccessfulTimeAverages.add((end - start)/(500000/i));
            getSuccessfulProbeAverages.add(((double) table.probes)/500000.0);

            table.probes = 0;
            start = System.currentTimeMillis();
            for (String in:inputData) {
                table.getH(Integer.parseInt(in.substring(0, 8).trim())+1);
            }
            end = System.currentTimeMillis();
            System.out.println("Time to get unsuccessful: " + ((end - start)/(500000/i)) + " ms");
            System.out.println("Probes: " + (((double) table.probes)/500000.0));
            getUnsuccessfulTimeAverages.add((end - start)/(500000/i));
            getUnsuccessfulProbeAverages.add(((double) table.probes)/500000.0);
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