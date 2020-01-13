package com.iqsolutions.mydemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iqsolutions.mydemo.pojo.Category;
import com.iqsolutions.mydemo.pojo.Product;
import com.iqsolutions.mydemo.pojo.Product_;
import com.iqsolutions.mydemo.pojo.Ranking;
import com.iqsolutions.mydemo.pojo.Tax;
import com.iqsolutions.mydemo.pojo.Variant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {


        private static DatabaseController databaseController;
        private Context context;
        private String TAG = DatabaseController.class.getSimpleName();
        DatabaseHelper databaseHelper;
        SQLiteDatabase sqLiteDatabase;

        private DatabaseController(Context context) {
            this.context = context;
        }

        public static DatabaseController getInstance(Context context) {
            if (databaseController == null)
                databaseController = new DatabaseController(context);
            return databaseController;
        }

        public DatabaseController open() throws SQLException {
            databaseHelper = new DatabaseHelper(context);
            sqLiteDatabase = databaseHelper.openDatabase();
            return this;
        }

    public void CloseDB() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.closeDatabase();

    }


    public Boolean isTableEmpty(String tableName) {
        boolean empty = true;
        open();
        Cursor cur = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM '" + tableName + "'", null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt(0) == 0);
        }
        cur.close();
        CloseDB();

        return empty;
    }


    public long insertValuesInTable(ContentValues contentValues, String tableName) {
        long val = -1;
        try {
            open();
            Log.d(TAG, "Values: "+contentValues.valueSet().toString());
            Log.d(TAG, "Keyset: "+contentValues.keySet().toString());
            val = sqLiteDatabase.insertOrThrow(tableName, null, contentValues);
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage());
        } finally {
            Log.d(TAG, "entry in " + tableName + " is successfull...");
            CloseDB();
        }
        return val;
    }

    public ArrayList<Category> getCategory(){

        ArrayList<Category> list = new ArrayList<>();
        List<Product> products_list = new ArrayList<>();
        List<Variant> variants_list = new ArrayList<>();

        try {
            open();
            String strQuery = "select * from category";
            /*String strQuery = "select c.cat_id ,c.cat_name, p.prod_id, p.prod_name,p.date_added,p.tax_name,p.tax_value "+
                    ",v.v_id,v.color,v.size,v.price from category c join products p on c.cat_id = p.cat_id " +
                    " join variants v on p.prod_id = v.prod_id";*/
            Cursor cursor = sqLiteDatabase.rawQuery(strQuery, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                            Category category = new Category();
                        Product product = new Product();
                        Variant variant= new Variant();
                        Tax tax = new Tax();

                                    category.setId(cursor.getInt(cursor.getColumnIndex("cat_id")));
                                    category.setName(cursor.getString(cursor.getColumnIndex("cat_name")));
                           //  category.setChildCategories(cursor.getString(cursor.getColumnIndex("child_caat")));
                                    /*product.setId(cursor.getInt(cursor.getColumnIndex("prod_id")));
                                    product.setId(cursor.getInt(cursor.getColumnIndex("prod_id")));
                                    product.setName(cursor.getString(cursor.getColumnIndex("prod_name")));
                                    product.setDateAdded(cursor.getString(cursor.getColumnIndex("date_added")));
                                    products_list.add(product);

                                    tax.setName(cursor.getString(cursor.getColumnIndex("tax_name")));
                                    tax.setValue(cursor.getDouble(cursor.getColumnIndex("tax_value")));
                                    product.setTax(tax);


                                    variant.setId(cursor.getInt(cursor.getColumnIndex("v_id")));
                                    variant.setColor(cursor.getString(cursor.getColumnIndex("color")));
                                    variant.setSize(cursor.getString(cursor.getColumnIndex("size")));
                                    variant.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                                    variants_list.add(variant);

                                    product.setVariants(variants_list);
                                    category.setProducts(products_list);*/
                                    list.add(category);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        Log.e(TAG, "List Count " + list.size());

        return list;
    }

    public ArrayList<Product> getProducts(String cat_id){

        ArrayList<Product> products_list = new ArrayList<>();

        try {
            open();
            String strQuery = "select * from products where cat_id = '"+cat_id+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(strQuery, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Product product = new Product();
                        product.setId(cursor.getInt(cursor.getColumnIndex("prod_id")));
                        product.setName(cursor.getString(cursor.getColumnIndex("prod_name")));
                        product.setDateAdded(cursor.getString(cursor.getColumnIndex("date_added")));
                        products_list.add(product);

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return products_list;
    }

    public String getoneColumnfromdatabase(String query, String requiredString) {
        String column = "0";
        try {
            open();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        column = cursor.getString(cursor.getColumnIndex(requiredString));
                    } while (cursor.moveToNext());
                }
            }
            CloseDB();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return column;
    }

    public ArrayList<Variant> getVariants(String prod_id){

        ArrayList<Variant> variant_list = new ArrayList<>();

        try {
            open();
            String strQuery = "select * from variants where prod_id = '"+prod_id+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(strQuery, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Variant variant = new Variant();
                        variant.setColor(cursor.getString(cursor.getColumnIndex("color")));
                        variant.setSize(cursor.getString(cursor.getColumnIndex("size")));
                        variant.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                        variant_list.add(variant);

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


        return variant_list;
    }

    public ArrayList<Ranking> getRanking(){

        ArrayList<Ranking> rankings_list = new ArrayList<>();

        try {
            open();
            String strQuery = "select * from rankings ";

            Cursor cursor = sqLiteDatabase.rawQuery(strQuery, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Ranking ranking = new Ranking();
                        //ranking.set(cursor.getString(cursor.getColumnIndex("id")));
                        ranking.setRanking(cursor.getString(cursor.getColumnIndex("ranking")));

                        rankings_list.add(ranking);

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


        return rankings_list;
    }
    public ArrayList<Product_> getRankingProducts(int id){
            String strQuery= null;
            if(id ==1)
                strQuery ="select * from r_most_viewd_products";

            if(id==2)
                strQuery ="select * from r_most_ordered_products";

            if(id==3)
                strQuery ="select * from r_most_shared_products";

        ArrayList<Product_> r_product_list = new ArrayList<>();

        try {
            open();
            Cursor cursor = sqLiteDatabase.rawQuery(strQuery, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Product_ product_ = new Product_();
                        if(id==1) {
                            product_.setId(cursor.getInt(cursor.getColumnIndex("prod_id")));
                            product_.setViewCount(cursor.getInt(cursor.getColumnIndex("view_count")));
                        }

                        if(id==2) {
                            product_.setId(cursor.getInt(cursor.getColumnIndex("prod_id")));
                            product_.setOrderCount(cursor.getInt(cursor.getColumnIndex("order_count")));
                        }

                        if(id==3) {
                            product_.setId(cursor.getInt(cursor.getColumnIndex("prod_id")));
                            product_.setShares(cursor.getInt(cursor.getColumnIndex("shares")));
                        }
                        r_product_list.add(product_);

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


        return r_product_list;
    }
}
