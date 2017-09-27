package Ruleta3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Pattern;

public class Calculos implements Serializable{
	private BufferedReader br;
	private String line,
				   text;
	private String[] words;
	private Pattern p;
	private int x;
	private Object<String,Integer>[] dictArray;
	private UniversalHash<String, Integer> uH;
	public Calculos(){
		this.x=0;
		dictArray=(Object[]) new Object[10];
		uH=new UniversalHash<String,Integer>();
	}
	public void readAll(){
		text="";
		try {
			line=br.readLine();
			while(line!=null){
				text+=line;
				line=br.readLine();
			}
			
			text=text.replace(",","");
			text=text.replace("(","");
			text=text.replace(")","");
			text=text.replace(".","");
			text=text.replace(":","");
			text=text.replace("—","");
			text=text.replace("-","");
			text=text.replace("\"","");
			text=text.replace("[","");
			text=text.replace("]","");
			text=text.replace("?","");
			text=text.replace("¿","");
			text=text.replace("!","");
			text=text.replace("¡","");
			text=text.replace(";","");
			text=text.replace("	","");
			text=text.replace("”","");
			text=text.replace("“","");
			
			words=text.split(" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void countWords(){
		int countTimes;
		for(int i=0;i<words.length;i++){
			countTimes=1;
			if(this.words[i].length()>3){
				if(uH.contains(this.words[i])){
					countTimes+=uH.search(this.words[i]);
				}
				uH.insert(this.words[i],countTimes);	
			}
		}
		uH.traverse();
		uH.getN();
	}
	
	public void compare(Calculos calculo){
		for(int i=0;i<this.dictArray.length;i++){
			if(dictArray[i]!=null){
				if(dictArray[i].getKey().equals(calculo.uH.searchKey(dictArray[i].getKey()))){
					x++;
				}
			}
		}
	}
	public int getX(){
		return this.x;
	}
	public void setX(int x){
		this.x=x;
	}
	public long getN(){
		return uH.getN();
	}
	public void obtainDictArray(){
		this.dictArray=uH.getArray();
	}
	public UniversalHash getUniversalHash(){
		return this.uH;
	}
	public UniversalHash<String, Integer> getHash(){
		return this.uH;
	}
	public void setBr(BufferedReader br){
		this.br=br;
	}
}
