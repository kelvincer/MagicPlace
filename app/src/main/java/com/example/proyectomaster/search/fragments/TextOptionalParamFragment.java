package com.example.proyectomaster.search.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.warkiz.widget.IndicatorSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TextOptionalParamFragment extends Fragment {

    private static final String TAG = TextOptionalParamFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.etx_location)
    EditText etxLocation;
    @BindView(R.id.btn_location)
    Button btnLocation;
    @BindView(R.id.etx_radius)
    EditText etxRadius;
    @BindView(R.id.chb_opennow)
    CheckBox chbOpennow;
    @BindView(R.id.chb_minprice)
    CheckBox chbMinprice;
    @BindView(R.id.isb_minprice)
    IndicatorSeekBar isbMinprice;

    public static Fragment newInstance() {
        Fragment fragment = new TextOptionalParamFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text_optional_param, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupViews() {

        etxLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty())
                    CommonHelper.location = s.toString();
                else
                    CommonHelper.location = null;
            }
        });

        etxRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty())
                    CommonHelper.radius = s.toString();
                else
                    CommonHelper.radius = null;
            }
        });

        chbOpennow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    CommonHelper.opennow = "";
                else
                    CommonHelper.opennow = null;
            }
        });

        isbMinprice.setEnabled(false);

        chbMinprice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isbMinprice.setEnabled(true);
                    CommonHelper.minprice = "0";
                } else {
                    isbMinprice.setEnabled(false);
                    isbMinprice.setProgress(0);
                    CommonHelper.minprice = null;
                }
            }
        });

        isbMinprice.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {
                if (fromUserTouch) {
                    Toast.makeText(getActivity(), textBelowTick, Toast.LENGTH_SHORT).show();
                    CommonHelper.minprice = textBelowTick;
                } else {
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }
}
