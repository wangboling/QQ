package com.iotek.fgs.qq;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {

	public MyFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		if(arg0 == 0){
			return new MessageFragment();
		}else if(arg0 == 1){
			return new ContactsFragment();
		}else{
			return new MyViewFragment();
		}


	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
