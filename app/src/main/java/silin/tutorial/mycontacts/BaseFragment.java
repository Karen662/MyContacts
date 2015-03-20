package silin.tutorial.mycontacts;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Gladeux on 3/19/15.
 */
public class BaseFragment<T> extends Fragment {

    private T mContract;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            mContract = (T) activity;
        }

        catch (ClassCastException e)
        {
            throw new IllegalStateException("Activity does not implement Contract interface.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContract = null;
    }

    public T getContract() {
        return mContract;
    }
}
