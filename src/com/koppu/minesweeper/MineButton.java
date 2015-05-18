package com.koppu.minesweeper;

import com.koppu.minesweeper.R;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
/**
 * Represents the block in the grid
 * @author vkoppu
 *
 */
public class MineButton extends Button {
	//Position of the block in the grid.
	private int pos;
	private boolean hasMine;
	private int value;
	
	
	public MineButton(Context context) {
		super(context);
	}
	
	public void initFields(){
		value=-1;
		this.setClickable(true);
		hasMine=false;
		this.setBackgroundResource(R.drawable.grey);
		this.setText("");
	}
	

	public boolean hasMine() {
		return hasMine;
	}


	public void setMine(boolean hasMine) {
		this.hasMine = hasMine;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void showMine(boolean win) {
		this.setText("M");
		if(win){
			this.setTextColor(Color.GREEN);
		}else{
			this.setTextColor(Color.RED);
		}
	}

	public void showValue() {
		this.setClickable(false);
		this.setBackgroundResource(R.drawable.blue);
		if (value != 0)
		{
			this.setText(Integer.toString(value));
			switch (value)
			{
				case 1:
					this.setTextColor(Color.BLUE);
					break;
				case 2:
					this.setTextColor(Color.YELLOW);
					break;
				case 3:
					this.setTextColor(Color.GRAY);
					break;
				case 4:
					this.setTextColor(Color.MAGENTA);
					break;
				case 5:
					this.setTextColor(Color.GREEN);
					break;
				case 6:
					this.setTextColor(Color.CYAN);
					break;
				case 7:
					this.setTextColor(Color.DKGRAY);
					break;
				case 8:
					this.setTextColor(Color.RED);
					break;
				default: 
					break;
			}
		}
	}
}
