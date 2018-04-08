import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;

import java.util.ArrayList;
import java.util.List;

import app.stucre.Vak;

/**
 * Created by TI_Laptop-008 on 30/03/2018.
 */

public class SetSegementAdpaterTranfomer extends ABaseTransformer {

    List<Vak> Vakken;
    @Override
    protected void onTransform(View page, float position) {
        
    }

    public void setFilter(List<Vak> vakken){
        Vakken = new ArrayList<>();
        Vakken.addAll(vakken);

    }
}
