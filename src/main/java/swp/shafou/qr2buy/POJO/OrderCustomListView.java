package swp.shafou.qr2buy.POJO;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Shafou on 08/02/2017.
 */

public class OrderCustomListView extends ListView {

    private android.view.ViewGroup.LayoutParams params;
    private int prevCount = 0;

    public OrderCustomListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getCount() != prevCount)
        {
            int height = getChildAt(0).getHeight() + 1 ;
            prevCount = getCount();
            params = getLayoutParams();
            params.height = getCount() * height;
            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }

}
