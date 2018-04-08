package app.stucre;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by TI_Laptop-008 on 26/02/2018.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    final int pageCount;

    public ViewPageAdapter(FragmentManager fm, int pageCounter) {

        super(fm);
        this.pageCount = pageCounter;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ElectivesFase3 electivesFase3 = new ElectivesFase3();
                return electivesFase3;
        }
        return null;
    }

    @Override
    public int getCount() {

        return pageCount;
    }
}
