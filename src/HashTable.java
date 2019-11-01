import java.util.Arrays;

public class HashTable {

    private Entry[] table;

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
        table[index] = new Entry(key, value);
        if(prev == null){
            return null;
        }
        return prev.value;
    }

    public Object get(Object key){
        int hash = key.hashCode();
        int index = hash%table.length;
        if(table[index] == null){
            return null;
        }
        return table[index].value;
    }

    @Override
    public String toString() {
        return "HashTable{" +
                "table=" + Arrays.toString(table) +
                '}';
    }

    private class Entry {

        Object key;
        Object value;

        Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {
        HashTable table = new HashTable();
        table.put("hello", "meet");
        System.out.println(table.get("hello"));

    }

}
