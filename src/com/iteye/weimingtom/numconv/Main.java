package com.iteye.weimingtom.numconv;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("convertCurrency : " + NumConvUtils.convertCurrency("123456"));
		System.out.println("convertCurrency : " + NumConvUtils.convertCurrency("1234.56"));
		System.out.println("convertCurrency : " + NumConvUtils.convertCurrency("100006"));

		System.out.println("convert : " + NumConvExtUtils.convert("123456", false));
		System.out.println("convert : " + NumConvExtUtils.convert("1234.56", false));
		System.out.println("convert : " + NumConvExtUtils.convert("100006", false));
		
		for (int i = 0; i < 1000; i++) {
			System.out.println("convert : " + i + " => " + NumConvExtUtils.convert(Integer.toString(i), false));
		}
	}
}
