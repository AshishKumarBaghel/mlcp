package com.tcs.itcsmlcp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class BookSlotSubActivity extends Activity {

	private RadioGroup radioGroup;
	private RadioButton radioButton;  
	private Button btnDisplay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio_button_example);
		ViewGroup vg = (ViewGroup) findViewById(R.id.root);
		Utils.setFontAllView(vg);
		addListenerOnButton();

	}

	public void addListenerOnButton() {

		radioGroup = (RadioGroup) findViewById(R.id.radioGender);
		btnDisplay = (Button) findViewById(R.id.btnDisplay);

		btnDisplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				int selectedId = radioGroup.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				radioButton = (RadioButton) findViewById(selectedId);

				String s=radioButton.getText().toString();
				String vtype;
				if(s.equalsIgnoreCase("Two Wheeler"))
				{
					vtype="2W";
					
				}
				else{
					vtype="4W";
				}
				
				Intent i= new Intent(BookSlotSubActivity.this,MySlotActivity.class);
				i.putExtra("vtype", vtype);
			    startActivity(i);
				
				
				Toast.makeText(BookSlotSubActivity.this,
						radioButton.getText(), Toast.LENGTH_SHORT).show();

			}

		});

	}
}