package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DutiesFase2 extends ListFragment {

  public DutiesFase2(){

  }

  String [] menuItems = {"Basisbegrippen van recht","Inleiding tot het Europees en internationaal recht","Geschiedenis van het publiekrecht en de politiek","Economie","Ethische en rechtsfilosofische stromingen","Rechtsmethodiek","Mensenrechten",
          "Strafrecht","Verbintenissenrecht","Sociale psychologie",
          "Staatsrecht"};
  ArrayAdapter<String>adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice);
    setListAdapter(adapter);
    return super.onCreateView(inflater, container, savedInstanceState);


  }

  @Override
  public void onStart() {
    super.onStart();
    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }
}
