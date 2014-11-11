package human.eternal.fragment;

import human.eternal.atdiet.R;
import human.eternal.config.ConfigText;
import human.eternal.data.GlobelData;
import human.eternal.utils.AppUtils;
import human.eternal.utils.GetCookie;
import human.eternal.utils.MyHandler;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 登陆控制
 * @author GzhuLee@163.com
 * @createtime 2014-11-05
 */
public class PlaceholderFragment extends Fragment implements OnClickListener{

	protected static final String TAG = "PlaceholderFragment";
	private Button btnLogin;
	private EditText etUserName,etUserPwd;
	private int bgTag = 0;
	private MyHandler myHandler;
	private ProgressBar pBar;
	private RelativeLayout rlayLogin,rlayHome;
	private TextView tvLog,tvCbg;
	private View rootView;
	
	public PlaceholderFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_at_diet, container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		tvLog = (TextView)rootView.findViewById(R.id.tv_log);
		tvCbg = (TextView)rootView.findViewById(R.id.tv_cbg);
		btnLogin = (Button)rootView.findViewById(R.id.btn_login);
		rlayLogin = (RelativeLayout)rootView.findViewById(R.id.login_rlay);
		rlayHome = (RelativeLayout)rootView.findViewById(R.id.rlay_home);
		etUserName = (EditText)rootView.findViewById(R.id.et_login_name);
		etUserPwd = (EditText)rootView.findViewById(R.id.et_login_pwd);
		pBar = (ProgressBar)rootView.findViewById(R.id.pbar);
		myHandler = new MyHandler(PlaceholderFragment.this);
		rlayLogin.setVisibility(View.GONE);
		tvLog.setOnClickListener(this);
		tvCbg.setOnClickListener(this);
		btnLogin.setOnClickListener(this);		
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_log:loginViewChange();break;
		case R.id.tv_cbg:changeBg();break;
		case R.id.btn_login:userLogin();break;
		}		
	}

	//得到登录后取得的Cookie
	private Runnable loginRunnable(final String un,final String pwd) {
		return new Runnable() {			
			@Override
			public void run() {
				String mCookie = GetCookie.getInstance().getLoginCookie(un, pwd);
				if (mCookie.equals("")) {
					Message msg = new Message();
					msg.what = MyHandler.MSG_MSG;
					msg.obj = ConfigText.LOGIN_FAILURE;
					myHandler.sendMessage(msg);					
				} else {
					GlobelData.getInstance().setCookie(mCookie);
					GlobelData.getInstance().setLogin(true);
					Message msg = new Message();
					msg.what = MyHandler.MSG_MSG;
					msg.obj = ConfigText.LOGIN_SUCCESS;
					myHandler.sendMessage(msg);
				}
			}
		};
	}
	
	// 10000 成功; 20000 用户名或密码错误; 30000 登陆过期，请重新登录
	private void userLogin(){
		loginViewChange();
		pBar.setVisibility(View.VISIBLE);
		String userName = etUserName.getText().toString().replace(" ", "").trim();
		String userPwd = etUserPwd.getText().toString().replace(" ", "").trim();
		if (userName.equals("")||userPwd.equals("")) {
			AppUtils.toastShow(ConfigText.LOGIN_ERROR);
			return;
		}
		new Thread(loginRunnable(userName,userPwd)).start();
	}

	//改变登陆布局的显示状态
	private void loginViewChange() {
		if (rlayLogin.getVisibility()==View.GONE) {
			rlayLogin.setVisibility(View.VISIBLE);	
		}else{
			rlayLogin.setVisibility(View.GONE);				
		}
	}
	
	//显示提示消息
	public void showMsg(Object obj){
		if (GlobelData.getInstance().isLogin()) tvLog.setText(ConfigText.LOGOUT);
		AppUtils.toastShow((String)obj);
		pBar.setVisibility(View.GONE);
	}

	//循环切换主界面背景
	public void changeBg() {
		bgTag++;
		if(bgTag==3)bgTag=0;
		if (bgTag==0) {
			rlayHome.setBackgroundResource(R.drawable.main_bg_00);
		} else if (bgTag==1){
			rlayHome.setBackgroundResource(R.drawable.main_bg_01);
		} else if (bgTag==2){
			rlayHome.setBackgroundResource(R.drawable.main_bg_02);
		}
	}
}
