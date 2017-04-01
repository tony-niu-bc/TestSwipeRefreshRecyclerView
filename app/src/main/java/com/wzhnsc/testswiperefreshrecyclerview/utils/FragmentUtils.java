package com.wzhnsc.testswiperefreshrecyclerview.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class FragmentUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void removeFragmentFromActivity(@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment) {
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
