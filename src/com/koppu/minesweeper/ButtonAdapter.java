package com.koppu.minesweeper;


import com.koppu.minesweeper.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
/**
 * Adaptor for the Grid. Holds all the blocks in the grid
 * @author vkoppu
 *
 */
public class ButtonAdapter extends BaseAdapter {

	private Context mContext;
	protected static int TOTAL_ROWS = 8;
	protected static int TOTAL_COLS = 8;
	private MineButton[] mButtons = new MineButton[TOTAL_ROWS*TOTAL_COLS];
	protected boolean gameOver;
	private float density;
	private TextView count;

    public ButtonAdapter(Context c) {
        mContext = c;
        setGameOver(true);
        density = mContext.getResources().getDisplayMetrics().density;
        count = (TextView) ((Activity)mContext).findViewById(R.id.count);
    }

    public int getCount() {
        return TOTAL_ROWS*TOTAL_COLS;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    public MineButton[] getMineButtons(){
    	return mButtons;
    }

    //Picked from the internet so don't touch its working  
    public View getView(int position, View convertView, ViewGroup parent) {
        MineButton mButton;
        if (convertView == null) {  
        	mButton = new MineButton(mContext);
        	mButton.setPos(position);
        	mButton.setOnClickListener(new MineButtonClickListener());
        	mButton.setLayoutParams(new GridView.LayoutParams((int)(30*density), (int)(30*density)));
        	mButton.setPadding(2, 2, 2, 2);
        	mButton.setClickable(false);
        	mButton.setBackgroundResource(R.drawable.grey);
        } else {
        	mButton = (MineButton) convertView;
        }
        if(mButtons[position] == null){
        	mButtons[position] = mButton;
        }
        return mButton;
    }
    
    /**
     * Taking this as a subclass as it is only used inside the ButtonAdapter
     * Click listener for the blocks
     * @author vkoppu
     * 
     */
    public class MineButtonClickListener implements OnClickListener {

    	@Override
    	public void onClick(View v) {
    		MineButton mb = (MineButton) v;
    		if(!isGameOver() && mb.isClickable()){
    			if(mb.hasMine()){
    				showMines(false);
    			}else{
    				int pos = mb.getPos();
    				int row = pos/TOTAL_COLS;
    				int col = pos%TOTAL_COLS;
    				openButtons(row,col);
    			}
    		}
    		
    	}

    }

    /**
     * Recursively opens the blocks.
     * The main logic sits here
     */
	public void openButtons(int row, int col) {
		//exit condition -> if it is already clicked or if there is a mine
		if(row < 0 || row >= TOTAL_ROWS || col < 0 || col >= TOTAL_COLS || 
				!mButtons[((row*8)+col)].isClickable() || mButtons[((row*8)+col)].hasMine()){
			return;
		}
		
		mButtons[((row*8)+col)].showValue();
		//Update the counter on the side 
		count.setText(Integer.toString(((Integer.parseInt((String) count.getText()))-1)));
		if(mButtons[((row*8)+col)].getValue() == 0){
			for(int p = row-1;p<=row+1;p++){
				for(int q=col-1;q<=col+1;q++){
					openButtons(p, q);
				}
			}
		}
		
	}
	/**
	 * Finishes the game by showing the mines in both the cases - win or loss
	 * 
	 */
	public void showMines(boolean win) {
		gameOver = true;
		for(int a=0;a<TOTAL_ROWS*TOTAL_COLS;a++){
			if(mButtons[a].hasMine()){
				mButtons[a].showMine(win);
			}
			//
			mButtons[a].setClickable(false);
		}
		if(win){
			count.setText("Game Won");
		}else{
			count.setText("Game Lost");
		}
	}

	/**
	 * GameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * gameOver the gameOver to set
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}
