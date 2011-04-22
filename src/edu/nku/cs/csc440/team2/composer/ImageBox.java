package edu.nku.cs.csc440.team2.composer;

import edu.nku.cs.csc440.team2.message.Image;
import edu.nku.cs.csc440.team2.message.Media;
import edu.nku.cs.csc460.team2.R;
import android.graphics.Canvas;

/**
 * An AudioBox is a Box that represents an Audio object.
 * 
 * @author William Knauer <knauerw1@nku.edu>
 * @version 2011.0421
 */
public class ImageBox extends Box {
	/**
	 * Class constructor.
	 * 
	 * @param source The source of the Media.
	 * @param begin The absolute begin time of the Media.
	 * @param duration The duration of the Media.
	 * @param region The region of the Media.
	 */
	public ImageBox(String source, int begin, int duration,
			ComposerRegion region) {
		super(source, begin, duration);
		setRegion(region);
		setType('I');
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (getContext() != null) {
			super.draw(canvas,
					getContext().getResources().getColor(R.color.imagebox_bg),
					getContext().getResources().getColor(R.color.imagebox_fg),
					getContext().getResources().getColor(R.color.resize_grip));
		}
	}

	@Override
	public Media toMedia() {
		return new Image(
				((double) getBegin()) / 10.0,
				((double)getEnd()) / 10.0,
				getSource(),
				getRegion().toRegion());
	}
	
}
