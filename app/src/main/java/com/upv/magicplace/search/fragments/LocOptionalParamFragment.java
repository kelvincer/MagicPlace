package com.upv.magicplace.search.fragments;

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

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.R;
import com.upv.magicplace.search.activity.ui.SearchActivity;
import com.upv.magicplace.search.activity.ui.SearchActivity;
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
    @BindView(R.id.rdg_sort)
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
        newSearchWithRadius();
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

                if (s.toString().length() < 3)
                    return;

                if (s.toString().isEmpty()) {
                    CommonHelper.radius = null;
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        etxRadius.setError("Se requiere el radio");
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        newSearchWithoutRadius();
                    }
                } else {
                    etxRadius.setError(null);
                    if (getActivity().getCurrentFocus() == etxRadius) {
                        CommonHelper.radius = s.toString();
                        newSearchWithRadius();
                    }
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

                if (s.toString().length() <= 3)
                    return;

                if (s.toString().isEmpty()) {
                    CommonHelper.KEYWORD = null;
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        newSearchWithRadius();
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        //etxKeyword.setError("Se requiere palabra clave");
                        newSearchWithoutRadius();
                    }
                } else {
                    CommonHelper.KEYWORD = s.toString();
                    etxKeyword.setError(null);
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        newSearchWithRadius();
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        newSearchWithoutRadius();
                    }
                }
            }
        });

        cebOpennow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CommonHelper.opennow = "";
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        newSearchWithRadius();
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        newSearchWithoutRadius();
                    }
                } else {
                    CommonHelper.opennow = null;
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        newSearchWithRadius();
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        newSearchWithoutRadius();
                    }
                }
            }
        });

        isbMinprice.setEnabled(false);

        chbMinprice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isbMinprice.setEnabled(true);
                    CommonHelper.minprice = "0";
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        newSearchWithRadius();
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        newSearchWithoutRadius();
                    }
                } else {
                    isbMinprice.setEnabled(false);
                    isbMinprice.setProgress(0);
                    CommonHelper.minprice = null;
                    if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                        newSearchWithRadius();
                    } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                        newSearchWithoutRadius();
                    }
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
                        case R.id.rdb_prominence:
                            CommonHelper.RANKYBY = "prominence";
                            CommonHelper.radius = "1000";
                            etxRadius.clearFocus();
                            etxRadius.setText("1000");
                            etxRadius.setEnabled(true);
                            newSearchWithRadius();
                            break;
                        case R.id.rdb_distance:
                            CommonHelper.RANKYBY = "distance";
                            CommonHelper.radius = null;
                            etxRadius.setEnabled(false);
                            etxRadius.getText().clear();
                            etxRadius.setError(null);
                            newSearchWithoutRadius();
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
                }
                if (CommonHelper.RANKYBY.equalsIgnoreCase("prominence")) {
                    newSearchWithRadius();
                } else if (CommonHelper.RANKYBY.equalsIgnoreCase("distance")) {
                    newSearchWithoutRadius();
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

    private void newSearchWithRadius() {

        if (CommonHelper.radius.isEmpty()) {
            Toast.makeText(getActivity(), "El radio es nulo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (CommonHelper.QUERY != null) {
            ((SearchActivity) getActivity()).newSearch(CommonHelper.QUERY);
        }
    }

    private void newSearchWithoutRadius() {

        if (CommonHelper.QUERY != null) {
            ((SearchActivity) getActivity()).newSearch(CommonHelper.QUERY);
        }
    }
}
