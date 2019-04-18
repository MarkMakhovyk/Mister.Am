package com.markmahovyk.misteram.ui.detailsOrder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.markmahovyk.misteram.R;
import com.markmahovyk.misteram.data.SharePreference;
import com.markmahovyk.misteram.data.newtwork.App;
import com.markmahovyk.misteram.model.details.Details;
import com.markmahovyk.misteram.model.details.Dish;
import com.markmahovyk.misteram.model.details.PriceData;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ORDER_ID = "order_id";
    Details details = null;
    @BindView(R.id.orderFullNumberTv) TextView orderFullNumberTv;
    @BindView(R.id.currentOrderStatusTv) TextView currentOrderStatusTv;
    @BindView(R.id.receivingTimeTv) TextView receivingTimeTv;
    @BindView(R.id.addressReceivingTv) TextView addressReceivingTv;
    @BindView(R.id.deliveryTimeTv) TextView deliveryTimeTv;
    @BindView(R.id.deliveryAddressTv) TextView deliveryAddressTv;
    @BindView(R.id.nameClientTv) TextView nameClientTv;
    @BindView(R.id.numberClientTv) TextView phoneClientTv;
    @BindView(R.id.customerNotesTv) TextView customerWishesTv;
    @BindView(R.id.companyPriceTv) TextView companyPriceTv;
    @BindView(R.id.userPriceTv) TextView userPriceTv;
    @BindView(R.id.onlinePriceTv) TextView onlinePriceTv;
    @BindView(R.id.companyDeliveryPriceTv) TextView companyDeliveryPriceTv;
    @BindView(R.id.userDeliveryPriceTv) TextView userDeliveryPriceTv;
    @BindView(R.id.onlineDeliveryTv) TextView onlineDeliveryTv;
    @BindView(R.id.companyFullPriceTv) TextView companyFullPriceTv
            ;
    @BindView(R.id.userFullPriceTv) TextView userFullPriceTv;
    @BindView(R.id.onlineFullPriceTv) TextView onlineFullPriceTv;
    @BindView(R.id.orderedDishesListTv) TextView orderedDishesListTv;
    @BindView(R.id.totalAmountTv) TextView totalAmountTv;
    @BindView(R.id.wishesLayout) LinearLayout wishesLayout;
    @BindView(R.id.tagsForReceivingLayout) LinearLayout tagsForReceivingLayout;
    @BindView(R.id.tagsForDeliveryLayout) LinearLayout tagsForDeliveryLayout;
    @BindView(R.id.layoutDetails) LinearLayout layoutDetails;
    @BindView(R.id.loadProgress) ProgressBar loadProgress;
    @BindView(R.id.dialingPhoneUser) ImageView dialingPhoneUser;
    private String orderId;
    private boolean isVisible = false;

    public static OrderDetailsFragment newInstance(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_ID, orderId);

        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOrderIdBundle();
    }

    private void getOrderIdBundle() {
        if (getArguments().containsKey(ORDER_ID)) {
            orderId = getArguments().getString(ORDER_ID);
        } else
            getActivity().onBackPressed();
    }

    private void loadDetails() {
        App.getApi().getDetails(SharePreference.getTokenApp(getContext()),
                SharePreference.getAppAuthToken(getContext()),orderId)
                .enqueue(new Callback<Details>() {
                    @Override
                    public void onResponse(Call<Details> call, Response<Details> response) {
                        if (response != null && response.body() != null) {
                            details = response.body();

                            loadProgress.setIndeterminate(false);
                            loadProgress.setVisibility(View.GONE);
                            layoutDetails.setVisibility(View.VISIBLE);

                            setData();
                        }
                    }

                    @Override
                    public void onFailure(Call<Details> call, Throwable t) {

                    }
                });
    }

    private void setData() {
        if (details == null || !isVisible)
            return;

        orderFullNumberTv.setText(details.getNumber());

        currentOrderStatusTv.setText(details.getStatusData().getConfirmAction().getTitle());

        setTime();

        setAddress();

        setDataClient();

        setWishes();

        setFinance();

        setDishes();

        setTags();
    }

    private void setTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        receivingTimeTv.setText(sdf.format(new Time(details.getReceiveAddressPlace().getTime())));

        deliveryTimeTv.setText(sdf.format(new Time(details.getDeliveryAddressPlace().getTime())));
    }

    private void setAddress() {
        addressReceivingTv.setText("- " + details.getReceiveAddressPlace().getAddressName());

        deliveryAddressTv.setText("- " + details.getDeliveryAddressPlace().getAddressName());
    }

    private void setDataClient() {
        nameClientTv.setText(details.getUser().getName());

        phoneClientTv.setText(details.getUser().getPhone());
    }

    private void setWishes() {
        if (details.getUser().getWishes().equals("")) {
            wishesLayout.setVisibility(View.GONE);
        }
        else
            customerWishesTv.setText(details.getUser().getWishes());

    }

    private void setFinance() {
        PriceData priceData = details.getPriceData();

        setPrice(companyPriceTv, priceData.getCompanyPriceData().getPrice());
        setPrice(companyDeliveryPriceTv, priceData.getCompanyPriceData().getDeliveryPrice());
        setPrice(companyFullPriceTv, priceData.getCompanyPriceData().getFullPrice());

        setPrice(userPriceTv, priceData.getUserPriceData().getPrice());
        setPrice(userDeliveryPriceTv, priceData.getUserPriceData().getDeliveryPrice());
        setPrice(userFullPriceTv, priceData.getUserPriceData().getFullPrice());

        setPrice(onlinePriceTv, priceData.getOnlinePriceData().getPrice());
        setPrice(onlineDeliveryTv, priceData.getOnlinePriceData().getDeliveryPrice());
        setPrice(onlineFullPriceTv, priceData.getOnlinePriceData().getFullPrice());


    }

    @SuppressLint("ResourceAsColor")
    void setPrice(TextView textView, int price) {
        textView.setText(String.valueOf(price));
        if (price > 0) {
            textView.setTextColor(getResources().getColor(R.color.shamrockGreen));
        } else if (price < 0) {
            textView.setTextColor(getResources().getColor(R.color.jasper));
        } else {
            textView.setTextColor(getResources().getColor(R.color.onyx));
        }

    }

    private void setDishes() {
        List<Dish> dishes = details.getDishes().getDishes();

        String stringDishes = "";
        for (Dish currentDish : dishes) {

            stringDishes += currentDish.getName();

//            if (currentDish.getOption() != null) {
//                stringDishes += " (" + currentDish.getOption()+ ")";
//            }

            stringDishes += " / " + currentDish.getCount() + " / " +
                    currentDish.getPrice() + " грн.";

            stringDishes += "\n";
        }

        orderedDishesListTv.setText(stringDishes);

        totalAmountTv.setText("Итого " + details.getPriceData().getSummaryPriceData().getPrice() + " грн.");

    }

    private void setTags() {
        tagsForReceivingLayout.removeAllViews();
        tagsForDeliveryLayout.removeAllViews();

        for (String tag : details.getDeliveryAddressPlace().getTags()) {
            tagsForDeliveryLayout.addView(createTag(tag));
        }

        for (String tag : details.getReceiveAddressPlace().getTags()) {
            tagsForReceivingLayout.addView(createTag(tag));
        }
    }

    private View createTag(String tag) {
        Pair<Integer,String> tagData = getTagRecourse(tag);

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_tag_details, null, false);

        ((ImageView) view.findViewById(R.id.imageTag)).setImageResource(tagData.first);
        ((TextView) view.findViewById(R.id.textTag)).setText(tagData.second);

        return view;
    }

    private Pair<Integer,String> getTagRecourse(String tag) {
        int res = -1;
        String tagRu = "";
        if (tag.equals("soup")) {
            res = R.drawable.soup;
            tagRu = "суп";
        } else if (tag.equals("alco")) {
            res = R.drawable.alco;
            tagRu = "алко";
        } else if (tag.equals("gift")) {
            res = R.drawable.gift;
            tagRu = "подарок";
        } else if (tag.equals("deliveryToDoor")) {
            res = R.drawable.door;
            tagRu = "до двери";
        } else if (tag.equals("info")) {
            res = R.drawable.info;
            tagRu = "инфо";
        }
        return new Pair<>(res,tagRu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        ButterKnife.bind(this, view);

        dialingPhoneUser.setOnClickListener(this);

        loadDetails();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
        setData();
        loadDetails();
    }

    @Override
    public void onStop() {
        super.onStop();
        isVisible = false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialingPhoneUser) {
            if (details != null && details.getUser().getPhone() != null) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + details.getUser().getPhone()));
                startActivity(intent);
            }
        }
    }
}
