package bemax.puzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ModeSelectActivity extends Activity implements OnItemClickListener{
	private ListView listView;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* レイアウトを適用 */
		setContentView(R.layout.option_menu);

		/* リストビューを取得 */
		listView = (ListView)findViewById(R.id.mode_list);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapter.add("3x3");
		adapter.add("4x4");
		adapter.add("5x5");

		listView.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch(position){
		case 0:

		}
	}

}
