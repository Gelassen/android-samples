package home.kazakov.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private View content;
	
	private View spinner;
	
	private int shortDuration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crossfire);
		
		content = findViewById(R.id.content);
		spinner = findViewById(R.id.loading_spinner);
		shortDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
		
		crossfire();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void crossfire() {
		content.setVisibility(View.VISIBLE);
		content.setAlpha(0f);
		
		content.animate()
			   .alpha(1f)
			   .setDuration(shortDuration)
			   .setListener(null);
		
		spinner.animate()
			   .alpha(0f)
			   .setDuration(shortDuration)
			   .setListener(new AnimatorListenerAdapter() {

					@Override
					public void onAnimationEnd(Animator animation) {
						spinner.setVisibility(View.GONE);
					}
					   
			   });
	}

}
