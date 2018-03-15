package app.stucre;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class SectionPageAdapter2 extends FragmentPagerAdapter{

    final  int pageCount;

    public SectionPageAdapter2(FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ElectivesModulesOptionA electivesModulesOptionA= new ElectivesModulesOptionA();
                return electivesModulesOptionA;
            case 1:
                ElectivesModulesOptionB electivesModulesOptionB = new ElectivesModulesOptionB();
                return electivesModulesOptionB;
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }


}
