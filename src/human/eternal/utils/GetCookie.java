package human.eternal.utils;

import human.eternal.config.ConfigUrl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/**
 * 得到链接后产生的Cookie
 * @author Gzhulee@163.com
 * @createtime 2014-11-06
 */
public class GetCookie {
	
	private static final String TAG = "GetCookie";
	private static GetCookie instance = new GetCookie();
	private GetCookie(){};
	public static GetCookie getInstance(){return instance;}

	/**
	 * 得到登陆者的cookie，即cookie绑定当前的登陆用户
	 */
	public String getLoginCookie(String uName, String pWord) {
		String userCookie ="";
		HttpPost request = new HttpPost(ConfigUrl.DOMAIN + "/");
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    BasicNameValuePair un = new BasicNameValuePair("username", uName);
	    BasicNameValuePair  pw = new BasicNameValuePair("password", pWord);
	    parameters.add(un);
	    parameters.add(pw);
	    
	    HttpEntity en;
	    try {
	    	en = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
	    	request.setEntity(en);
	    } catch (UnsupportedEncodingException e1) {
	    	e1.printStackTrace();
	    }
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(request);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
				try {
					JSONObject jsObj = new JSONObject(result);
					if (jsObj.getString("code").equals("10000")) {
						userCookie = getCookie(httpClient);
						Log.i(TAG, jsObj.getString("sessionid"));
					} else {
						Log.i(TAG, jsObj.getString("message"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
			Log.i(TAG, "ClientProtocolException-->" + e.toString());
		} catch (IOException e) {
			Log.i(TAG, "IOException-->" + e.toString());
		}
		return userCookie;		
	}
	
	/**
	 * 得到链接后产生的Cookie
	 */
	public String getRegCookie() {
		String myCookie ="";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(ConfigUrl.DOMAIN + "/");
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
				myCookie = getCookie(httpClient);
		} catch (ClientProtocolException e) {
			Log.i(TAG, "ClientProtocolException-->" + e.toString());
		} catch (IOException e) {
			Log.i(TAG, "IOException-->" + e.toString());
		}
		return myCookie;		
	}

	/**
	 * 获取标准 Cookie ，并存储
	 * @param httpClient
	 */
	private String getCookie(DefaultHttpClient httpClient) {
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			//Log.i(TAG, "cookieName = "+ cookieName +" , cookieValue = "+cookieValue);
			if (!TextUtils.isEmpty(cookieName)&& !TextUtils.isEmpty(cookieValue)) {
				sb.append(cookieValue);
			}
		}
		Log.e("cookie", sb.toString());
		return sb.toString();
	}

}
