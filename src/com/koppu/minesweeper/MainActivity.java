package com.koppu.minesweeper;

import java.util.Random;

import com.koppu.minesweeper.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
/**
 * Main Activity of the application
 * @author vkoppu
 *
 */
public class MainActivity extends Activity {
	
	private Button newgame;
	private Button validate;
	private Button giveup;
	private ButtonAdapter buttonAdapter;
	
	private MineButton[] mButtons; // Holds all the blocks in the grid
	private int totalMines = 10;
	private int totalRows = 8;
	private int totalCols = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		newgame = (Button) findViewById(R.id.newgame);
		newgame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startNewGame();
				
			}
		});
		validate = (Button) findViewById(R.id.validate);
		validate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				validateGame();
			}
		});
		validate.setEnabled(false);
		giveup = (Button) findViewById(R.id.giveup);
		giveup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				revealMines();
			}
		});
		giveup.setEnabled(false);
		buttonAdapter = new ButtonAdapter(this);
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(buttonAdapter);
	    mButtons = buttonAdapter.getMineButtons();
	    
	}

	/**
	 * Reveals the blocks containing mines. And disable those blocks 
	 */
	protected void revealMines() {
		if(!buttonAdapter.isGameOver()){
			for(int a=0;a<totalRows*totalCols;a++){
				if(mButtons[a].hasMine()){
					mButtons[a].showMine(true);
					mButtons[a].setClickable(false);
				}
			}
		}
	}
	/**
	 * Checks whether the game is won or lost. Finishes the game in both the cases
	 */
	protected void validateGame() {
		if(!buttonAdapter.isGameOver()){
			for(int a=0;a<totalRows*totalCols;a++){
				if(mButtons[a].isClickable() && !mButtons[a].hasMine()){
					buttonAdapter.showMines(false);
					return;
				}
			}
			buttonAdapter.showMines(true);
		}
	}
	/**
	 * Starts a new game
	 */
	protected void startNewGame() {
		resetButtons();
		setButtonValues();
		buttonAdapter.setGameOver(false);
		validate.setEnabled(true);
		giveup.setEnabled(true);
		((TextView)findViewById(R.id.count)).setText(Integer.toString((totalRows*totalCols)));
	}
	/**
	 * Resets all the blocks in the grid
	 */
	private void resetButtons() {
		for(int a=0;a<totalRows*totalCols;a++){
			mButtons[a].initFields();
		}
	}
	/**
	 * Randomly sets the mines in the grid. Then calculates the values for each block
	 */
	private void setButtonValues() {
		Random rand = new Random();
		int row, col;
		int i=0;
		while(i<totalMines){
			row = rand.nextInt(totalRows);
			col = rand.nextInt(totalCols);
			if(!mButtons[((row*8)+col)].hasMine()){
				mButtons[((row*8)+col)].setMine(true);
				i++;
			}
		}
		//Counting the mines around the block and setValue
		for(row=0;row<totalRows;row++){
			for(col=0;col<totalCols;col++){
				if(!mButtons[((row*8)+col)].hasMine()){
					int value=0;
					int startRow = Math.max(0, row-1);
					int endRow = Math.min(totalRows-1, row+1);
					int startCol = Math.max(0, col-1);
					int endCol = Math.min(totalCols-1, col+1);
					for(int p=startRow;p<=endRow;p++){
						for(int q=startCol;q<=endCol;q++){
							if(mButtons[((p*8)+q)].hasMine()){
								value++;
							}
						}
					}
					mButtons[((row*8)+col)].setValue(value);
				}
			}
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
