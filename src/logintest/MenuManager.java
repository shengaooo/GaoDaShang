package logintest;

import pojo.AccessToken;
import pojo.Button;
import pojo.CommonButton;
import pojo.ComplexButton;
import pojo.Menu;
import util.WeixinUtil;

public class MenuManager {
//    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
    
    public static void main(String[] args) {  
    	
        String appId = "000000000000000000";  
        String appSecret = "00000000000000000000000000000000";  
  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
  
        if (null != at) {  
            //  调用接口创建菜单      
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
  
            // ????????   
            if (0 == result)  {
//              log.info("菜单创建成功!");             	
            }
            else {
//              log.info("菜单创建失败，错误码：" + result);            	
            }
        }  
    }  
  
    /** 
     * ?????? 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("套餐1： 霸王餐 ");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("套餐2： 兰州拉面 ");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("套餐3： 都城市");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("订座：2 人");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("订座：3 人");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("订座：4 人");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("订座：4 人");  
        btn24.setType("click");  
        btn24.setKey("24");  
  

	  
	        ComplexButton mainBtn1 = new ComplexButton();  
	        mainBtn1.setName("套餐");  
	        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13});  
	  
	        ComplexButton mainBtn2 = new ComplexButton();  
	        mainBtn2.setName("订座");  
	        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24 });  
	  
	  
	        /** 
	         * ?????xiaoqrobot???????,?????????????<br> 
	         *  
	         * ?????????????????,menu???????<br> 
	         * ??,??????????“????”,????“????”,??menu??????:<br> 
	         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
	         */  
	        Menu menu = new Menu();  
	        menu.setButton(new Button[] { mainBtn1, mainBtn2});  
	  
	        return menu;  
	    }  

}
