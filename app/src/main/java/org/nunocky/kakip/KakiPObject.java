package org.nunocky.kakip;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class KakiPObject {
	private int type;
	private int x, y;
	private float rot_z;
	private int width, height;
	private Bitmap mBitmap;
	
	private static final int COLLISION_W = 92;
	private static final int COLLISION_H = 92;
	
	private static final int bitmap_resources[] = {
		R.drawable.kaki1,
		R.drawable.kaki2,
		R.drawable.kaki3,
		R.drawable.kaki4,
		R.drawable.peanut1,
		R.drawable.peanut2,
		R.drawable.peanut3,
		R.drawable.peanut4,
	};

	static private Bitmap mBitmapBase[] = null;

	public static void initResources(Resources r) {
		if (mBitmapBase != null)
			return;
		
		mBitmapBase = new Bitmap[8];
		mBitmapBase[0] = BitmapFactory.decodeResource(r, bitmap_resources[0]);
		mBitmapBase[1] = BitmapFactory.decodeResource(r, bitmap_resources[1]);
		mBitmapBase[2] = BitmapFactory.decodeResource(r, bitmap_resources[2]);
		mBitmapBase[3] = BitmapFactory.decodeResource(r, bitmap_resources[3]);
		mBitmapBase[4] = BitmapFactory.decodeResource(r, bitmap_resources[4]);
		mBitmapBase[5] = BitmapFactory.decodeResource(r, bitmap_resources[5]);
		mBitmapBase[6] = BitmapFactory.decodeResource(r, bitmap_resources[6]);
		mBitmapBase[7] = BitmapFactory.decodeResource(r, bitmap_resources[7]);
	}


	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public float getRotz() {
		return rot_z;
	}

	public void setRotz(float rot_z) {
		this.rot_z = rot_z;
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postRotate(rot_z);
        
        mBitmap = Bitmap.createBitmap(mBitmapBase[type], 0, 0, mBitmapBase[type].getWidth(), mBitmapBase[type].getHeight(), matrix, true); 

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}
	
	public KakiPObject(Resources r) {
		init(r, 0);
	}

	public KakiPObject(Resources r, int type) {
		init(r, type);
	}
	
	private void init(Resources r, int type) {
		initResources(r);

		this.type = type % bitmap_resources.length;
		x         = 0;
		y         = 0;
		rot_z     = 0;
		mBitmap   = Bitmap.createBitmap(mBitmapBase[this.type]);
		width     = mBitmap.getWidth();
		height    = mBitmap.getHeight();
	}
	
	public boolean checkIntersect(int px, int py) {
		final int lx = x - width/2;
		final int ly = y - height/2;
		if ( (lx < px && px < lx + COLLISION_W ) &&
			 (ly < py && py < ly + COLLISION_H ) )
			return true;
		
		return false;
	}
}
