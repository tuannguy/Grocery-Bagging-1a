import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class partitions of a list into k subsets and 
 * iterate through them.
 * @param <T> Generics type
 */
public class Partition<T> implements Iterable<List<List<T>>> {

    private final List<T> elements = new ArrayList<>();
    private int k = 0;

    public Partition(List<T> elements, int k) {
        checkNumberK(k, elements.size());
        this.elements.addAll(elements);
    }

    @Override
    public Iterator<List<List<T>>> iterator() {
        return new PartitionIterator<>(elements, k);
    }

    private void checkNumberK(int k, int numElements) {
        if (k < 1) {
            throw new IllegalArgumentException("The number K should be at least 1");
        }

        else if (k > numElements) {
            this.k = numElements;
        }   
        else {
        	this.k = k;
        }
    }

    private static final class PartitionIterator<T> 
    implements Iterator<List<List<T>>> {

        private List<List<T>> newPartition;
        private final List<T> elements = new ArrayList<>();
        private final int k;

        private final int[] list1;
        private final int[] list2;
        private final int size;

        PartitionIterator(List<T> elements, int k) {
            this.elements.addAll(elements);
            this.k = k;
            this.size = elements.size();

            list1 = new int[size];
            list2 = new int[size];

            if (size != 0) {
                for (int i = 0; i < size - k + 1; ++i) {
                    list1[i] = 0;
                    list2[i] = 0;
                }

                for (int i = size - k + 1; i < size; ++i) {
                    list1[i] = list2[i] = i - size + k;
                }

                inputPartition();
            }
        }

        @Override
        public boolean hasNext() {
            return newPartition != null;
        }

        @Override
        public List<List<T>> next() {
            if (newPartition == null) {
                throw new NoSuchElementException("No more partitions left.");
            }

            List<List<T>> partition = newPartition;
            generateNewPartition();
            return partition;
        }

        private void inputPartition() {
            newPartition = new ArrayList<>(k);

            for (int i = 0; i < k; ++i) {
                newPartition.add(new ArrayList<>());
            }

            for (int i = 0; i < size; ++i) {
                newPartition.get(list1[i]).add(elements.get(i));
            }
        }

        private void generateNewPartition() {
            for (int i = size - 1; i > 0; --i) {
                if (list1[i] < k - 1 && list1[i] <= list2[i - 1]) {
                    list1[i]++;
                    list2[i] = Math.max(list2[i], list1[i]);

                    for (int j = i + 1; j < size - k + list2[i] + 1; ++j) {
                        list1[j] = 0;
                        list2[j] = list2[i];
                    }

                    for (int j = size - k + list2[i] + 1; j < size; ++j) {
                        list1[j] = list2[j] = k - size + j;
                    }

                    inputPartition();
                    return;
                }
            }

            newPartition = null;
        }
    }
}