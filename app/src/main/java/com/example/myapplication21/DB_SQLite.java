package com.example.myapplication21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.NoCopySpan;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DB_SQLite extends SQLiteOpenHelper
    {
    public static final String db_name="BMP_HW_SQLITE.db";
    public DB_SQLite(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE \"User\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"email\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"password\"\tTEXT NOT NULL\n" +
                ")");
        db.execSQL("CREATE TABLE \"Type\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"description\"\tTEXT \n" +
                ")");
        db.execSQL("CREATE TABLE \"Material\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"Type_id\"\tINTEGER NOT NULL,\n" +
                "\t\"description\"\tTEXT NOT NULL,\n" +
                "\tFOREIGN KEY(\"Type_id\") REFERENCES \"Type\"(\"id\")\n" +
                ")");
        db.execSQL("CREATE TABLE \"Agent\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"description\"\tTEXT NOT NULL, \n" +
                "\t\"Material_id\"\tINTEGER NOT NULL, \n" +
                "\tFOREIGN KEY(\"Material_id\") REFERENCES \"Material\"(\"id\")\n" +
                ")");
        db.execSQL("CREATE TABLE \"Invoice\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\t\"agent_id\"\tINTEGER NOT NULL,\n" +
                "\t\"date\"\tTEXT NOT NULL,\n" +
                "\t\"D\"\tINTEGER NOT NULL,\n" +
                "\t\"is_buy\"\tTEXT NOT NULL,\n" +
                "\t\"description\"\tTEXT ,\n" +
                "\tFOREIGN KEY(\"agent_id\") REFERENCES \"Agent\"(\"id\")\n" +
                ")");
        db.execSQL("CREATE TABLE \"Invoice_Detail\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\t\"invoice_id\"\tINTEGER NOT NULL,\n" +
                "\t\"material_id\"\tINTEGER NOT NULL,\n" +
                "\t\"number\"\tINTEGER NOT NULL,\n" +
                "\t\"price\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"invoice_id\") REFERENCES \"Invoice\"(\"id\"),\n" +
                "\tFOREIGN KEY(\"material_id\") REFERENCES \"Material\"(\"id\")\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Type");
        db.execSQL("DROP TABLE IF EXISTS Material");
        db.execSQL("DROP TABLE IF EXISTS Agent");
        db.execSQL("DROP TABLE IF EXISTS Agent");
        db.execSQL("DROP TABLE IF EXISTS Invoice");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
    public boolean insert_data(String Name,String Email,String Password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put("name",Name);
        content_values.put("email",Email);
        content_values.put("password",Password);
        Long result = db.insert("User" , null , content_values);
        if(result == -1)
            return false;
        else
            return true;
    }
        public boolean check_name(String Name)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor =db.rawQuery("select * from User where name=?",new String[]{Name});
            if(cursor.getCount() > 0)
                return true;
            else
                return false;
        }
        public boolean check_email(String Email)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor =db.rawQuery("select * from User where email=?",new String[]{Email});
            if(cursor.getCount() > 0)
                return true;
            else
                return false;
        }
        public boolean check_pass_name(String Name,String Password)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor =db.rawQuery("select * from User where name=? and password=?",new String[]{Name,Password});
            if(cursor.getCount() > 0)
                return true;
            else
                return false;
        }

        public boolean insert_invoice_data(String productType, String productName, String descriptionOfTheProduct,
                                           String agentName, String descriptionOfTheAgent, String invoiceType, String q, String p) {
            Integer quantity= Integer.parseInt(q);
            Float price=Float.parseFloat(p);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content_values = new ContentValues();

            // Add product type
            content_values.put("name", productType);
            content_values.put("description", "---------");
            db.insert("Type", null, content_values);
            content_values.clear();

            // Get product type ID
            Cursor cursor = db.rawQuery("SELECT id FROM Type WHERE name=?", new String[]{productType});
            int typeId = -1;
            if (cursor.moveToFirst()) {
                typeId = cursor.getInt(0);
            }
            cursor.close();

            // Add product
            content_values.put("name", productName);
            content_values.put("Type_id", typeId);
            content_values.put("description", descriptionOfTheProduct);
            db.insert("Material", null, content_values);
            content_values.clear();

            // Get product ID
            cursor = db.rawQuery("SELECT id FROM Material WHERE name=?", new String[]{productName});
            int productId = -1;
            if (cursor.moveToFirst()) {
                productId = cursor.getInt(0);
            }
            cursor.close();

            // Add agent
            content_values.put("name", agentName);
            content_values.put("description", descriptionOfTheAgent);
            content_values.put("Material_id", productId);
            db.insert("Agent", null, content_values);
            content_values.clear();

            // Get agent ID
            cursor = db.rawQuery("SELECT id FROM Agent WHERE name=?", new String[]{agentName});
            int agentId = -1;
            if (cursor.moveToFirst()) {
                agentId = cursor.getInt(0);
            }
            cursor.close();

            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            Date currentDate = new Date();
            String formattedDate = dateFormat.format(currentDate);
            Integer D= Integer.valueOf(formattedDate);
            content_values.put("agent_id", agentId);
            String d=String.valueOf(System.currentTimeMillis()) ;
            content_values.put("date", d);
            content_values.put("D", D);
            content_values.put("is_buy", invoiceType);
            content_values.put("description", "--------");
            db.insert("Invoice", null, content_values);
            content_values.clear();

            // Get invoice ID
            cursor = db.rawQuery("SELECT id FROM Invoice WHERE agent_id=? AND date=?", new String[]{String.valueOf(agentId), d});
            int invoiceId = -1;
            if (cursor.moveToFirst()) {
                invoiceId = cursor.getInt(0);
            }
            cursor.close();

            // Add invoice detail
            content_values.put("invoice_id", invoiceId);
            content_values.put("material_id", productId);
            content_values.put("number", quantity);
            content_values.put("price", price);
            db.insert("Invoice_Detail", null, content_values);
            content_values.clear();

            return true;
        }
        public Integer getSumValue(String is_buy) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT SUM(price * number) AS SV FROM Invoice_Detail INNER JOIN Invoice ON Invoice.id = Invoice_Detail.invoice_id WHERE Invoice.is_buy = ?",  new String[]{is_buy});
            Integer sumValue = 0;
            if (c != null && c.moveToFirst()) {
                int columnIndex = c.getColumnIndex("SV");
                if (columnIndex >= 0) {
                    sumValue = c.getInt(columnIndex);
                }
            }
            if (c != null) {

                c.close();

            }
            return sumValue;
        }
        public Cursor getDataBetweenDates(String is_buy )/*,String startDate,String endDate*/ {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery( "SELECT Type.name, Material.name, Material.description,Agent.name, Agent.description ,Invoice.D, Invoice.is_buy, Invoice_Detail.number, Invoice_Detail.price " +
                    "FROM Type " +
                    "INNER JOIN Material ON Type.id = Material.Type_id " +
                    "INNER JOIN Agent ON Material.id = Agent.Material_id " +
                    "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                    "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id" +
                    " WHERE Invoice.is_buy = ?"
                    ,new String[]{is_buy });/*"AND Invoice.date BETWEEN ? AND ?"*/   /*, startDate , endDate*/

            return c;
        }

        /*public Cursor getDataBetweenDates(String is_buy ,String startDate,String endDate) {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery( "SELECT Type.name, Material.name, Material.description,Agent.name, Agent.description ,Invoice.D, Invoice.is_buy, Invoice_Detail.number, Invoice_Detail.price " +
                    "FROM Type " +
                    "INNER JOIN Material ON Type.id = Material.Type_id " +
                    "INNER JOIN Agent ON Material.id = Agent.Material_id " +
                    "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                    "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id" +
                    " WHERE Invoice.is_buy = ?" +
                    "AND Invoice.date BETWEEN ? AND ?",new String[]{is_buy , startDate , endDate});

            return c;
        }*/
       /* public Cursor getDataBetweenDates(String is_buy, String startDate, String endDate) {
            SQLiteDatabase db = this.getWritableDatabase();

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy", Locale.US);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);

                Date start = inputFormat.parse(startDate);
                Date end = inputFormat.parse(endDate);

                String formattedStart = outputFormat.format(start);
                String formattedEnd = outputFormat.format(end);

                String[] columns = { "Type.name", "Material.name", "Material.description", "Agent.name", "Agent.description",
                        "Invoice.D", "Invoice.is_buy", "Invoice_Detail.number", "Invoice_Detail.price" };

                String whereClause = "Invoice.is_buy = ? AND (Invoice.D BETWEEN ? AND ?)";

                String[] whereArgs = { is_buy, formattedStart, formattedEnd };

                Cursor cursor = db.query("Type " +
                                "INNER JOIN Material ON Type.id = Material.Type_id " +
                                "INNER JOIN Agent ON Material.id = Agent.Material_id " +
                                "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                                "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id",
                        columns, whereClause, whereArgs, null, null, "Invoice.D");

                return cursor;
            } catch (Exception e) {
                Log.e("getDataBetweenDates", "Failed to retrieve data", e);
                return null;
            }
        }*/

        public Cursor getTopMaterialBetweenDates( ) {
            SQLiteDatabase db = this.getWritableDatabase();
            String topMaterialSql = "SELECT Material.name, SUM(Invoice_Detail.number) AS total_sales " +
                    "FROM Type " +
                    "INNER JOIN Material ON Type.id = Material.Type_id " +
                    "INNER JOIN Agent ON Material.id = Agent.Material_id " +
                    "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                    "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id " +
                    "WHERE Invoice.is_buy = ? " +
                    "GROUP BY Material.id " +
                    "ORDER BY total_sales DESC " +
                    "LIMIT 1";
            Cursor topMaterialCursor = db.rawQuery(topMaterialSql, new String[]{"Sale"});
            topMaterialCursor.moveToFirst();
            String topMaterialName = topMaterialCursor.getString(0);

            // Find all invoices containing the top material
            String invoicesSql = "SELECT Type.name, Material.name, Material.description, Agent.name, Agent.description, Invoice.D, Invoice.is_buy, Invoice_Detail.number, Invoice_Detail.price , SUM(Invoice_Detail.price * Invoice_Detail.number) AS SV " +
                    "FROM Type " +
                    "INNER JOIN Material ON Type.id = Material.Type_id " +
                    "INNER JOIN Agent ON Material.id = Agent.Material_id " +
                    "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                    "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id " +
                    "WHERE Invoice.is_buy = ? " +
                    "AND Material.name = ?";
            Cursor c = db.rawQuery(invoicesSql, new String[]{"Sale", topMaterialName});
            return c;
        }
        public Cursor getTopPayingCustomers(String is_buy) {
            SQLiteDatabase db = this.getWritableDatabase();

            // Find the customer with the maximum total payment
            String topCustomerSql = "SELECT Agent.name, SUM(Invoice_Detail.number * Invoice_Detail.price) AS total_payment " +
                    "FROM Agent " +
                    "INNER JOIN Material ON Agent.Material_id = Material.id " +
                    "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                    "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id " +
                    "WHERE Invoice.is_buy = ? " +
                    "GROUP BY Agent.id " +
                    "ORDER BY total_payment DESC " +
                    "LIMIT 1";
            Cursor topCustomerCursor = db.rawQuery(topCustomerSql, new String[]{is_buy});
            topCustomerCursor.moveToFirst();
            String topCustomerName = topCustomerCursor.getString(0);

            // Find all invoices containing payments from the top customer
            String invoicesSql = "SELECT Type.name, Material.name, Material.description, Agent.name, Agent.description, Invoice.D, Invoice.is_buy, Invoice_Detail.number, Invoice_Detail.price " +
                    "FROM Type " +
                    "INNER JOIN Material ON Type.id = Material.Type_id " +
                    "INNER JOIN Agent ON Material.id = Agent.Material_id " +
                    "INNER JOIN Invoice ON Agent.id = Invoice.Agent_id " +
                    "INNER JOIN Invoice_Detail ON Invoice.id = Invoice_Detail.invoice_id " +
                    "WHERE Invoice.is_buy = ? " +
                    "AND Agent.name = ?";
            Cursor c = db.rawQuery(invoicesSql, new String[]{is_buy, topCustomerName});
            return c;
        }

}
