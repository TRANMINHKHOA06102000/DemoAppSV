package com.delaroystudios.studentmgt;

import java.security.PublicKey;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.delaroystudios.studentmgt.R;

public class StudentMainActivity extends Activity {
	EditText emsv,ename,emark;
	Button add,update,delete,view,viewall,show;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_main);
		emsv=(EditText)findViewById(R.id.msv);
		ename=(EditText)findViewById(R.id.name);
		emark=(EditText)findViewById(R.id.mark);
		add=(Button)findViewById(R.id.addbtn);
		update=(Button)findViewById(R.id.updatebtn);
		delete=(Button)findViewById(R.id.deletebtn);
		view=(Button)findViewById(R.id.viewbtn);
		viewall=(Button)findViewById(R.id.viewallbtn);
		show=(Button)findViewById(R.id.showbtn);

		
		db=openOrCreateDatabase("Student_Manage", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS student(msv INTEGER,name VARCHAR,mark INTEGER);");
	
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(emsv.getText().toString().trim().length()==0||
			    		   ename.getText().toString().trim().length()==0||
			    		   emark.getText().toString().trim().length()==0)
			    		{
			    			showMessage("Lỗi", "Bạn cần nhập đủ thông tin");
			    			return;
			    		}
			    		db.execSQL("INSERT INTO student VALUES('"+emsv.getText()+"','"+ename.getText()+
			    				   "','"+emark.getText()+"');");
			    		showMessage("Success", "Thêm thành công");
			    		clearText();
			}
		});
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(emsv.getText().toString().trim().length()==0)
	    		{
	    			showMessage("Lỗi", "Bạn cần nhập Mã sinh viên!");
	    			return;
	    		}
	    		Cursor c=db.rawQuery("SELECT * FROM student WHERE msv='"+emsv.getText()+"'", null);
	    		if(c.moveToFirst())
	    		{
	    			db.execSQL("DELETE FROM student WHERE msv='"+emsv.getText()+"'");
	    			showMessage("Success", "Xóa thành công");
	    		}
	    		else
	    		{
	    			showMessage("Lỗi", "Mã sinh viên không hợp lệ!");
	    		}
	    		clearText();
			}
		});
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(emsv.getText().toString().trim().length()==0)
	    		{
	    			showMessage("Lỗi", "Bạn cần nhập Mã sinh viên!");
	    			return;
	    		}
	    		Cursor c=db.rawQuery("SELECT * FROM student WHERE msv='"+emsv.getText()+"'", null);
	    		if(c.moveToFirst())
	    		{
	    			db.execSQL("UPDATE student SET name='"+ename.getText()+"',mark='"+emark.getText()+
	    					"' WHERE msv='"+emsv.getText()+"'");
	    			showMessage("Success", "Sửa thành công");
	    		}
	    		else
	    		{
	    			showMessage("Lỗi", "Mã sinh viên không hợp lệ!");
	    		}
	    		clearText();
			}
		});
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(emsv.getText().toString().trim().length()==0)
	    		{
	    			showMessage("Lỗi", "Bạn cần nhập Mã sinh viên!");
	    			return;
	    		}
	    		Cursor c=db.rawQuery("SELECT * FROM student WHERE msv='"+emsv.getText()+"'", null);
	    		if(c.moveToFirst())
	    		{
	    			ename.setText(c.getString(1));
	    			emark.setText(c.getString(2));
	    		}
	    		else
	    		{
	    			showMessage("Lỗi", "Mã sinh viên không hợp lệ!");
	    			clearText();
	    		}
			}
		});
		viewall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cursor c=db.rawQuery("SELECT * FROM student", null);
	    		if(c.getCount()==0)
	    		{
	    			showMessage("Lỗi", "Không tìm thấy");
	    			return;
	    		}
	    		StringBuffer buffer=new StringBuffer();
	    		while(c.moveToNext())
	    		{
	    			buffer.append("Mã sinh viên: "+c.getString(0)+"\n");
	    			buffer.append("Họ và Tên sinh viên: "+c.getString(1)+"\n");
	    			buffer.append("Điểm: "+c.getString(2)+"\n\n");
	    		}
	    		showMessage("Kết quả", buffer.toString());
			}
		});
		show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMessage("Quản lý sinh viên ", "Write by TRAN MINH KHOA");
			}
		});
		
	}
	public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
    public void clearText()
    {
    	emsv.setText("");
    	ename.setText("");
    	emark.setText("");
    	emsv.requestFocus();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_main, menu);
		return true;
	}

}
