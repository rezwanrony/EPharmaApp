package com.appersden.epharma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    SwipeMenuListView lv_cart;
    List<Cart> cartList;
    CustomCartAdapter customCartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        lv_cart=(SwipeMenuListView) findViewById(R.id.listViewcart);
        cartList=new ArrayList<Cart>();

        cartList.add(new Cart("Antacid","Square",100,2,10,40));
        cartList.add(new Cart("Flexo","Square",100,2,10,40));
        customCartAdapter=new CustomCartAdapter(CartActivity.this,cartList);
        lv_cart.setAdapter(customCartAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.colorAccent);
                // set item width
                deleteItem.setWidth(120);
                // set a icon
                deleteItem.setIcon(R.drawable.cartwhite);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        lv_cart.setMenuCreator(creator);

        lv_cart.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}
