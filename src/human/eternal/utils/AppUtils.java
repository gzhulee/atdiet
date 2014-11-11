package human.eternal.utils;

import android.content.Context;
import android.widget.Toast;

public class AppUtils {

	public static Context myContext = null;
	
	public static void toastShow(String msg){
		if(myContext!=null)Toast.makeText(myContext, msg, Toast.LENGTH_SHORT).show();
	}
}
