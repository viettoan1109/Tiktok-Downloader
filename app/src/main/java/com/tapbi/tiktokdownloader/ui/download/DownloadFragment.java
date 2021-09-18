package com.tapbi.tiktokdownloader.ui.download;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.tapbi.tiktokdownloader.R;

import com.tapbi.tiktokdownloader.databinding.FragmentDownloadBinding;
import com.tapbi.tiktokdownloader.ui.adapter.TablayoutAdapter;
import com.tapbi.tiktokdownloader.ui.base.BaseFragment;
import com.tapbi.tiktokdownloader.ui.music.MusicFragment;
import com.tapbi.tiktokdownloader.ui.video.VideoFragment;
import com.tapbi.tiktokdownloader.utils.Method;
import com.tapbi.tiktokdownloader.utils.StorageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.tapbi.tiktokdownloader.ui.video.VideoFragment.videoArrayList;


public class DownloadFragment extends BaseFragment<FragmentDownloadBinding, DownloadViewModel> {

    private VideoFragment videoFragment = new VideoFragment();
    private MusicFragment musicFragment = new MusicFragment();
    private int indicatorWidth;
    private int[] tabIcons = {
            R.drawable.ic_video_tablayout,
            R.drawable.ic_music_tablayout
    };


    @Override
    protected Class<DownloadViewModel> getViewModelClass() {
        return DownloadViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_download;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(binding.viewPagerDownload);
      //  checkData();
    }

    private void setupViewPager(ViewPager viewPager) {
        binding.tablayoutDownload.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(videoFragment, "Video");
        adapter.addFrag(musicFragment, "Music");

        viewPager.setAdapter(adapter);
        setupTablayout();

    }

    public void onSelect(){
        binding.txtDownloadSelect.setVisibility(View.VISIBLE);
    }

    private void setupTablayout() {
       /* TextView txtTabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_text_view, null);
        txtTabOne.setText(getActivity().getResources().getText(R.string.tab_download_video));
        TextView txtTabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_text_view, null);
        txtTabTwo.setText(getActivity().getResources().getText(R.string.tab_download_music));*/

        binding.tablayoutDownload.getTabAt(0).setIcon(tabIcons[0]);
        binding.tablayoutDownload.getTabAt(1).setIcon(tabIcons[1]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /* private void setupTablayout() {
        TablayoutAdapter adapter = new TablayoutAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(VideoFragment.newInstance(), "Video");
        adapter.addFragment(MusicFragment.newInstance(), "Music");
        binding.viewPagerDownload.setAdapter(adapter);
        binding.tablayoutDownload.setupWithViewPager(binding.viewPagerDownload);

        binding.tablayoutDownload.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = binding.tablayoutDownload.getWidth() / binding.tablayoutDownload.getTabCount();
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                binding.indicator.setLayoutParams(indicatorParams);
            }
        });

        binding.tablayoutDownload.getTabAt(0).setIcon(R.drawable.ic_video_tablayout);
        binding.tablayoutDownload.getTabAt(1).setIcon(R.drawable.ic_music_tablayout);

        binding.viewPagerDownload.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();

                float translationOffset = (positionOffset + position) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                binding.indicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }*/
}