package human.eternal.data;
/**
 * 存储全局的数据资源
 * @author Gzhulee@163.com
 * @createtime 2014-11-06
 */
public class GlobelData {

	private static GlobelData instance = new GlobelData();
	private GlobelData(){};
	public static GlobelData getInstance(){return instance;}
	
	private String cookie ="";
	private boolean isLogin = false;
	
	public String getCookie(){
		return this.cookie;
	}
	
	public void setCookie(String cookie){
		if(cookie!=null)this.cookie = cookie;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

}
