package com.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;
import java.util.Map.Entry;

public class MiscUtil {
	
	public static Map sortByValue(Map map, final boolean reverse) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (reverse) {
					return -((Comparable) ((Entry) o1).getValue()).compareTo(((Entry) o2).getValue());
				}
				return ((Comparable) ((Entry) o1).getValue()).compareTo(((Entry) o2).getValue());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	
	
	public static void printMap(Map map) {
		for (Object key : map.keySet()) {
			System.out.println(key + " : " + map.get(key));
		}
		System.out.println("==========================================================");
	}
	
	public static void printMap(Map map, int size) {
		int cnt = 0;
		for (Object key : map.keySet()) {
			if (cnt < size) {
				System.out.println(key + ":" + map.get(key));
			} else {
				return;
			}
			cnt++;
			
		}
	}
	
	   public static Map sortMap(Map oldMap) {  
	        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(oldMap.entrySet());
	        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
	  
	            public int compare(Entry<String, Integer> arg0,
	                    Entry<String, Integer> arg1) {
	                return arg1.getValue() - arg0.getValue();  
	            }  
	        });  
	        Map newMap = new LinkedHashMap();  
	        for (int i = 0; i < list.size(); i++) {  
	            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
	        }  
	        return newMap;  
	    } 
	
	/** 
     * 获取汉字串拼音首字母，英文字符不变 
     * @param chinese 汉字串 
     * @return 汉语拼音首字母 
     */ 
    public static String getFirstSpell(String chinese) { 
            StringBuffer pybf = new StringBuffer(); 
            char[] arr = chinese.toCharArray(); 
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
            for (int i = 0; i < arr.length; i++) { 
                    if (arr[i] > 128) { 
                            try { 
                                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat); 
                                    if (temp != null) { 
                                            pybf.append(temp[0].charAt(0)); 
                                    } else {
										pybf.append(arr[0]);
									}
                            } catch (BadHanyuPinyinOutputFormatCombination e) { 
                                    e.printStackTrace(); 
                            } 
                    } else { 
                            pybf.append(arr[i]); 
                    } 
            } 
            return pybf.toString().replaceAll("\\W", "").trim(); 
    } 
    
    /** 
     * 获取汉字串拼音，英文字符不变 
     * @param chinese 汉字串 
     * @return 汉语拼音 
     */ 
    public static String getFullSpell(String chinese) { 
            StringBuffer pybf = new StringBuffer(); 
            char[] arr = chinese.toCharArray(); 
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
            for (int i = 0; i < arr.length; i++) { 
                    if (arr[i] > 128) { 
                            try { 
                            	String[] pyArray = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                            	if (pyArray != null && pyArray.length > 0) {
                            		pybf.append(pyArray[0]); 
								} else {
									pybf.append(arr[i]);
								}
                                    
                            } catch (BadHanyuPinyinOutputFormatCombination e) { 
//                            		System.out.println(arr[i]);
//                                    pybf.append(arr[i]);
                            } 
                    } else { 
                            pybf.append(arr[i]); 
                    } 
            } 
            return pybf.toString(); 
    }
	
    public static int myToInt(String str) {
    	str = str.replaceAll(",", "");
    	return Integer.parseInt(str);
    }
    
	public static void add2Map(String text, Map<String, Integer> map) {
		String key = text;
		if (map.containsKey(key)) {
			int value = map.get(key);
			map.put(key, value + 1);
		} else {
			map.put(key, 1);
		}
	}
	
	public static void add2Map(String text, Map<String, Integer> map, int byCount) {
		String key = text;
		if (map.containsKey(key)) {
			int value = map.get(key);
			map.put(key, value + byCount);
		} else {
			map.put(key, byCount);
		}
	}
	
	
	

	
	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {

	}
	
    public static  float sum(Float[] array) {
		float sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	
}
