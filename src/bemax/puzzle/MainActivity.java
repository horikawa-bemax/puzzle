package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceView;

public class MainActivity extends Activity {
	private SurfaceView puzView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ここから入力 */
        puzView = (SurfaceView)findViewById(R.id.puzzle_view);
        Puzzle puzzle = new Puzzle(puzView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


}
