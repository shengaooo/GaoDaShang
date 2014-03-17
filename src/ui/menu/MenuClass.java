package ui.menu;

public class MenuClass {


	public static String[] MenuString = new String [] {" 您好，我是高大上，请回复数字选择服务：\n 1: 订餐 \n 2: 用户注册\n "
			+ "3: 查询订餐\n 4: <a href= \"http://www.winjune.com\">高大上网站</a>", //Menu0 
			"请您在回复中写下您的地址： ", //Menu1
			" 服务器出现异常，请您稍后注册", //Menu2
			" 您还不是注册用户，请您先注册", //Menu3
			"您已注册成功，您的地址是： ", //Menu4 
		" 请你选择套餐：\n 1: 霸王餐 \n 2:兰州拉面 "  //Menu5 
		
		};
	
	public static String getMenu( int option ) {
		  return MenuString [option]; 
	}
}
