/**
 * 
 */
package human.eternal.utils;

import human.eternal.fragment.PlaceholderFragment;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

/**
 * @author GzhuLee@163.com
 *
 */
public class MyHandler extends Handler {

	public static final int MSG_FAILURE = 0;
	public static final int MSG_OK = 1;
	public static final int MSG_ADD = 2;
	public static final int MSG_MSG = 3;
	WeakReference<Object> mObj;
	private Object object;

	public MyHandler(Object obj) {
		this.object = obj;
		mObj = new WeakReference<Object>(obj);
	}

	@Override
	public void handleMessage(Message msg) {
		if (this.object instanceof PlaceholderFragment){
			PlaceholderFragment theMfg = (PlaceholderFragment)mObj.get();
			if (theMfg==null) return;
			switch (msg.what) {
				//case MSG_ADD:theMfg.changePMsg(msg.arg1);break;
				case MSG_MSG:theMfg.showMsg(msg.obj);break;
			}			
		}
	}
}
