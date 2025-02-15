package com.example.myapplication21;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder>{
    private Context context;
    private ArrayList ProductType , ProductName ,Quantity, Price,AgentName, IsBuy, DProduct, DCustomer,Date ;

    public listAdapter(Context context, ArrayList ProductType, ArrayList ProductName, ArrayList Quantity, ArrayList Price,ArrayList AgentName, ArrayList IsBuy, ArrayList DProduct, ArrayList DCustomer , ArrayList Date) {
        this.context = context;
        this.ProductType = ProductType;
        this.ProductName = ProductName;
        this.Quantity = Quantity;
        this.Price = Price;
        this.AgentName = AgentName;
        this.IsBuy = IsBuy;
        this.DProduct = DProduct;
        this.DCustomer = DCustomer;
        this.Date = Date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_list_adapter,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ProductType.setText(String.valueOf(ProductType.get(position)));
        holder.ProductName.setText(String.valueOf(ProductName.get(position)));
        holder.Quantity.setText(String.valueOf(Quantity.get(position)));
        holder.Price.setText(String.valueOf(Price.get(position)));
        holder.AgentName.setText(String.valueOf(AgentName.get(position)));
        holder.IsBuy.setText(String.valueOf(IsBuy.get(position)));
        holder.DProduct.setText(String.valueOf(DProduct.get(position)));
        holder.DCustomer.setText(String.valueOf(DCustomer.get(position)));
        holder.Date.setText(String.valueOf(Date.get(position)));
    }

    @Override
    public int getItemCount() {
        return ProductName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView ProductType,ProductName,Quantity,Price,AgentName,IsBuy,DProduct,DCustomer,Date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductType =itemView.findViewById(R.id.ProductType);
            ProductName =itemView.findViewById(R.id.ProductName);
            Quantity =itemView.findViewById(R.id.Quantity);
            Price =itemView.findViewById(R.id.Price);
            AgentName =itemView.findViewById(R.id.AgentName);
            IsBuy =itemView.findViewById(R.id.IsBuy);
            DProduct =itemView.findViewById(R.id.DProduct);
            DCustomer =itemView.findViewById(R.id.DCustomer);
            Date =itemView.findViewById(R.id.Date);
        }
    }


}
