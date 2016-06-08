package com.gloudtek.util.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {
	
	//获取两个数组的交集
	public static int[] getArrayIntersection(int a[],int b[]) {
		/**
		//除去输出,最坏情况时间花费2n,空间花费4n;
		//如果用嵌套for循环,最坏情况时间花费n*n,空间花费2n
		//因此这是一个空间换时间的算法,当然嵌套for循环就是一个时间换空间的算法
		Map<Integer,Integer> map = new HashMap<>();
		Map<Integer,Integer> resultMap = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			int m = a[i];
			map.put(m, m);
		}
		for (int i = 0; i < b.length; i++) {
			int n = b[i];
			if(map.get(n)!=null){
				resultMap.put(n,n);
			}
		}
		//这里是输出部分
		int result[] = new int[resultMap.size()]; 
		int count = 0;
		for(int i:resultMap.keySet()){
			result[count++] = i;
		}
		return result;
		*/
		//使用java8的lambda表达式进行改造
		return Arrays.stream(a).flatMap(i->Arrays.stream(b).filter(j->i==j)).distinct().toArray();
	}
	
	//矩阵相乘(行乘以列)  
    public static int[][] matrixMultiplication(int a[][],int b[][]){  
        //a的列数要与b的行数相同  
        if(a[0].length!=b.length){  
            return null;  
        }  
        //得到两矩阵相乘后的新矩阵的行列  
        int aLength = a.length;  
        int bLength = b[0].length;  
        List<Integer> list = new ArrayList<Integer>();  
        //返回相乘后的数组  
        int newArray[][] = new int[aLength][bLength];  
        //相当于a行*b列  
        int totalLength = aLength * bLength;  
        int m = 0,k = 0;  
        //最外层循环用于生成矩阵  
        for (int i = 0; i < totalLength; i++) {  
            //一共计算a*b次结果,每次计算后都要将其结果清零  
            int result = 0;  
            //矩阵相乘核心计算方式  
            for (int n = 0; n < b.length; n++) {  
                result += a[m][n] * b[n][k];  
            }  
            k++;  
            if((i+1)%bLength==0){  
                m++;  
                k=0;  
            }  
            list.add(result);  
        }  
        //下面这些只是为了统一输出,可有可无  
        int count = 0;  
        for (int i = 0; i < newArray.length; i++) {  
            for (int j = 0; j < newArray[i].length; j++) {  
                newArray[i][j] = list.get(count);  
                count++;  
            }  
        }  
        return newArray;  
    }
    
    //矩阵置换(行列置换)  
    public static int[][] maxtrixPermutation(int beforeArray[][]){  
        int column = beforeArray[0].length;  
        int row = beforeArray.length;  
        int newArray[][] = new int[column][row];  
        for (int i = 0; i < newArray.length; i++) {  
            for (int j = 0; j < newArray[i].length; j++) {  
                newArray[i][j] = beforeArray[j][i];  
            }  
        }  
        return newArray;  
    }

}
