package com.example.andrea.diariostraordinari.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 19/06/17.
 */

/**
 * Adatper per associare una lista di Fragment alle TAB
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    //Variabili di classe
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    //Costruttore
    public FragmentViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    //Metodo per aggiungere un Fragment
    //Si associa per ogni Fragment una coppia di valori <Fragment, title> salvati nelle rispettive liste
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    //Metodo per acquisire il titolo indicizzato
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    //Metodo per acquisire il fragment indicizzato
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //Metodo per acquisire la grandezza dell'ArrayList
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
