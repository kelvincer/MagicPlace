package com.example.proyectomaster.search.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.warkiz.widget.IndicatorSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LocOptionalParamFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.etx_radius)
    EditText etxRadius;
    @BindView(R.id.etx_keyword)
    EditText etxKeyword;
    @BindView(R.id.chb_minprice)
    CheckBox chbMinprice;
    @BindView(R.id.ceb_opennow)
    CheckBox cebOpennow;
    @BindView(R.id.isb_minprice)
    IndicatorSeekBar isbMinprice;
    @BindView(R.id.rdg_sortby)
    RadioGroup rdgSortby;

    public static Fragment newInstance() {
        Fragment fragment = new LocOptionalParamFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loc_optional_param, container, false);
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

        etxRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    CommonHelper.radius = null;
                } else {
                    CommonHelper.radius = s.toString();
                    rdgSortby.clearCheck();
                    CommonHelper.RANKYBY = null;
                }
            }
        });

        etxKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    CommonHelper.KEYWORD = null;
                } else {
                    CommonHelper.KEYWORD = s.toString();
                }
            }
        });

        cebOpennow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        rdgSortby.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == -1)
                    return;

                RadioButton checkedRadioButton = group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.rdb_text:
                            CommonHelper.RANKYBY = "prominence";
                            break;
                        case R.id.rdb_location:
                            CommonHelper.RANKYBY = "distance";
                            etxRadius.getText().clear();
                            CommonHelper.radius = null;
                            if (etxKeyword.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity(), "Se requiere keyword", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
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

        rdgSortby.check(R.id.rdb_text);
    }
}
