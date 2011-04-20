package edu.nku.cs.csc440.team2.player;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoPlayer extends SingleInstancePlayer implements
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback,
		MediaPlayer.OnBufferingUpdateListener, 
		MediaPlayer.OnCompletionListener
{
	private MediaPlayer mMediaPlayer;
	private SurfaceView sfView;
	private SurfaceHolder sfHolder;
	private boolean videoRendered = false;
	
	public VideoPlayer(String resource, double begin, double duration)
	{
		this.resourceURL = resource;
		this.start = begin;
		this.duration = duration;
	}

	public void play()
	{
		if( ! this.videoRendered )
		{
			this.render();
		}
		try
		{
			this.mMediaPlayer.start();
		}
		catch(IllegalStateException e)
		{
			e.printStackTrace();
		}
		this.isPlaying = true;
		this.incrementPlaybackTime();
	}
	
	public void pause()
	{
		if( this.isPlaying )
		{
			try
			{
				this.mMediaPlayer.pause();
			}
			catch(IllegalStateException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void seekForward()
	{
		
	}
	
	public void seekBackward()
	{
		
	}
	
	public void render()
	{
		Log.w("Video", "Rendering the video");
		this.layout.post(new Runnable() {
			public void run() {
				//layout.setVisibility(View.VISIBLE);
				//sfView.setMinimumHeight(mMediaPlayer.getVideoHeight());
				//sfView.setMinimumWidth(mMediaPlayer.getVideoWidth());
				sfHolder.setFixedSize(mMediaPlayer.getVideoWidth(), mMediaPlayer.getVideoHeight());
				mMediaPlayer.setDisplay(sfHolder);
				//layout.addView(sfView);
			}
		});
		this.videoRendered = true;
	}
	
	public void unRender()
	{
		if( this.isPlaying )
		{
			this.layout.post(new Runnable() {
				public void run() {
					layout.removeView(sfView);
					try
					{
						mMediaPlayer.stop();
					}
					catch(IllegalStateException e)
					{
						e.printStackTrace();
					}
					//layout.setVisibility(View.INVISIBLE);				
				}
			});
		}
	}
	
	public void prepare()
	{
		this.mMediaPlayer = new MediaPlayer();
		try {
			this.mMediaPlayer.reset();
			this.mMediaPlayer.setDataSource(this.resourceURL);
			this.mMediaPlayer.setOnPreparedListener(this);
			this.subject.notifyBufferingWithoutPause();
			this.mMediaPlayer.prepareAsync();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		this.layout.post(new Runnable() {
			@Override
			public void run() {
				sfView = new SurfaceView(layout.getContext());
				layout.addView(sfView);
				sfHolder = sfView.getHolder();
				sfHolder.addCallback(VideoPlayer.this);
				sfHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				//sfView.setVisibility(View.INVISIBLE);
			}
		});
		this.subject.notifyBufferingWithoutPause();
	}
	
	@Override
	public void onPrepared(MediaPlayer mp)
	{
		this.subject.notifyDoneBufferingWithoutRestart();
		Log.w("Video", "Prepared video");
	}

	
	/*
	 * Surface callbacks from here down
	 * (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.subject.notifyDoneBufferingWithoutRestart();
		Log.w("Video", "Surface is ready for rendering!");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}

	
	/*
	 * MediaPlayer.OnCompletionListner
	 * (non-Javadoc)
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media.MediaPlayer)
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {}

	/*
	 * MediaPlayer.onBufferingUpdateListener
	 * (non-Javadoc)
	 * @see android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate(android.media.MediaPlayer, int)
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {}
	
	
	@Override
	public void reset()
	{
		this.mMediaPlayer.seekTo(0);
		super.reset();
	}
	
}
