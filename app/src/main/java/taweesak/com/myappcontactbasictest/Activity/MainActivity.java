package taweesak.com.myappcontactbasictest.Activity;

import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import taweesak.com.myappcontactbasictest.Adapter.ViewPagerAdapter;
import taweesak.com.myappcontactbasictest.Data.Contact;
import taweesak.com.myappcontactbasictest.Data.DBHelper;
import taweesak.com.myappcontactbasictest.Fragment.FragmentFavorite;
import taweesak.com.myappcontactbasictest.Fragment.FragmentMain;
import taweesak.com.myappcontactbasictest.Fragment.FragmentProfile;
import taweesak.com.myappcontactbasictest.R;

public class MainActivity extends AppCompatActivity {

    public static DBHelper myDb;
    public static ArrayList<Contact> allContacts = new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBHelper(this);
        allContacts = new ArrayList<Contact>();

        getThemAll(); //allContacts = getThemAll();

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewpager = (ViewPager) findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding fragments
        adapter.addFragment(new FragmentMain(), "Contact");
        adapter.addFragment(new FragmentFavorite(), "Favorite");
        adapter.addFragment(new FragmentProfile(), "Profile"); // Add

        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_import_contacts_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_person1);

    }

    public static ArrayList<Contact> getThemAll() {

        Cursor res = myDb.getAllData();

        allContacts.removeAll(allContacts);

        while (res.moveToNext()) {

            Contact c = new Contact(
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getString(5),
                    res.getBlob(6)
            );
            c.setLocation(res.getString(7));

            Log.d("TAG", "getThemAll: " + c.toString() + c.getFavorite());
            Log.d("GTA", " " + c.getLocation());
            if (c.getLastName() != null || c.getFirstName() != null)
                allContacts.add(c);
        }

        return allContacts;

    }
}
