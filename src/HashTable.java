import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("WeakerAccess")
public class HashTable {

    private Entry[] table;
    private int size;

    public HashTable(){
        table = new Entry[101];
    }

    public HashTable(int initCap){
        table = new Entry[initCap];
    }

    public Object put(Object key, Object value){

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
                for (int i = index+1; i < table.length; i++) {
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
                }
            }
        }

        return prev.value;
    }

    public Object get(Object key){
        int hash = key.hashCode();
        int index = hash%table.length;
        if(table[index] == null){
            return null;
        }
        else if(!table[index].removed){
            return table[index].value;
        }
        for (int i = index+1; i < table.length; i++) {
            if(table[i] == null){
                return null;
            }
            else if(!table[i].removed && table[i].key.equals(key)){
                return table[i].value;
            }
        }
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
        for (int i = index+1; i < table.length; i++) {
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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Entry entry:table) {
            if(entry == null) {
                out.append("null\n");
            }
            else if(entry.removed){
                out.append("dummy\n");
            }
            else {
                out.append(entry).append("\n");
            }
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
            return key + " : " + value;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("sampledata101.txt"));
        HashTable table = new HashTable();

        while (input.hasNext()){
            table.put(Integer.parseInt(input.next()), input.next() + " " + input.next());
        }
        System.out.println(table);
    }

}
