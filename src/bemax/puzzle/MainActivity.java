package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	private SurfaceView puzView;
	private Button button;
	private Puzzle puzzle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* ここから入力 */
        puzView = (SurfaceView)findViewById(R.id.puzzle_view);
        button = (Button)findViewById(R.id.reset_button);
        puzzle = new Puzzle(puzView);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		puzzle.shuffle();
	}
}
