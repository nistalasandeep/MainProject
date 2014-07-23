package book.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bookprototype.R;

public class ChapterView extends Activity {
	private List<String> chapters = new ArrayList<String>();
	private ListView chaptersListView;
	private Adapter adapter;
	public static String chapter = "";
	public static Locale language;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		language = Locale.ENGLISH;
		chaptersListView = (ListView) findViewById(R.id.chaptersListView);
		adapter = new Adapter(chapters);
		chaptersListView.setAdapter(adapter);
		LoadData loadData = new LoadData(this);
		loadData.loadData();
		loadStativData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_layout, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {
		    case R.id.English:
		    	language = Locale.ENGLISH;
		     
		      return true;
		    case R.id.German:
		    	
		    	language = Locale.GERMAN;
		      return true;
		    case R.id.French:
		    	
		    	language = Locale.FRENCH;
			      return true;
		    default:
		      return super.onOptionsItemSelected(item);
		  }
		}

	private void loadStativData() {
		Set<String> keys = LoadData.data.keySet();
		for(String chapter:keys){
			chapters.add(chapter.trim());
		}
//		chapters.add("Chapter 1");
//		chapters.add("Chapter 2");
//		chapters.add("Chapter 3");
//		chapters.add("Chapter 4");
//		chapters.add("Chapter 5");
//		chapters.add("Chapter 6");
//		chapters.add("Chapter 7");
//		chapters.add("Chapter 8");
//		chapters.add("Chapter 9");
//		chapters.add("Chapter 10");
	}

	class Adapter extends BaseAdapter {
		private List<String> chapterItems;

		public Adapter(List<String> chapters) {
			chapterItems = chapters;
		}

		@Override
		public int getCount() {
			return chapterItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		// cityofangel.ttf
		// darkisthenight.ttf
		// donovanquidaw.ttf
		// karlwright.ttf
		// raymorganstyle.ttf
		// roseskingdom.ttf
		// shadowboxing.ttf
		// snowforsanta.ttf
		// youngshark.ttf
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflte = LayoutInflater.from(ChapterView.this); // 1
			View theInflatedView = inflte.inflate(R.layout.chapter_row, null);
			TextView chapterNamesTextView = (TextView) theInflatedView
					.findViewById(R.id.rowTextView);
			Typeface font = null;
//			if (position == 0) {
//				font = Typeface.createFromAsset(getAssets(), "cheesecake.ttf");
//			} else if (position == 1) {
//				font = Typeface.createFromAsset(getAssets(), "cityofangel.ttf");
//			} else if (position == 2) {
				font = Typeface.createFromAsset(getAssets(),
						"darkisthenight.ttf");
//			} else if (position == 3) {
//				font = Typeface.createFromAsset(getAssets(),
//						"donovanquidaw.ttf");
//			} else if (position == 4) {
//				font = Typeface.createFromAsset(getAssets(), "karlwright.ttf");
//			} else if (position == 5) {
//				font = Typeface.createFromAsset(getAssets(),
//						"raymorganstyle.ttf");
//			} else if (position == 6) {
//				font = Typeface
//						.createFromAsset(getAssets(), "roseskingdom.ttf");
//			} else if (position == 7) {
//				font = Typeface
//						.createFromAsset(getAssets(), "shadowboxing.ttf");
//			} else if (position == 8) {
//				font = Typeface
//						.createFromAsset(getAssets(), "snowforsanta.ttf");
//			} else if (position == 9) {
//				font = Typeface.createFromAsset(getAssets(), "youngshark.ttf");
//			}

			chapterNamesTextView.setText(chapterItems.get(position).toString());
			chapterNamesTextView.setTypeface(font);
			theInflatedView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ContentViewActivity.lineNo = 0;
					chapter = chapterItems.get(position).toString();
					Intent intent = new Intent(ChapterView.this,
							ContentViewActivity.class);
					startActivity(intent);
//					finish();
				}
			});
			return theInflatedView;
		}

	}

}
