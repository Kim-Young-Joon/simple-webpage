package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {
    /**
     * 여러 Thread 가 동시에 접근하는 경우에 문제가 발생할 수 있음
     * 1. HashMap 이 아닌 ConcurrentHashMap 을 사용
     * 2. Long 이 아닌 AtomicLong 등의 자료형을 사용해야 함
     */
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
        /*
            이렇게 Collection 으로 감싸지 않고 store 를 바로 반환해도 되는데 굳이 하는 이유는
            store 자체에 대한 변화는 막기 위해서이다. (데이터의 안정성을 위해)
            + 타입 변환
         */
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        // test 할 때 사용할 용도 (store 데이터 다 날리기)
        store.clear();
    }

}
