package ru.noties.filldrawable.sample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ru.noties.filldrawable.FillDrawable;
import ru.noties.filldrawable.FillImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<FillDrawable> drawables = createDrawables();
        final List<ImageView> imageViews = Arrays.asList(
                (ImageView) findViewById(R.id.image_1),
                (ImageView) findViewById(R.id.image_2)
        );

        for (int i = 0, length = Math.min(drawables.size(), imageViews.size()); i < length; i++) {
            imageViews.get(i).setImageDrawable(drawables.get(i));
        }

        final FillImageView fillImageView1 = (FillImageView) findViewById(R.id.fill_image_view_1);
        final FillImageView fillImageView2 = (FillImageView) findViewById(R.id.fill_image_view_2);
        drawables.add(0, fillImageView2.getFillDrawable());
        drawables.add(0, fillImageView1.getFillDrawable());

        final Handler handler = new Handler();
        handler.post(new FakeProgress(handler, new FakeProgress.OnProgressChange() {
            @Override
            public void onProgressChange(float progress) {
                for (FillDrawable drawable: drawables) {
                    drawable.setFillPercent(progress);
                }
            }
        }));
    }

    private List<FillDrawable> createDrawables() {

        final Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_android);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        final int alpha = 125;

        final int rightColor = ContextCompat.getColor(this, R.color.color_3);
        final FillDrawable right = new FillDrawable(FillDrawable.FROM_RIGHT, drawable.mutate())
                .setNormalColor(ColorUtils.applyAlpha(rightColor, alpha))
                .setFillColor(rightColor);

        final int bottomColor = ContextCompat.getColor(this, R.color.color_4);
        final FillDrawable bottom = new FillDrawable(FillDrawable.FROM_BOTTOM, drawable.mutate())
                .setNormalColor(ColorUtils.applyAlpha(bottomColor, alpha))
                .setFillColor(bottomColor);

        return new ArrayList<FillDrawable>() {{
            add(right);
            add(bottom);
        }};
    }

    private static class FakeProgress implements Runnable {

        interface OnProgressChange {
            void onProgressChange(float progress);
        }

        private final Handler mHandler;
        private final Random mRandom;
        private final OnProgressChange mOnProgressChange;
        private final float mPercent = 1.F / 100;
        int runs;
        boolean isGrowing = true;

        FakeProgress(Handler handler, OnProgressChange onProgressChange) {
            mHandler = handler;
            mOnProgressChange = onProgressChange;
            mRandom = new Random();
        }

        @Override
        public void run() {

            if (isGrowing && ++runs >= 100) {
                isGrowing = false;
                runs = 100;
            } else if (!isGrowing && --runs <= 0) {
                isGrowing = true;
                runs = 0;
            }

            final int step = mRandom.nextInt(50) + 50;
            mOnProgressChange.onProgressChange(mPercent * runs);
            mHandler.postDelayed(this, step);
        }
    }
}
