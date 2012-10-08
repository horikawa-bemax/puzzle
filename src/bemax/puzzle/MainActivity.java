package bemax.puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private SurfaceView puzView;
	private Resources res;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* ここから入力 */
        puzView = (SurfaceView)findViewById(R.id.puzzle_view);
        res = puzView.getResources();
        Puzzle puzzle = new Puzzle(puzView);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
