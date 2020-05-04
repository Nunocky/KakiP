package org.nunocky.kakip;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class KakiPActivity extends Activity {
	
	private KakiPView mKakiPView;
	private TickHandler mTickHandler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        
        mKakiPView = new KakiPView(this);
        setContentView(mKakiPView);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	mTickHandler = new TickHandler();
    	mTickHandler.sleep(0);	
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	mTickHandler = null;
    }
    
    public class TickHandler extends Handler {
    	@Override
    	public void handleMessage(Message msg) {
    		mKakiPView.invalidate();
    		if (mTickHandler != null)
    			mTickHandler.sleep(100);
    	}
    	
    	public void sleep(long delayMills) {
    		removeMessages(0);
    		sendMessageDelayed(obtainMessage(0), delayMills);
    	}
    }
}