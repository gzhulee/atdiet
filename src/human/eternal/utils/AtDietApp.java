package human.eternal.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class AtDietApp extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();

	private static AtDietApp instance;
	private AtDietApp() {}
	// ����ģʽ�л�ȡΨһ��ExitAppʵ��
	public synchronized static AtDietApp getInstance() {
		if (null == instance) instance = new AtDietApp();
		return instance;
	}

	// ���Activity��������
	public void addActivity(Activity activity) {
		if (!activityList.contains(activity))activityList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : activityList) 
				if (activity != null) activity.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}	

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
}
