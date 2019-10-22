package com.app.foodtonutrition.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.foodtonutrition.room.model.DailyNutritionModel;
import com.app.foodtonutrition.R;

import java.text.DecimalFormat;
import java.util.List;

public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder> {

    private List<DailyNutritionModel> list;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public FoodItemsAdapter(List<DailyNutritionModel> list, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.list = list;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    @NonNull
    public FoodItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_food, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull FoodItemsAdapter.ViewHolder holder, int position) {
        final DailyNutritionModel nutritionModel = list.get(position);
        double total = nutritionModel.getTotalFat() + nutritionModel.getCarbohydrate() + nutritionModel.getDietaryFibre() + nutritionModel.getProtein() + nutritionModel.getSaturatedFat() + nutritionModel.getTotalSugar();
        holder.foodName.setText(nutritionModel.getFoodName());
        holder.totalCalories.setText("Total: " + decimalFormat(total) + " g");
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onDeleteClickListener(nutritionModel);
            }
        });
    }

    private String decimalFormat(double value) {
        return String.valueOf(new DecimalFormat("##.##").format(value));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<DailyNutritionModel> q) {
        clearItems();
        list.addAll(q);
        notifyDataSetChanged();
    }

    private void clearItems() {
        list.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, totalCalories;
        ImageView ivDelete;

        ViewHolder(View view) {
            super(view);
            foodName = view.findViewById(R.id.foodName);
            totalCalories = view.findViewById(R.id.totalCalories);
            ivDelete = view.findViewById(R.id.ivDelete);
        }
    }

    public interface OnRecyclerItemClickListener {
        void onDeleteClickListener(DailyNutritionModel dailyNutritionModel);
    }
}





