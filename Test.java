package com.zsw.controller;

import java.util.Scanner;

//第一行输入一个数N（0<N<=100）,表示有N组测试数据。
//后面的N行输入多组输入数据，每组输入数据都是一个字符串S(S的长度小于10000，且S不是空串），
//测试数据组数少于5组。数据保证S中只含有"[","]","(",")"四种字符

//每组输入数据的输出占一行，如果该字符串中所含的括号是配对的，则输出Yes,如果不配对则输出No

//样例输入
//3
//[(])
//(])
//([[]()])
//样例输出
//No
//No
//Yes
public class Test {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int a = sc.nextInt();
		String[] strs= new String[a];
		for (int i = 0; i < a; i++) {
			strs[i]=sc.next();
		}
		for (String str :strs) {
			char[] chars= str.toCharArray();
			System.out.println(go(chars,new Integer(0),0));
		}
	}
	//当前位置，开口位置
	public static boolean go(char[] chars,Integer i,int j){
		
		if(chars.length%2!=0){
			return false;
		}
		boolean flag=false;
		if(String.valueOf(chars[i]).equals("[")||String.valueOf(chars[i]).equals("(")){
			go(chars,i+1,i);
		}
		for (int k = i; k < chars.length; k++) {
			if(flag==true){
				break;
			}
			if(String.valueOf(chars[k]).equals("[")||String.valueOf(chars[k]).equals("(")){
				go(chars,k+1,k);
			}else{
				if(String.valueOf(chars[j]).equals("[")){
					if(String.valueOf(chars[k]).equals("]")==true){
						flag= true;
					};
				}else if(String.valueOf(chars[j]).equals("(")){
					if(String.valueOf(chars[k]).equals(")")==true){
						flag= true;
					};
				}
			}
		}
		
		
		
		
		
		
		
		return flag;
		
		
			
//		boolean flag = false;
//		if(String.valueOf(chars[i]).equals("[")||String.valueOf(chars[i]).equals("(")){
//			 go(chars,i+1,i);
//			 
//		}else{//([[]()])
//		if(String.valueOf(chars[j]).equals("[")){
//			if(String.valueOf(chars[i]).equals("]")==true){
//				flag=true;
//				System.out.println("位置"+j+" 字符:"+chars[j]);
//				go(chars,i+1,j);
//			};
//		}else if(String.valueOf(chars[j]).equals("(")){
//			if(String.valueOf(chars[i]).equals(")")==true){
//				flag=true;
//				System.out.println("位置"+j+" 字符:"+chars[j]);
//				go(chars,i+1,j);
//			};
//		}
//		}
//		return flag;
			
		
	}
	public static boolean duibi(char char1,char char2){
		if(String.valueOf(char1).equals("[")){
			if(String.valueOf(char2).equals("]")==true){
				return true;
			};
		}else if(String.valueOf(char1).equals("(")){
			if(String.valueOf(char2).equals(")")==true){
				return true;
			};
		}
		return false;
		
	}
}
