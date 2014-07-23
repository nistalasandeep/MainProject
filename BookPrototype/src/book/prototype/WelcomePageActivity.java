package book.prototype;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bookprototype.R;

public class WelcomePageActivity extends Activity {
	private ProgressDialog progDailog;
	private Button continueButton;
	private TextView aboutTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		continueButton = (Button) findViewById(R.id.continueBT);
		aboutTextView = (TextView) findViewById(R.id.aboutText);

		continueButton.setVisibility(View.INVISIBLE);
		aboutTextView.setVisibility(View.INVISIBLE);
		progDailog = new ProgressDialog(WelcomePageActivity.this);
		progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDailog.setCancelable(true);
		progDailog.show();
		String aboutText = loadAbout();
		aboutTextView.setText(aboutText);

		new CountDownTimer(3000, 1000) {

			public void onTick(long millisUntilFinished) {
				progDailog
						.setMessage("Loading..." + millisUntilFinished / 1000);
			}

			public void onFinish() {
				progDailog.dismiss();
				continueButton.setVisibility(View.VISIBLE);
				aboutTextView.setVisibility(View.VISIBLE);
			}
		}.start();
		Typeface font = Typeface.createFromAsset(getAssets(), "darkisthenight.ttf");
		aboutTextView.setTypeface(font);
		continueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomePageActivity.this,ChapterView.class);
				startActivity(intent);
				
			}
		});
	};

	private String loadAbout() {
		String aboutText = "";
		try {
			InputStream fileStream = WelcomePageActivity.this.getResources()
					.openRawResource(R.raw.abtt);
			int fileLen = fileStream.available();
			// Read the entire resource into a local byte buffer.
			byte[] fileBuffer = new byte[fileLen];
			fileStream.read(fileBuffer);
			fileStream.close();

			aboutText = new String(fileBuffer);
		} catch (IOException e) {
			// exception handling
		}
		return aboutText;
	}
}
