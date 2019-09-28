package ru.sergisa.sender.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ru.sergisa.sender.R;
import ru.sergisa.sender.activity.FullActivity;
import ru.sergisa.sender.models.SenderResponse.Post;

//адаптер для RecyclerView
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    static class PersonViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView type;
        TextView text;

        TextView line;
        Button linkBtn,qrBtn;

        PersonViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            text = itemView.findViewById(R.id.code);
            qrBtn = itemView.findViewById(R.id.qrBtn);
            linkBtn = itemView.findViewById(R.id.linkButton);
        }

        public void setLinkBtnText(String link) {
            this.linkBtn.setText(link);
        }
        public void showQrBtn(String link) {
            this.qrBtn.setVisibility(View.VISIBLE);
        }
    }

    private Post[] posts;
    private Activity main;
    private Context context;
    private Intent intent;

    private onItemClickListener itemClickListener;
    private onButtonClickListener buttonClickListener;

    public RVAdapter(Activity main,Context context, Post[] posts){
        this.posts = posts;
        this.main = main;
        this.context = context;
        intent = new Intent(context, FullActivity.class);
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        final PersonViewHolder pvh = new PersonViewHolder(v);

        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(posts[pvh.getAdapterPosition()],pvh.getAdapterPosition());
                //onItemClickListener.onItemClick(posts[i]);
            }
        };

        View.OnClickListener buttonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonClickListener.onButtonClick(v, posts[pvh.getAdapterPosition()],
                        pvh.getAdapterPosition());
            }
        };

        v.setOnClickListener(cardClickListener);
        pvh.qrBtn.setOnClickListener(buttonClick);
        pvh.linkBtn.setOnClickListener(buttonClick);
        return pvh;
    }

    @Override
    public void onViewRecycled(PersonViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewAttachedToWindow(PersonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }


    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
        if(posts[i].hasLink()){
            //context.getResources().getColor()
            personViewHolder.linkBtn.setTextColor(context.getResources().getColorStateList(R.color.violet_button));
            personViewHolder.linkBtn.setBackgroundResource(R.drawable.grey_outline_button);
            personViewHolder.linkBtn.setText(context.getResources().getText(R.string.linkButtonText));
            personViewHolder.qrBtn.setVisibility(View.VISIBLE);
        }else{
            personViewHolder.linkBtn.setBackgroundResource(R.drawable.grey_button);
            personViewHolder.linkBtn.setTextColor(Color.WHITE);
            personViewHolder.linkBtn.setText(context.getResources().getText(R.string.linkCreateText));
            personViewHolder.qrBtn.setVisibility(View.INVISIBLE);
        }

        if (posts[i].hasTitle()){
            personViewHolder.title.setText(posts[personViewHolder.getAdapterPosition()].getTitle());
        }else{
            SpannableString span = new SpannableString("Нет заголовка");
            span.setSpan(new ForegroundColorSpan(Color.GRAY), 0,
                    "Нет заголовка".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            personViewHolder.title.setText(span);
        }

        personViewHolder.text.setText(posts[i].getText());
        personViewHolder.type.setText(posts[i].getLanguageName());

    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);
    }

    public onItemClickListener getOnItemClickListener() {
        return itemClickListener;
    }
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
}

    public void setOnButtonClickListener(onButtonClickListener onButtonClickListener){
        this.buttonClickListener = onButtonClickListener;
    }
    public onButtonClickListener getOnButtonClickListener() {
        return buttonClickListener;
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}