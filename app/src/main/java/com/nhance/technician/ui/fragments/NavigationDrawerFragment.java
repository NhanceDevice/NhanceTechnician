package com.nhance.technician.ui.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhance.technician.R;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.model.Application;
import com.nhance.technician.ui.action.CommonAction;
import com.nhance.technician.ui.custom.CircularImageView;
import com.nhance.technician.ui.custom.adapter.NavigationDrawerListviewAdapter;
import com.nhance.technician.util.FileUtil;
import com.nhance.technician.util.Util;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    public ActionBarDrawerToggle getmDrawerToggle() {
        return mDrawerToggle;
    }

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;
    private String mTitle;
    private String mSubTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = -1;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private TextView nameTextView,phoneNumberTextView;
    private CircularImageView profilePicImageView;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    private AppCompatTextView versionTv, copyrightTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myParentView =  inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        RelativeLayout profile_pic_main_ll = (RelativeLayout)myParentView.findViewById(R.id.profile_pic_main_ll);
        profile_pic_main_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onNavigationDrawerProfileClicked();
            }
        });

        Application application = Application.getInstance();

        profilePicImageView = (CircularImageView)myParentView.findViewById(R.id.profile_pic_imageview);

        updateProfilePic();

        nameTextView = (TextView)myParentView.findViewById(R.id.profile_management_name);
        updateUserName(application.getLoggedInUserName());

        phoneNumberTextView = (TextView)myParentView.findViewById(R.id.profile_management_mobile_number);
        if(application != null && application.getMobileNumber() != null)
        {
            phoneNumberTextView.setText(application.getMobileNumber());
        }
        mDrawerListView = (ListView) myParentView.findViewById(R.id.drawer_list_view);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        /*mDrawerListView.setAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
//                android.R.layout.simple_list_item_1,
                android.R.id.text1,getResources().getStringArray(R.array.app_navigation_menu)));
//        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);*/

        mDrawerListView.setAdapter(new NavigationDrawerListviewAdapter(getActivity(), getResources().getStringArray(R.array.app_navigation_menu), getResources().obtainTypedArray(R.array.app_navigation_menu_icons)/*getResources().getIntArray(R.array.app_navigation_menu_icons)*/));

       /* TextView textView = (TextView)myParentView.findViewById(R.id.about_us_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "About clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        copyrightTv = (AppCompatTextView) myParentView.findViewById(R.id.copyright_tv);
        copyrightTv.setText(getActivity().getResources().getString(R.string.copyright));
        versionTv = (AppCompatTextView) myParentView.findViewById(R.id.version_no_tv);
        versionTv.setText(Util.getAppVersion(getActivity()));

        return myParentView;
    }

    public void updateUserName(String userName) {
        if(userName != null && userName.length() > 0)
        {
            nameTextView.setText(userName);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public boolean isDrawerOpen(int gravity) {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(gravity);
    }

    public void closeDrawer(int gravity) {
        mDrawerLayout.closeDrawer(gravity);
    }

    public void openDrawer(int gravity) {
        mDrawerLayout.openDrawer(gravity);
    }

    private float lastTranslate = 0.0f;
    private View frame;
    private boolean rtlEnabled;

    public void setContainer(View container) {

        frame = container;
    }

    public void setRightToLeftEnabled(boolean rtlEnabled) {

        this.rtlEnabled = rtlEnabled;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar, String screenTitle) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;
        mTitle = screenTitle;

        if(mTitle == null)
            mTitle = getActivity().getResources().getString(R.string.app_name);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        initActionBar();

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                mToolbar,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                if(frame != null) {

                    float moveFactor = (mDrawerListView.getWidth() * slideOffset);

                    if(rtlEnabled)
                        moveFactor = -moveFactor;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        frame.setTranslationX(moveFactor);
                    } else {
                        TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                        anim.setDuration(0);
                        anim.setFillAfter(true);
                        frame.startAnimation(anim);

                        lastTranslate = moveFactor;
                    }
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                new CommonAction().reloadApplicationContextDetails();

                Application application = Application.getInstance();
                updateUserName(application.getLoggedInUserName());
                if(application != null && application.getMobileNumber() != null)
                {
                    phoneNumberTextView.setText(application.getMobileNumber());
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        /*if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }*/

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar, String screenTitle, String screenSubTitle) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;
        mTitle = screenTitle;
        mSubTitle = screenSubTitle;

        if(mTitle == null)
            mTitle = getActivity().getResources().getString(R.string.app_name);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        initActionBar();

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                mToolbar,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                if(frame != null) {

                    float moveFactor = (mDrawerListView.getWidth() * slideOffset);

                    if(rtlEnabled)
                        moveFactor = -moveFactor;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        frame.setTranslationX(moveFactor);
                    } else {
                        TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                        anim.setDuration(0);
                        anim.setFillAfter(true);
                        frame.startAnimation(anim);

                        lastTranslate = moveFactor;
                    }
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        /*if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }*/

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }

        closeNavigationDrawerLayout();

        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    public void closeNavigationDrawerLayout() {

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && (isDrawerOpen() || !isDrawerOpen())) {
//            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_settings) {
//            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {

        initActionBar();
    }

    public void initActionBar() {

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + mTitle + " </font>"));

        if(mSubTitle != null && mSubTitle.trim().length() > 0)
            actionBar.setSubtitle(Html.fromHtml("<font color='#ffffff'> <small>" + mSubTitle + " <small> </font>"));
        //TODO:Please set sub title and logo before release.
        //-----------Commented by Javeed as on 23-12-2015 bcos of unknown subtitle and logo---------------//
        /*actionBar.setLogo(R.mipmap.ic_launcher);*/
    }

    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public void updateProfilePic() {

        Application application = Application.getInstance();
        if(application != null && application.getLoggedInUserProfilePicPath() != null && application.getLoggedInUserProfilePicPath().length() > 0)
        {
            int outWidth = profilePicImageView.getWidth();
            int outHeight = profilePicImageView.getHeight();
            Bitmap resizedBitmap = null;
            try {
                resizedBitmap = FileUtil.getScaledBitmap(application.getLoggedInUserProfilePicPath(), 60, 60);
            } catch (NhanceException e) {
                e.printStackTrace();
            }
            if(resizedBitmap != null)
                profilePicImageView.setImageBitmap(resizedBitmap);
           /* BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(application.getLoggedInUserProfilePicPath(), options);
            if(bitmap != null)
                profilePicImageView.setImageBitmap(bitmap);*/
        }
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
        void onNavigationDrawerProfileClicked();
    }
}
