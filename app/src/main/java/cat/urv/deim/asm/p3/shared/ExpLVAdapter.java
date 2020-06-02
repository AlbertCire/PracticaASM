package cat.urv.deim.asm.p3.shared;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import cat.urv.deim.asm.R;

public class ExpLVAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> questionList;
    private Map<String, String> questionAnswer;
    private Context context;

    public ExpLVAdapter(ArrayList<String> questionList, Map<String, String> questionAnswer, Context context) {
        this.questionList = questionList;
        this.questionAnswer = questionAnswer;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return questionList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return questionList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return questionAnswer.get(questionList.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }   //we didn't use it

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }   //we didn't use it

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String question = (String) getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elv_group, null);
        TextView tvGroup = convertView.findViewById(R.id.tv_group);
        tvGroup.setText(question);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String answer = (String) getChild(groupPosition, childPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elv_child, null);
        TextView tvChild = convertView.findViewById(R.id.tv_child);
        tvChild.setText(answer);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
