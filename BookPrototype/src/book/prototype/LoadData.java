package book.prototype;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;

import com.bookprototype.R;

public class LoadData {
	private Context context;

	// private Map<String, List<String>> data = new TreeMap<String,
	// List<String>>();
	public static Map<String, List<String>> data = new TreeMap<String, List<String>>();

	public LoadData(Context context) {
		this.context = context;
	}

	public void loadData() {
		if (data.isEmpty()) {
			String displayText = "";
			try {
				InputStream fileStream = context.getResources()
						.openRawResource(R.raw.data);
				int fileLen = fileStream.available();
				// Read the entire resource into a local byte buffer.
				byte[] fileBuffer = new byte[fileLen];
				fileStream.read(fileBuffer);
				fileStream.close();
				displayText = new String(fileBuffer);

				String[] splitbyChapter = displayText.trim().split("###");

				for (String content : splitbyChapter) {
					String[] splitbyContent = content.trim().split("##");
					String chapter = splitbyContent[0].trim();
					if (chapter.length() > 0) {
						List<String> chapterContent = new ArrayList<String>();
						for (int i = 1; i < splitbyContent.length; i++) {
							chapterContent.add(splitbyContent[i]);
						}
						data.put(chapter, chapterContent);
					}
				}
			} catch (IOException e) {
				// exception handling
			}

		}
	}

}
