package com.app.foodtonutrition;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.foodtonutrition.adapter.FoodItemsAdapter;
import com.app.foodtonutrition.custom.CustomAutoComplete;
import com.app.foodtonutrition.room.model.DailyNutritionDisplayModel;
import com.app.foodtonutrition.room.model.DailyNutritionModel;
import com.app.foodtonutrition.room.model.MeasurementModel;
import com.app.foodtonutrition.room.model.User;
import com.app.foodtonutrition.room.repository.Repository;
import com.app.foodtonutrition.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainPageActivity extends AppCompatActivity implements FoodItemsAdapter.OnRecyclerItemClickListener {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvWeight;
    private TextView tvAge;
    private TextView tvGender;
    private TextView tvProteinValue;
    private TextView tvCarboValue;
    private TextView tvSugarValue;
    private TextView tvFiberValue;
    private TextView tvTotalFatValue;
    private TextView tvSaturatedFatValue;
    private TextView tvTotalValue;
    private TextView tvDetails;
    private Button btnSave;
    private ImageView ivToday;
    private ImageView ivCalender;
    private EditText etGrams;
    private Repository repository;
    private CustomAutoComplete autoComTextView;
    private String queryDate;
    private String loggedEmail;
    private String selectedFoodFromDropDown;
    private MeasurementModel selectedFood;
    private ArrayList<MeasurementModel> foodsNamesList = new ArrayList<>();
    private List<DailyNutritionModel> dailyNutritionModels = new ArrayList<>();
    private FoodItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        repository = new Repository(MainPageActivity.this);
        setTitle("Main");
        setupViews();
        setQueryDate(new Date());
        getUserDetails();
        getFoodList();
        handleObservable();
        getTodaysTotalNutritionsValue();
        getTodaysFoodItems();
    }

    private void setupViews() {
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvWeight = findViewById(R.id.tvWeight);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvDetails = findViewById(R.id.tvDetails);
        tvProteinValue = findViewById(R.id.tvProteinValue);
        tvCarboValue = findViewById(R.id.tvCarbohydrateValue);
        tvSugarValue = findViewById(R.id.tvTotalSugarValue);
        tvFiberValue = findViewById(R.id.tvTotalDietaryFiberValue);
        tvTotalFatValue = findViewById(R.id.tvTotalFatValue);
        tvSaturatedFatValue = findViewById(R.id.tvSaturatedFatValue);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        ivCalender = findViewById(R.id.ivCalender);
        ivToday = findViewById(R.id.ivToday);
        RecyclerView rvFoodItemList = findViewById(R.id.rvFoodItemList);
        btnSave = findViewById(R.id.btnAdd);
        autoComTextView = findViewById(R.id.autoComTextView);
        etGrams = findViewById(R.id.etGrams);

        mAdapter = new FoodItemsAdapter(dailyNutritionModels, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvFoodItemList.setLayoutManager(mLayoutManager);
        rvFoodItemList.setItemAnimator(new DefaultItemAnimator());
        rvFoodItemList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFoodItemList.setAdapter(mAdapter);
    }

    private void handleObservable() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etGrams.getText().toString())) {
                    double gram = Double.parseDouble(etGrams.getText().toString());
                    double totalProtein = gram * selectedFood.getProteinPerGram();
                    double totalCarbohydrate = gram * selectedFood.getCarbohydratePerGram();
                    double totalSugar = gram * selectedFood.getTotalSugarPerGram();
                    double totalDietaryFibre = gram * selectedFood.getTotalDietaryFibre();
                    double totalFat = gram * selectedFood.getTotalFat();
                    double totalSaturatedFat = gram * selectedFood.getSaturatedFat();
                    DailyNutritionModel dailyNutritionModel = new DailyNutritionModel(loggedEmail + "_" + queryDate,
                            selectedFoodFromDropDown,
                            totalProtein,
                            totalCarbohydrate,
                            totalSugar,
                            totalDietaryFibre,
                            totalFat,
                            totalSaturatedFat);
                    repository.insertDailyNutrition(dailyNutritionModel);
                    selectedFoodFromDropDown = "";
                    autoComTextView.setText("");
                    etGrams.setText("");
                } else {
                    Toast.makeText(MainPageActivity.this, "Invalid input", Toast.LENGTH_LONG).show();
                }
            }
        });

        ivToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDetails.setText("Details");
                setQueryDate(new Date());
                getTodaysTotalNutritionsValue();
                getTodaysFoodItems();
            }
        });

        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
    }

    private void openDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        DatePickerDialog picker = new DatePickerDialog(MainPageActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        setQueryDate(calendar.getTime());
                        getTodaysTotalNutritionsValue();
                        getTodaysFoodItems();
                        tvDetails.setText(queryDate + "'s Details");
                    }
                }, year, month, day);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.show();
    }

    private void getUserDetails() {
        SharedPreferences preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        loggedEmail = preferences.getString("LOGGED_EMAIL", "");
        repository.getUser(loggedEmail).observe(MainPageActivity.this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                tvName.setText(tvName.getText().toString().concat(user.getName()));
                tvEmail.setText(tvEmail.getText().toString().concat(user.getEmail()));
                tvWeight.setText(tvWeight.getText().toString().concat(String.valueOf(user.getWeight())).concat(" Kg"));
                tvAge.setText(tvAge.getText().toString().concat(String.valueOf(user.getAge()).concat(" years")));
                tvGender.setText(tvGender.getText().toString().concat(user.getGender()));
            }
        });
    }

    private void getFoodList() {
        repository.getFoods().observe(MainPageActivity.this, new Observer<List<MeasurementModel>>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChanged(@Nullable List<MeasurementModel> foods) {
                foodsNamesList = (ArrayList<MeasurementModel>) foods;
                final ArrayList<String> foodsNames = new ArrayList<>();
                for (MeasurementModel model : foods)
                    foodsNames.add(model.getFoodName());
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPageActivity.this,
                        android.R.layout.simple_dropdown_item_1line, foodsNames);
                autoComTextView.setThreshold(1);
                autoComTextView.setAdapter(adapter);
                autoComTextView.setOnTouchListener(new View.OnTouchListener() {

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                        if (foodsNames.size() > 0) {
                            if (!autoComTextView.getText().toString().equals(""))
                                adapter.getFilter().filter(null);
                            autoComTextView.showDropDown();
                        }
                        return false;
                    }
                });

                autoComTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        boolean matched = false;
                        for (String food : foodsNames) {
                            if (food.equals(autoComTextView.getText().toString().trim())) {
                                matched = true;
                                break;
                            }
                        }
                        btnSave.setEnabled(matched);
                        etGrams.setEnabled(matched);
                    }
                });

                autoComTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                        selectedFood = foodsNamesList.get(position);
                        selectedFoodFromDropDown = foodsNames.get(position);
                        btnSave.setEnabled(true);
                        etGrams.setEnabled(true);
                        hideSoftKeyboard(autoComTextView);
                    }
                });
            }
        });
    }

    protected void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    private void getTodaysTotalNutritionsValue() {
        repository.getTodaysNutritions(loggedEmail + "_" + queryDate).observe(MainPageActivity.this, new Observer<DailyNutritionDisplayModel>() {
            @Override
            public void onChanged(@Nullable DailyNutritionDisplayModel nutritions) {
                if (nutritions != null) {
                    tvProteinValue.setText(decimalFormat(nutritions.getTotalProtein()));
                    tvCarboValue.setText(decimalFormat(nutritions.getTotalCarbohydrate()));
                    tvSugarValue.setText(decimalFormat(nutritions.getTotalSugar()));
                    tvFiberValue.setText(decimalFormat(nutritions.getTotalDietaryFibre()));
                    tvTotalFatValue.setText(decimalFormat(nutritions.getTotalFat()));
                    tvSaturatedFatValue.setText(decimalFormat(nutritions.getTotalSaturatedFat()));
                    tvTotalValue.setText(decimalFormat(nutritions.getTotalProtein() + nutritions.getTotalCarbohydrate() + nutritions.getTotalSugar() +
                            nutritions.getTotalDietaryFibre() + nutritions.getTotalFat() + nutritions.getTotalSaturatedFat()));
                }
            }
        });
    }

    private void getTodaysFoodItems() {
        repository.getTodaysFoodItems(loggedEmail + "_" + queryDate).observe(MainPageActivity.this, new Observer<List<DailyNutritionModel>>() {
            @Override
            public void onChanged(@Nullable List<DailyNutritionModel> nutritions) {
                if (nutritions != null) {
                    mAdapter.addItems(nutritions);
                }
            }
        });
    }

    private String decimalFormat(double value) {
        return String.valueOf(new DecimalFormat("##.##").format(value));
    }

    private void setQueryDate(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        queryDate = formatter.format(d);
        boolean isEnable = queryDate.equals(formatter.format(new Date()));
        btnSave.setEnabled(isEnable);
        etGrams.setEnabled(isEnable);
        autoComTextView.setEnabled(isEnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            SharedPreferences preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("IS_LOGGED_IN", false);
            editor.apply();
            startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
            finishAffinity();
        }
        return true;
    }

    @Override
    public void onDeleteClickListener(DailyNutritionModel dailyNutritionModel) {
        repository.deleteFoodItem(dailyNutritionModel);
    }
}
