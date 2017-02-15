package com.iotek.fgs.connweb;

import java.io.IOException;
import java.io.InputStream;

public class GetData {
	public static String getData(InputStream is){
		StringBuffer buff = new StringBuffer();
		byte[] c = new byte[1024];
		int length = -1;
		try {
			while((length=is.read(c))!=-1){
				buff.append(new String(c,0,length));
			}
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buff.toString();
	}

}
