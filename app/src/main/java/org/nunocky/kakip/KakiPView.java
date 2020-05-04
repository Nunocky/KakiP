package org.nunocky.kakip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class KakiPView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

	private static final String LOGHEADER = "KAKIPVIEW";
	private static final int NUM_OBJECTS = 20;
	private SurfaceHolder mHolder;
	private Thread mThread;
	
	private ArrayList<KakiPObject> mEntityList;
	private KakiPObject mGrabbingEntity;
	
	public KakiPView(Context context) {
		super(context);

		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setFixedSize(getWidth(), getHeight());
		Log.i(LOGHEADER,"new VIEW");
		
		setFocusable(true);
		
		mGrabbingEntity = null;
	}

	// �T�[�t�F�C�X�̍쐬���ɌĂ΂��
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(LOGHEADER,"Thread Start");

		Random rand = new Random();

		mEntityList = new ArrayList<KakiPObject>();

		for (int i=0; i<NUM_OBJECTS; i++) {
			KakiPObject ent = new KakiPObject(getResources(), rand.nextInt(8));
			ent.setX(rand.nextInt(getWidth()));
			ent.setY(rand.nextInt(getHeight()));
			ent.setRotz(rand.nextInt(360));
			mEntityList.add(ent);
		}
		mThread = new Thread(this);
		mThread.start();
	}
	// �T�[�t�F�C�X�̕ύX���ɌĂ΂��
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		
	}

	// �T�[�t�F�C�X�j�����ɌĂ΂��
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThread = null;
	}

	public
	void run()
	{
		Canvas canvas;
		while (mThread != null) {
			//Log.i(LOGHEADER, "thread loop");
			canvas = mHolder.lockCanvas();

			canvas.drawColor(Color.GRAY);

			synchronized(mEntityList) {
				for (KakiPObject ent:mEntityList) {
					canvas.drawBitmap(ent.getBitmap(), ent.getX() - ent.getBitmap().getWidth()/2, ent.getY() - ent.getBitmap().getHeight()/2, null);				
				}
			}
			mHolder.unlockCanvasAndPost(canvas);

			try{
				Thread.sleep(50);
			} catch (Exception e) {
			}
		}
	}

	private void onPanelPress(MotionEvent event)
	{
		synchronized(mEntityList) {
			// �^�b�`�����ʒu�ɂ���G���e�B�e�B�����ޏ���
			mGrabbingEntity = null;
			
			for (int i= mEntityList.size()-1; i>=0; i--) {
				KakiPObject ent = mEntityList.get(i);
				if (ent.checkIntersect((int)event.getX(), (int)event.getY()) == true) {
					mGrabbingEntity = ent;
					break;
				}
			}
			
			if(mGrabbingEntity != null) {
				mEntityList.remove(mGrabbingEntity);
				mEntityList.add(mGrabbingEntity);
			}
		}
	}

	private void onPanelRelease(MotionEvent event)
	{
		mGrabbingEntity = null;
	}
	private void onPanelDrag(MotionEvent event)
	{
		if (mGrabbingEntity != null) {
			// ����ł���G���e�B�e�B�𓮂�������
			mGrabbingEntity.setX( (int)event.getX());
			mGrabbingEntity.setY( (int)event.getY());
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if        (event.getAction() == MotionEvent.ACTION_DOWN) {
			onPanelPress(event);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			onPanelRelease(event);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			onPanelDrag(event);
		} 
		return true;
	}
}
