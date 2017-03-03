package com.BYL.lotteryTools.backstage.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.BYL.lotteryTools.common.util.MyMD5Util;

public class TestController {

	
	public static void main(String args[])
	{
		try {
			System.out.println(MyMD5Util.getEncryptedPwd("123456"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
