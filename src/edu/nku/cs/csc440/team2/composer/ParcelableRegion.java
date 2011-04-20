package edu.nku.cs.csc440.team2.composer;

import edu.nku.cs.csc440.team2.message.Region;
import edu.nku.cs.csc440.team2.message.SmilDimension;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A ParcelableRegion mimics the functionality of message.Region but can be
 * saved to and restored from a Parcel.
 * 
 * @author William Knauer <knauerw1@nku.edu>
 * @version 2011.0416
 */
public class ParcelableRegion implements Parcelable {
	/** The Region's bounds */
	Rect mBounds;
	
	/** The Region's z-index relative to other Regions */
	int mZindex;

	/** Used to generate instances of this class from a Parcel */
	public static final Parcelable.Creator<ParcelableRegion> CREATOR = new Parcelable.Creator<ParcelableRegion>() {

		@Override
		public ParcelableRegion createFromParcel(Parcel source) {
			return new ParcelableRegion(source);
		}

		@Override
		public ParcelableRegion[] newArray(int size) {
			return new ParcelableRegion[size];
		}

	};

	/**
	 * Class constructor. (No-arg)
	 */
	public ParcelableRegion() {
		mBounds = new Rect();
		mZindex = 999;
	}

	/**
	 * Class constructor.
	 * 
	 * @param l The left bound of in px
	 * @param t The top bound of in px
	 * @param r The right bound in px
	 * @param b The bottom bound in px
	 * @param zIndex The relative z-index
	 */
	public ParcelableRegion(int l, int t, int r, int b, int zIndex) {
		mBounds = new Rect(l, t, r, b);
		mZindex = 999;
	}

	/**
	 * Class constructor for creating from a Parcel.
	 * 
	 * @param in The Parcel to construct from.
	 */
	public ParcelableRegion(Parcel in) {
		mBounds = in.readParcelable(Rect.class.getClassLoader());
		mZindex = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Rect getBounds() {
		return mBounds;
	}

	public int getZindex() {
		return mZindex;
	}

	public void setBounds(Rect r) {
		mBounds.set(r);
	}

	public void setZindex(int zindex) {
		mZindex = zindex;
	}

	/**
	 * Generates a Region from this ParcelableRegion
	 * 
	 * @return Returns the generated Region.
	 */
	public Region toRegion() {
		Region result = new Region(
				new SmilDimension(getBounds().width(), getBounds().height()));
		result.setOrigin(
				new SmilDimension(getBounds().left, getBounds().top));
		result.setZindex(getZindex());
		return result;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mBounds, 0);
		dest.writeInt(mZindex);
	}
}