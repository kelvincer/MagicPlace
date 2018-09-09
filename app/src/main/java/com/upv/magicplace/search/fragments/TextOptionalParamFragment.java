package com.upv.magicplace.search.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.ConstantsHelper;
import com.upv.magicplace.R;
import com.upv.magicplace.search.activity.ui.SearchActivity;
import com.warkiz.widget.IndicatorSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TextOptionalParamFragment extends Fragment implements View.OnClickListener {

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
    @BindView(R.id.txv_radius)
    TextView txvRadius;

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_location:
                getLocation();
                break;
            default:
                break;
        }
    }

    private void setupViews() {

        btnLocation.setOnClickListener(this);
        etxLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    if (ConstantsHelper.LAT_LNG_PATTERN.matcher(s.toString()).matches()) {
                        CommonHelper.location = s.toString();
                        enableRadius(true);
                        if (CommonHelper.radius == null) {
                            Toast.makeText(getActivity(), "Debes ingresar el radio", Toast.LENGTH_SHORT).show();
                        } else {
                            newSearch();
                        }
                    } else {
                        CommonHelper.location = null;
                        enableRadius(false);
                        Toast.makeText(getActivity(), "Los datos no son válidos", Toast.LENGTH_LONG).show();
                    }
                } else {
                    CommonHelper.location = null;
                    CommonHelper.radius = null;
                    etxRadius.getText().clear();
                    enableRadius(false);
                    newSearch();
                }
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

                if (!s.toString().isEmpty()) {
                    CommonHelper.radius = s.toString();
                    if (CommonHelper.location == null) {
                        Toast.makeText(getActivity(), "Debes ingresar la localización", Toast.LENGTH_SHORT).show();
                    } else {
                        newSearch();
                    }
                } else {
                    CommonHelper.radius = null;
                    Toast.makeText(getActivity(), "Debes ingresar el radio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chbOpennow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CommonHelper.opennow = "";
                    newSearch();
                } else {
                    CommonHelper.opennow = null;
                    newSearch();
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
                    newSearch();
                } else {
                    isbMinprice.setEnabled(false);
                    isbMinprice.setProgress(0);
                    CommonHelper.minprice = null;
                    newSearch();
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
                    newSearch();
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

    private void enableRadius(boolean enabled) {
        etxRadius.setEnabled(enabled);
        txvRadius.setEnabled(enabled);
    }

    public void getLocation() {

        if (CommonHelper.MY_LOCATION != null) {
            etxLocation.setText(String.format("%1$.5f,%2$.5f", CommonHelper.MY_LOCATION.getLatitude(), CommonHelper.MY_LOCATION.getLongitude()));
        } else {
            Toast.makeText(getActivity(), "MY LOCATION IS NULL", Toast.LENGTH_SHORT).show();
        }
    }

    private void newSearch() {

        if (CommonHelper.QUERY != null) {
            ((SearchActivity) getActivity()).newSearch(CommonHelper.QUERY);
        } else {
            Toast.makeText(getActivity(), "QUERY IS NULL", Toast.LENGTH_SHORT).show();
        }
    }
}
