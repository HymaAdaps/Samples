//package com.adaps.ain043.samples.barchart;
//
//import android.databinding.DataBindingUtil;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//
//import com.adaps.ain043.samples.R;
//import com.adaps.ain043.samples.databinding.ActivityBarChartBinding;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.utils.ColorTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ain043 on 3/22/2018 at 12:00 PM
// */
//
//public class BarChartActivity extends AppCompatActivity {
//    private ActivityBarChartBinding binding;
//
//    ArrayList<String> BarEntryLabels;
//
//    BarDataSet set1, set2, set3, set4;
//    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//    ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
//    ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
//    ArrayList<BarEntry> yVals4 = new ArrayList<BarEntry>();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_bar_chart);
//        initComponents();
////        tryAnother();
//    }
//
//    public void AddValuesToBARENTRY() {
//
//        yVals1 = new ArrayList<>();
//        yVals1.add(new BarEntry(8f, 0));
//        yVals1.add(new BarEntry(2f, 1));
//        yVals1.add(new BarEntry(5f, 2));
//        yVals1.add(new BarEntry(20f, 3));
//        yVals1.add(new BarEntry(15f, 4));
//
//// create BarEntry for Bar Group 1
//        yVals2 = new ArrayList<>();
//        yVals2.add(new BarEntry(6f, 0));
//        yVals2.add(new BarEntry(10f, 1));
//        yVals2.add(new BarEntry(5f, 2));
//        yVals2.add(new BarEntry(25f, 3));
//        yVals2.add(new BarEntry(4f, 4));
//
//        yVals3 = new ArrayList<>();
//        yVals3.add(new BarEntry(5f, 0));
//        yVals3.add(new BarEntry(4f, 1));
//        yVals3.add(new BarEntry(9f, 2));
//        yVals3.add(new BarEntry(23f, 3));
//        yVals3.add(new BarEntry(12f, 4));
//
//        yVals4 = new ArrayList<>();
//        yVals4.add(new BarEntry(7f, 0));
//        yVals4.add(new BarEntry(8f, 1));
//        yVals4.add(new BarEntry(20f, 2));
//        yVals4.add(new BarEntry(15f, 3));
//        yVals4.add(new BarEntry(5f, 4));
//    }
//
//    public void AddValuesToBarEntryLabels() {
//
//        BarEntryLabels.add("January");
//        BarEntryLabels.add("February");
//        BarEntryLabels.add("March");
//        BarEntryLabels.add("April");
//        BarEntryLabels.add("May");
//    }
//
////    private void tryAnother() {
////        AddValuesToBARENTRY();
////
////        set1 = new BarDataSet(yVals1, "Company A");
////        set1.setColor(Color.rgb(104, 241, 175));
////        set2 = new BarDataSet(yVals2, "Company B");
////        set2.setColor(Color.rgb(164, 228, 251));
////        set3 = new BarDataSet(yVals3, "Company C");
////        set3.setColor(Color.rgb(242, 247, 158));
////        set4 = new BarDataSet(yVals4, "Company D");
////        set4.setColor(Color.rgb(255, 102, 0));
////
////        BarData data = new BarData(set1, set2, set3, set4);
////        data.setValueFormatter(new LargeValueFormatter());
//////        data.setValueTypeface(mTfLight);
////
////        binding.barChart.setData(data);
////    }
//
//    private void initComponents() {
//        BarData data = new BarData(getXAxisValues(), getDataSet());
//        binding.barChart.setData(data);
//        binding.barChart.setDescription("My Chart");
//        binding.barChart.animateXY(2000, 2000);
//        binding.barChart.invalidate();
//
//    }
//
//    private ArrayList<BarDataSet> getDataSet() {
//        ArrayList<BarDataSet> dataSets = null;
//
//        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
//        valueSet1.add(v1e4);
//        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
//        valueSet1.add(v1e5);
//        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
//        valueSet1.add(v1e6);
//
//        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
//        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
//        valueSet2.add(v2e1);
//        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
//        valueSet2.add(v2e2);
//        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
//        valueSet2.add(v2e3);
//        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
//        valueSet2.add(v2e4);
//        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
//        valueSet2.add(v2e5);
//        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
//        valueSet2.add(v2e6);
//
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
//        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        barDataSet1.setColors(Color.BLUE);
//
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(Color.GRAY);
//
//        dataSets = new ArrayList<>();
//        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
//        return dataSets;
//    }
//
//    private ArrayList<String> getXAxisValues() {
//        ArrayList<String> xAxis = new ArrayList<>();
//        xAxis.add("JAN");
//        xAxis.add("FEB");
//        xAxis.add("MAR");
//        xAxis.add("APR");
//        xAxis.add("MAY");
//        xAxis.add("JUN");
//        return xAxis;
//    }
//}
