package Ruleta3;
import java.math.BigInteger;
import java.util.Random;

class Object<K,V>{
	K key;//stores key
	V value;//stores value
	int aux;//stores aux, if used !=0
	public Object(K key, V value){
		this.aux=0;
		this.key=key;
		this.value=value;
	}
	public K getKey(){
		return this.key;
	}
	public V getValue(){
		return this.value;
	}
	public int getAux(){
		return this.aux;
	}
	public void setAux(int aux){
		this.aux=aux;
	}
	public void setKey(K key){
		this.key=key;
	}
	public void setValue(V value){
		this.value=value;
	}
}

public class UniversalHash<K,V>{
	private Object<K,V>[] array; //where keys and values are stored
	private long a, //random number
				 b, //random number
				 p, //prime number
				 n, //values in the table
				 m; //size
	private int aux; //auxiliar variable
	private Random random;
	public UniversalHash(){
		random=new Random();
		m=10;
		n=0;
		aux=0;
		a=new BigInteger(Integer.toString(random.nextInt(100000000))).nextProbablePrime().longValue();
		b=new BigInteger(Integer.toString(random.nextInt(100000000))).nextProbablePrime().longValue();
		p=new BigInteger(Long.toString(m)).nextProbablePrime().longValue();
		array=(Object[]) new Object[(int)m];
	}
	
	//Math: h(k)=(((ak+b)%p)%m)
	private int hash(K key, int aux){
		int code = (int) ((((((a*key.hashCode() & 0x7FFFFFF)+b) %p))+aux) %m);
		return code;
	}
	
	public void deleteAll(){
		m=10;
		n=0;
		aux=0;
		a=new BigInteger(Integer.toString(random.nextInt(100000000))).nextProbablePrime().longValue();
		b=new BigInteger(Integer.toString(random.nextInt(100000000))).nextProbablePrime().longValue();
		p=new BigInteger(Long.toString(m)).nextProbablePrime().longValue();
		this.array=(Object[]) new Object[(int) m];
	}
	
	public boolean contains(K key){
		aux=0;
		int h=hash(key,aux);
		int initial=h;
		if(array[h]==null){
			return false;
		}
		while(array[h].getKey().equals(key)==false){
			h=(int) ((h+1) % m);
			if(array[h]==null||h==initial){
				return false;
			}
		}
		return true;
	}
	
	
	public void insert(K key,V value){
		if(n>=m){
			resize();
		}
		Object<K,V> object=new Object<K, V>(key,value);
		if(isEmpty(hash(key,aux))){
			array[hash(key,aux)]=object;
			n++;	
			object.setAux(aux);
			aux=0;
		}
		else{
			//Detect collisions h(k,i)=h(k)+i%m	
			if(array[hash(key,aux)].getKey().equals(key)){
				array[hash(key,aux)]=object;
				return;
			}
			aux++;
			insert(key,value);
		}
	}
	public void setSize(int m){
		this.m=m;
	}
	public long getN(){
		return this.n;
	}
	
	public void delete(V value){
		for(int i=0;i<array.length;i++){
			if(array[i]!=null){
				if(value.equals(array[i].getValue())){
					array[i]=null;
					n--;
					return;
				}
			}
		}
	}
	public V search(K key){
		for(int aux=0;aux<array.length;aux++){
			if(isEmpty(hash(key,aux))==false){
				Object<K,V> object;
				object=array[hash(key,aux)];
				if(object.getKey().equals(key)){
					aux=0;
					return object.getValue();
				}
			}
		}
		return null;
	}
	public K searchKey(K key){
		for(int aux=0;aux<array.length;aux++){
			if(isEmpty(hash(key,aux))==false){
				Object<K,V> object;
				object=array[hash(key,aux)];
				if(object.getKey().equals(key)){
					aux=0;
					return object.getKey();
				}
			}
		}
		return null;
	}
	public void traverse(){
		for(int i=0;i<array.length;i++){
			if(array[i]==null){
				System.out.println(i+": "+"null");
			}
			else{
				System.out.println(i+": Key:"+array[i].getKey()+" Value:"+array[i].getValue());
			}
		}
//		System.out.println(n);
	}	
	private boolean isEmpty(int position){
		if(array[position]==null){
			return true;
		}
		else{
			return false;
		}
	}
	public Object<K,V>[] getArray(){
		return this.array;
	}
	private void resize(){
		Object<K,V>[] arraySave=(Object[]) new Object[(int)m];
		System.arraycopy(array, 0, arraySave, 0, (int)m);
		m*=2;
		n=0;
		p=new BigInteger(Long.toString(m)).nextProbablePrime().longValue();
		array=new Object[(int)m];
		for(int i=0;i<arraySave.length;i++){
			if(arraySave[i]!=null){
				this.insert(arraySave[i].getKey(),arraySave[i].getValue());
			}
		}
	}
}
