package com.lengjiye.clock.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lengjiye.clock.R;
import com.lengjiye.clock.activity.AddClockActivity;
import com.lengjiye.clock.utils.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 快速回答增加图片之后的显示图片的适配器
 * 
 * @author wenjing.liu
 * @version 2014-7-2
 */
public class SponsorEngagementGridViewAdapter extends BaseAdapter {
	private List<String> imgList;
	private Context mContext;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;
	private boolean isFull = false;

	private class DisImgHolder {
		@ViewInject(R.id.im_gridview)
		ImageView disImg;
	}

	public SponsorEngagementGridViewAdapter(Context context, List<String> list) {
		this.mContext = context;
		this.imgList = list;
        bitmapUtils = BitmapHelper.getBitmapUtils(mContext);
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void getBitmapUtils(Context appContext) {

	}

	public int getCount() {
		return imgList.size();
	}

	public Object getItem(int position) {
		return imgList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DisImgHolder holder;
		String pathUrl = imgList.get(position);
		int pathId = 0;
		int listCount = 0;
		// LogUtils.d("pathUrl = " + pathUrl);
		// TODO 如果是第9张图片的时候还要限制一下
		if (convertView == null) {
			holder = new DisImgHolder();
			convertView = inflater.inflate(R.layout.sponsor_gv_item, null);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (DisImgHolder) convertView.getTag();
		}
		if (imgList == null || imgList.size() == 0) {// 没有任何图片
			pathId = R.drawable.add_picture;
			holder.disImg.setImageResource(pathId);
		} else if (imgList != null && imgList.size() == 1) {// 只有一张添加图片的提示
			pathId = Integer.valueOf(imgList.get(0));
			holder.disImg.setImageResource(pathId);
		} else { // 有图片和添加图标的标志
			listCount = imgList.size();
			if (listCount - 1 == position && listCount <= AddClockActivity.MAX_NUM && !isFull) {
				pathId = Integer.valueOf(imgList.get(listCount - 1));
				holder.disImg.setImageResource(pathId);// 不要超过9张图片，如果是9张图片的时候已经没有那张提示图片了
			} else {
				bitmapUtils.display(holder.disImg, pathUrl, BitmapHelper.setBitmapDisplayConfig(mContext.getResources()
						.getDrawable(R.drawable.default_picture),
						mContext.getResources().getDrawable(R.drawable.fail_picture)));
			}
		}
		return convertView;
	}

	public void setFullPicture(boolean isFull) {
		this.isFull = isFull;
	}

	public boolean getFullPicture() {
		return isFull;
	}

	private class CustomBmLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
		public CustomBmLoadCallBack(ImageView imagView) {

		}

		@Override
		public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config,
				BitmapLoadFrom from) {
			// TODO Auto-generated method stub
			super.onLoadCompleted(container, uri, bitmap, config, from);
			// Toast.makeText(context,"Completed = " + uri, 300).show();
		}

		@Override
		public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
			// TODO Auto-generated method stub
			super.onLoadStarted(container, uri, config);
			// Toast.makeText(context, uri, 1000).show();
		}

		@Override
		public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
			// TODO Auto-generated method stub
			super.onLoadFailed(container, uri, drawable);
			// Toast.makeText(context,"Failed = "+ uri, 300).show();
			container.setImageResource(R.drawable.ic_launcher);
		}
	}
}
