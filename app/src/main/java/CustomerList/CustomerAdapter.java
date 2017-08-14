package CustomerList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dexlabs.foodback.R;

import java.util.ArrayList;
import java.util.HashMap;

import GeneralCode.OnGetWorkListener;

class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> cNames;
    private HashMap<String, customerDS> cList;
    private OnGetWorkListener mWork;

    public CustomerAdapter(Context mContext, OnGetWorkListener work) {
        this.mContext = mContext;
        this.cNames = new ArrayList<>();
        this.cList = new HashMap<>();
        this.mWork = work;
    }

    void reset() {
        notifyDataSetChanged();
    }

    void restart() {
        this.cNames = new ArrayList<>();
        this.cList = new HashMap<>();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_row_customer_list, parent, false);
        cHolder holder = new cHolder(v);
        v.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        cHolder cd = (cHolder) holder;
        String name = this.cNames.get(pos);
        customerDS cust = this.cList.get(name);
        cd.name.setText(name);
        cd.number.setText(cust.getNumber());
        cd.birthday.setText(cust.getBirthDate());
    }

    @Override
    public int getItemCount() {
        this.cNames = CustomerDriver.getCustomerNames();
        this.cList = CustomerDriver.getCustomerList();

        if (this.cNames != null && this.cNames.size() > 0) {
            mWork.workSomething(OnGetWorkListener.HIDE);
            return this.cNames.size();
        }

        mWork.workSomething(OnGetWorkListener.SHOW);
        return 0;
    }

    private class cHolder extends RecyclerView.ViewHolder {
        TextView name, number, birthday;

        cHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_rr_name);
            number = view.findViewById(R.id.tv_rr_number);
            birthday = view.findViewById(R.id.tv_rr_birthday);
        }
    }
}
