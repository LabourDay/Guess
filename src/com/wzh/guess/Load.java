package com.wzh.guess;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Load extends Activity {
	private ProgressBar bar=null;													//���������
	private TextView info=null;														//������ʾ�ı���Ϣ
	
	private String nm;																//����String�������˻�ȡ�����
	private String pi;																//����String�������˻�ȡ���id
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);												//ȡ�����
		this.bar=(ProgressBar)findViewById(R.id.bar);								//���������
		this.info=(TextView)findViewById(R.id.info);								//�ı����
		ChildUpdate child=new ChildUpdate();										//���������
		child.execute(100);															//Ϊ����ʱ��
		
		Intent intent=getIntent();
		this.nm=intent.getStringExtra("name");										//��ȡ�����
		this.pi=intent.getStringExtra("id");										//��ȡ���id
		//System.out.println(nm);
		//System.out.println(pi);
	}
	
	
	private class ChildUpdate extends AsyncTask<Integer, Integer, String>{

		@Override
		protected String doInBackground(Integer... params) {						//�����̨����
			for(int x=0;x<100;x++){													//�������ۼ�
				Load.this.bar.setProgress(x);										//���ý���
				this.publishProgress(x);											//����ÿ�θ�������
				try{
					Thread.sleep(params[0]);										//�ӻ�ִ��
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			return "�������";															//����ִ�н��
		}

		@Override
		protected void onPostExecute(String result) {								//����ִ�к�ִ��
			Load.this.info.setText(result);											//�����ı�
			String str=info.getText().toString();									//��ȡ�ı�����	
			if(str.equals("�������")){												//����ı�����Ϊ��������ɡ�
				Intent intent=new Intent();				//ʵ����Intent
				intent.putExtra("playername", nm);
				intent.putExtra("playerid", pi);
				intent.setClass(Load.this,ChooseMode.class);
				startActivity(intent);												//����Activity
				Load.this.finish();													//���ٲ���
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {						//ÿ�θ��º����ֵ
			Load.this.info.setText(String.valueOf(progress[0])+"%");				//�����ı���Ϣ
		}
		
	}
}
