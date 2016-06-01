package landlord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cmpe277.termproj_rentapp.R;
import landlord.search_info.SearchInfo;

/**
 * Created by yunlongxu on 4/19/16.
 */
public class SearchOptionAdapter extends BaseAdapter {

    private static List<SearchInfo> searchInfos;
    private LayoutInflater mInflater;

    public SearchOptionAdapter(Context context, List<SearchInfo> searchInfos) {
        this.searchInfos = searchInfos;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return searchInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_row_element, null);
            holder = new ViewHolder();
            holder.searchInfo = (TextView)convertView.findViewById(R.id.search_info);
            holder.icon = (ImageView) convertView.findViewById(R.id.category_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.searchInfo.setText(searchInfos.get(position).getInformation());
        holder.icon.setImageResource(searchInfos.get(position).getIconId());
        return convertView;
    }

    static class ViewHolder {
        TextView searchInfo;
        ImageView icon;
    }
}
