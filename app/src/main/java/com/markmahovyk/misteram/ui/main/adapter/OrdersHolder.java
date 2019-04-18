package com.markmahovyk.misteram.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.model.task.Order;

import java.sql.Time;
import java.text.SimpleDateFormat;

class OrdersHolder extends RecyclerView.ViewHolder {
    private TextView timeTv;
    private TextView orderCodeTv;
    private TextView nameTv;
    private TextView addressTv;
    private TextView financeTv;
    private TextView orderStatusTextView;
    private ImageView typeOrdersImageView;
    public LinearLayout tagForOrderLayout;

    public OrdersHolder(@NonNull View itemView) {
        super(itemView);
        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        orderCodeTv = (TextView) itemView.findViewById(R.id.orderCodeTv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        addressTv = (TextView) itemView.findViewById(R.id.adressTv);
        financeTv = (TextView) itemView.findViewById(R.id.financeTv);
        orderStatusTextView = (TextView) itemView.findViewById(R.id.orderStatusTextView);
        typeOrdersImageView = (ImageView) itemView.findViewById(R.id.typeOrderImageView);
        tagForOrderLayout = (LinearLayout) itemView.findViewById(R.id.tagForOrderLayout);
    }

    public void bindView(Order order) {

        timeTv.setText(new SimpleDateFormat("HH:mm").format(new Time(order.getTime())));

        nameTv.setText(order.getAddress().getTitle());

        addressTv.setText(order.getAddress().getDescription());

        setTextPriceAndColor(order);

        orderCodeTv.setText(String.valueOf(order.getNumber().toCharArray(),
                order.getNumber().length()-4,4));

        setIconPutOrderToBox(order);
    }

    private void setTextPriceAndColor(Order order) {
        double price = order.getAmount();
        financeTv.setText(String.valueOf(price)+ "грн.");
        if (price > 0) {
            financeTv.setTextColor(R.color.shamrockGreen);
        } else if (price < 0) {
            financeTv.setTextColor(R.color.jasper);
        }
    }

    private void setIconPutOrderToBox(Order order) {
        if (order.getType().equals("company")) {
            typeOrdersImageView.setImageResource(R.drawable.arrow_company);
        } else if (order.getType().equals("user")) {
            typeOrdersImageView.setImageResource(R.drawable.arrow_user);
        }
    }
}
