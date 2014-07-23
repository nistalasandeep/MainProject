package book.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import book.prototype.SimpleGestureFilter.SimpleGestureListener;

import com.bookprototype.R;

public class ContentViewActivity extends Activity implements
		SimpleGestureListener, TextToSpeech.OnInitListener {
	private SimpleGestureFilter detector;
	public static int lineNo = 0;
	private TextView lineTextView;
	private static int transitionType = 0;
	private TextToSpeech tts;
	private Button speakButton;
	int result;// = tts.setLanguage(Locale.ENGLISH);
	private List<String> content = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contentview);
		tts = new TextToSpeech(this, this);
		content = LoadData.data.get(ChapterView.chapter);

		speakButton = (Button) findViewById(R.id.speakButton);
		Button speakButton = (Button) findViewById(R.id.speakButton);
		RelativeLayout touchLayout = (RelativeLayout) findViewById(R.id.swipeLayout);
		result = tts.setLanguage(Locale.ENGLISH);
		speakButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				speakOut();
			}
		});
		if (transitionType == -1) {
			overridePendingTransition(R.anim.left, R.anim.right);
		} else if (transitionType == 1) {
			overridePendingTransition(R.anim.left_to_right,
					R.anim.right_to_left);
		}
		detector = new SimpleGestureFilter(this, this);
		lineTextView = (TextView) findViewById(R.id.contentTextView);

		Typeface font = null;
//		if (lineNo == 0) {
//			font = Typeface.createFromAsset(getAssets(), "cheesecake.ttf");
//		} else if (lineNo == 1) {
//			font = Typeface.createFromAsset(getAssets(), "cityofangel.ttf");
//		} else if (lineNo == 2) {
			font = Typeface.createFromAsset(getAssets(), "darkisthenight.ttf");
//		} else if (lineNo == 3) {
//			font = Typeface.createFromAsset(getAssets(), "donovanquidaw.ttf");
//		} else if (lineNo == 4) {
//			font = Typeface.createFromAsset(getAssets(), "karlwright.ttf");
//		} else if (lineNo == 5) {
//			font = Typeface.createFromAsset(getAssets(), "raymorganstyle.ttf");
//		} else if (lineNo == 6) {
//			font = Typeface.createFromAsset(getAssets(), "roseskingdom.ttf");
//		} else if (lineNo == 7) {
//			font = Typeface.createFromAsset(getAssets(), "shadowboxing.ttf");
//		} else if (lineNo == 8) {
//			font = Typeface.createFromAsset(getAssets(), "snowforsanta.ttf");
//		} else if (lineNo == 9) {
//			font = Typeface.createFromAsset(getAssets(), "youngshark.ttf");
//		}

		touchLayout.setOnTouchListener(new RelativeLayout.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent m) {
				detector.onTouchEvent(m);
				return true;
			}
		});
		 Animation fadeIn = new AlphaAnimation(0, 1);
		 fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
		 fadeIn.setDuration(6000);

//		Animation fadeOut = new AlphaAnimation(1, 0);
//		fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
//		// fadeOut.setStartOffset(000);
//		fadeOut.setDuration(4000);

		AnimationSet animation = new AnimationSet(false); // change to false
		 animation.addAnimation(fadeIn);
//		animation.addAnimation(fadeOut);
		lineTextView.setTypeface(font);
		lineTextView.setTextColor(Color.BLACK);
		
		lineTextView.setAnimation(animation);
		lineTextView.setText(content.get(lineNo));
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onSwipe(int direction) {
		if ((lineNo >= 0) && (lineNo < content.size() - 1)) {
			switch (direction) {

			case SimpleGestureFilter.SWIPE_RIGHT:
				lineNo--;
				transitionType = 1;
				break;
			case SimpleGestureFilter.SWIPE_LEFT:
				lineNo++;
				transitionType = -1;
				break;
			}
			// lineTextView.setText("Line No :" + lineNo);
			// Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(ContentViewActivity.this,
					ContentViewActivity.class);
			startActivity(intent);
			finish();
		}

	}

	@Override
	public void onInit(int status) {

		// TODO Auto-generated method stub

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(ChapterView.language);

			// tts.setPitch(5); // set pitch level

			// tts.setSpeechRate(2); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				speakButton.setEnabled(true);
//				speakOut();
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}

	private void speakOut() {

		tts.speak(lineTextView.getText().toString(), TextToSpeech.QUEUE_FLUSH,
				null);
	}
}
