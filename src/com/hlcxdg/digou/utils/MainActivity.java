package com.hlcxdg.digou.utils;

import java.util.ArrayList;
import java.util.List;

import com.hlcxdg.digou.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	
	
	private EditText et_text;
	private ImageView iv_select;
	private TextView tv_number;
	private ImageView iv_delete;
	private MyAdapter adapter;

	private List<String> list=new ArrayList<String>();
	private android.widget.ListView listView;
	//Ĭ��Ϊ����ֵpx
	private int pwHeight=300;
	private PopupWindow window; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		initView();
		initListener();
		initData();
		showNumList();
	}
	/**
	 * ��ʼ������
	 */
    private void initData() {
		for (int i = 0; i <15; i++) {
			list.add(90000+i+"");
		}
		ListView();
	}
    //����ListView
    private void ListView(){
    	listView = new android.widget.ListView(this);
    	
    	//���ع�����
    	listView.setVerticalScrollBarEnabled(false);
    	adapter=new MyAdapter();
    	listView.setAdapter(adapter);
    	//����listview�ĵ���¼�
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				et_text.setText(list.get(position));
				window.dismiss();
			}
		});
    }
    
    class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view=View.inflate(MainActivity.this,R.layout.adapter,null);
			tv_number = (TextView) view.findViewById(R.id.tv_number);
			
			tv_number.setText(list.get(position));
		
			return view;
		}
    	
    }
    /**
     * ��ʼ���¼�
     */
	private void initListener() {
		
		iv_select.setOnClickListener(this);
	}
	/**
	 * ��ʼ������
	 */
	private void initView() {
		setContentView(R.layout.activity_main);
		et_text = (EditText) findViewById(R.id.et_text);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
		
	}
	/**
	 * ��ListView��������ʾ���б���
	 * ��һ����������listView
	 * �ڶ���������PopupWindow�Ŀ�ȣ���EditText�Ŀ��һ��
	 * ���������������ù̶��ĸ߶�
	 */
	private void showNumList() {
		if(window==null){
			//����PopupWindow
			window = new PopupWindow(listView,et_text.getWidth(), pwHeight);
		}
		
		window.setFocusable(true);
		//���ñ���ͼƬ
		window.setBackgroundDrawable(new BitmapDrawable());
		//�����ⲿ�����ʧ
		window.setOutsideTouchable(true);
		window.showAsDropDown(et_text, 0, 0);
	}

}














