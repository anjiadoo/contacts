package main;

import frame.LoginFrame;
/**
 * main方法
 * 
 * @author anjiadoo
 *
 */
public class Start {
	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LoginFrame frame = new LoginFrame();// 登录窗体
		frame.setVisible(true);             // 窗体可见
	}
}
