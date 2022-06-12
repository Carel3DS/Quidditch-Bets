package es.dws.quidditch;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EntityHolder<K,E> {
    //We use the a generic key and generic element
    private Map<K,E> entities = new ConcurrentHashMap<>();

    //Basically, this class adapts as service the ConcurrentHashMap class
    public void add(K k, E e){
        entities.put(k,e);
    }

    public Collection<E> getEntities(){
        return entities.values();
    }

    //NOT SAFE, RISK OF DATA BREACH
    public E get(K k){
        return entities.get(k);
    }

    public void put(K k, E e){
        entities.put(k,e);
    }

    public E remove(K k) {
        E e  = entities.get(k);
        if ( e != null) {
            entities.remove(k);
        }
        return e;
    }
}

