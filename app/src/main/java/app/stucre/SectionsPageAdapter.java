package app.stucre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TI_Laptop-008 on 26/02/2018.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    final int pageCount;
    private List<Vak> Vakken1 = new ArrayList<>();
    private List<Vak> Vakken2 = new ArrayList<>();
    private List<Vak> Vakken3 = new ArrayList<>();


    private List<Fragment>fragments = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm, int pageCounter) {

        super(fm);
        this.pageCount = pageCounter;

    }


    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();

        switch (position){
            case 0:
                DutiesFase1 fase1 = new DutiesFase1();

                return fase1;
            case 1:

                DutiesFase2 fase2 = new DutiesFase2();

                return fase2;
            case 2:

                DutiesFase3 fase3 = new DutiesFase3();

                return fase3;
        }
        return null;
    }

    @Override
    public int getCount() {

        return pageCount;
    }

}
