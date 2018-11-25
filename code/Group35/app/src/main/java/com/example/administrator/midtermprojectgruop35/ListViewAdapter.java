package com.example.administrator.midtermprojectgruop35;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;



public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Hero> mList;
    private LayoutInflater mInflater;
    private RelativeLayout mHead;
    private int n = 1;//标记变量

    public ListViewAdapter(Context context, List<Hero> list, RelativeLayout head) {
        this.mContext = context;
        this.mList = list;
        this.mHead = head;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        MyViewHolder holder;
        Hero hero = mList.get(i);
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item, group, false);
            holder = new MyViewHolder();
            CustomHScrollView scrollView = (CustomHScrollView) view.findViewById(R.id.h_scrollView);
            holder.scrollView = scrollView;
            holder.imageViewIcon = (ImageView) view.findViewById(R.id.imageViewIcon);
            holder.textViewChineseName = (TextView) view.findViewById(R.id.textViewChineseName);
            holder.textViewSpecies = (TextView) view.findViewById(R.id.textViewSpecies);
            holder.textViewAttackMode = (TextView) view.findViewById(R.id.textViewAttackMode);
            holder.textViewCarry = (TextView) view.findViewById(R.id.textViewCarry);
            holder.textViewSupport = (TextView) view.findViewById(R.id.textViewSupport);
            holder.textViewNuker = (TextView) view.findViewById(R.id.textViewNuker);
            holder.textViewDisabler = (TextView) view.findViewById(R.id.textViewDisabler);
            holder.textViewJungler = (TextView) view.findViewById(R.id.textViewJungler);
            holder.textViewDurable = (TextView) view.findViewById(R.id.textViewDurable);
            holder.textViewEscape = (TextView) view.findViewById(R.id.textViewEscape);
            holder.textViewPusher = (TextView) view.findViewById(R.id.textViewPusher);
            holder.textViewInitiator = (TextView) view.findViewById(R.id.textViewInitiator);
            CustomHScrollView headScrollView = (CustomHScrollView) mHead.findViewById(R.id.h_scrollView);
            headScrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView));
            view.setTag(holder);
        }
        else {
            holder = (MyViewHolder) view.getTag();
        }
        holder.imageViewIcon.setImageBitmap(hero.getIcon());
        holder.textViewChineseName.setText(hero.getChineseName());
        holder.textViewSpecies.setText(Hero.speciesToString(hero.getSpecies()));
        holder.textViewAttackMode.setText(Hero.attackModeToString(hero.getAttackMode()));
        holder.textViewCarry.setText(Integer.valueOf(hero.getCarry()) > 0 ? "○" : "");
        holder.textViewSupport.setText(Integer.valueOf(hero.getSupport()) > 0 ? "○" : "");
        holder.textViewNuker.setText(Integer.valueOf(hero.getNuker()) > 0 ? "○" : "");
        holder.textViewDisabler.setText(Integer.valueOf(hero.getDisabler()) > 0 ? "○" : "");
        holder.textViewJungler.setText(Integer.valueOf(hero.getJungler()) > 0 ? "○" : "");
        holder.textViewDurable.setText(Integer.valueOf(hero.getDurable()) > 0 ? "○" : "");
        holder.textViewEscape.setText(Integer.valueOf(hero.getEscape()) > 0 ? "○" : "");
        holder.textViewPusher.setText(Integer.valueOf(hero.getPusher()) > 0 ? "○" : "");
        holder.textViewInitiator.setText(Integer.valueOf(hero.getInitiator()) > 0 ? "○" : "");
        return view;
    }

    class OnScrollChangedListenerImp implements CustomHScrollView.OnScrollChangedListener {
        CustomHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(CustomHScrollView scrollViewar) {
            mScrollViewArg = scrollViewar;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l, t);
            if (n == 1) {//记录滚动的起始位置，避免因刷新数据引起错乱
                new MainActivity().setPosData(oldl, oldt);
            }
            n++;
        }
    };

    class MyViewHolder{
        ImageView imageViewIcon;
        TextView textViewChineseName;
        TextView textViewSpecies;
        TextView textViewAttackMode;
        TextView textViewCarry;
        TextView textViewSupport;
        TextView textViewNuker;
        TextView textViewDisabler;
        TextView textViewJungler;
        TextView textViewDurable;
        TextView textViewEscape;
        TextView textViewPusher;
        TextView textViewInitiator;
        HorizontalScrollView scrollView;
    }
}
