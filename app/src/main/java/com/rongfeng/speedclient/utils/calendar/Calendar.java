package com.rongfeng.speedclient.utils.calendar;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @class WorkLogCalendar.java [V 1.0.0]
 *
 * @author 唐磊
 *
 * @time 2014-4-25 下午5:14:21
 *
 * @TODO [日历类]
 */
public class Calendar {
	private GridView gridView;
	private int mYear;
	private int mMonth;
	private int mDay;
	/** 一年中的大月份*/
	private List<Integer> listBigMonth = new ArrayList<Integer>();
	/** 一年中的小月份*/
	private List<Integer> listSmallMonth = new ArrayList<Integer>();
    /** 阳历*/
	private List<Integer> list = new ArrayList<Integer>();
	private HashMap<Integer,String> map=new HashMap<>();
	private CalendarAdapter adapter;
	/**
	 * 返回当月1号是星期几
	 */
	private int fdw;
	private CalendarView calendarView;
	public int size = 0;
	private boolean isFrist=true;

	public Calendar(CalendarView calendarView) {
		super();
		this.calendarView = calendarView;
//		adapter = new CalendarAdapter(calendarView.getContext());
	}

	public void setYMD(int mYear, int mMonth, int mDay, int fdw){
		this.mYear=mYear;
		this.mMonth = mMonth;
		this.mDay = mDay;
		this.fdw = fdw;
	}

	public View onCreateView() {
		gridView= (GridView) LayoutInflater.from(calendarView.getContext()).inflate(R.layout.calender, null);
		initMonthList();
		init();
		return gridView;
	}

	private void initMonthList() {
		// TODO Auto-generated method stub
		listBigMonth.add(1);
		listBigMonth.add(3);
		listBigMonth.add(5);
		listBigMonth.add(7);
		listBigMonth.add(8);
		listBigMonth.add(10);
		listBigMonth.add(12);

		listSmallMonth.add(4);
		listSmallMonth.add(6);
		listSmallMonth.add(9);
		listSmallMonth.add(11);
	}

	private void init() {
		list.clear();
		map.clear();
		int days = getMonthDays(mMonth);
		boolean isStart = false;
		int count = days + fdw - 1;
		int y = count % 7;
		int j = count / 7;
		if (y > 0) {
			size = (j + 1) * 7;
		} else if (y == 0) {
			size = j * 7;
		}
		for (int i = 0; i < size; i++) {
			if (i + 1 == fdw)
				isStart = true;
			if (i == fdw + days - 1)
				isStart = false;
			if (isStart) {
				int d=i - fdw + 2;
				list.add(d);
				map.put(d,ChinaDate.getday(mYear,mMonth,d));
			} else
				list.add(null);
		}
		adapter = new CalendarAdapter(calendarView.getContext());
		adapter.setListMap(list,map,mYear,mMonth,mDay);
		/** GridView 设置padding 去掉右边空隙过大  */
        gridView.setColumnWidth(Utils.getDeviceWidthPixels(calendarView.getContext())-Utils.dip2px(calendarView.getContext(),40));
		if(isFrist){
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					gridView.setAdapter(adapter);
				}
			},50);
			isFrist=false;
		}else gridView.setAdapter(adapter);

		getLastMonthFristDay(mMonth);
		getNextMonthFristDay(mMonth);
	}

	/**
	 * @return 返回当月的天数
	 */
	private int getMonthDays(int mMonth) {
		int days = 0;
		switch (mMonth) {
		case 2:
			if (mYear%4==0) {
				days = 29;
				break;
			}
			days = 28;
			break;
		default:
			if (listBigMonth.contains(mMonth)) {
				days = 31;
			}
			if (listSmallMonth.contains(mMonth)) {
				days = 30;
			}
			break;
		}
		return days;

	}

	/**
	 * @param mMonth
	 *            第几月
	 *            <p>
	 *            上一个月1号周几
	 *            </p>
	 */
	private void getLastMonthFristDay(int mMonth) {
		int fd = 0;
		int lastMonth = mMonth - 1;
		if (lastMonth == 0) {
			lastMonth = 12;
		}
		int lastMonthDays = getMonthDays(lastMonth);
		int x = lastMonthDays % 7;
		int y = fdw - x;
		if (y > 0) {
			fd = y;
		} else if (y < 0) {
			fd = y + 7;
		} else if (y == 0) {
			fd = 7;
		}
		calendarView.setLastMonthFristDay(fd);
	}

	/**
	 * @param mMonth 第几月
	 *            <p>
	 *            下一个月1号周几
	 *            </p>
	 */
	private void getNextMonthFristDay(int mMonth) {
		int fd = 0;
		int monthDays = getMonthDays(mMonth);
		int x = (monthDays) % 7;
		int y = fdw + x;
		if (y > 0 && y <= 7) {
			fd = y;
		} else if (y > 7) {
			fd = y - 7;
		} else if (y == 0) {
			fd = 7;
		}
		 calendarView.setNextMonthFristDay(fd);
	}

	public CalendarAdapter getAdapter() {
		return adapter;
	}

	public List<Integer> getList() {
		return list;
	}
}
