package com.javaweb.util.common;

import java.util.Arrays;

public class ArrayUtil {
	
	//获取两个数组的交集
	public static int[] getArrayIntersection(int a[],int b[]) {
		return Arrays.stream(a).flatMap(i->Arrays.stream(b).filter(j->i==j)).distinct().toArray();
	}
	
	//矩阵相乘(行乘以列)  
    public static int[] matrixMultiplication(int a[][],int b[][]){  
        //a的列数要与b的行数相同  
        if(a[0].length!=b.length){  
            return null;  
        } 
        int aLength = a.length;
        int bLength = b[0].length;
        //返回相乘后的数组  
        int newArray[] = new int[aLength*bLength];  
        int m = 0,k = 0;  
        //最外层循环用于生成矩阵  
        for (int i = 0; i < newArray.length; i++) {  
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
            newArray[i] = result;  
        }  
        return newArray;
    }
    
    //矩阵置换(行列置换)  
    public static int[][] maxtrixPermutation(int beforeArray[][]){  
        int newArray[][] = new int[beforeArray[0].length][beforeArray.length];  
        for (int i = 0; i < newArray.length; i++) {  
            for (int j = 0; j < newArray[i].length; j++) {  
                newArray[i][j] = beforeArray[j][i];  
            }  
        }  
        return newArray;  
    }

}
