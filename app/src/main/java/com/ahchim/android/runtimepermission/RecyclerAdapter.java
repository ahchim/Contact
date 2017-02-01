package com.ahchim.android.runtimepermission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahchim on 2017-02-01.
 * 리사이클러 뷰 구현 순서
 * 1. 홀더에 사용하는 위젯을 모두 정의한다.
 * 2. getItemCount에 데이터 개수 전달
 * 3. onCreateViewHolder 에서 뷰 아이템 생성
 * 4. onBindViewHolder를 통해 로직을 구현
 */
// 1. adapter 확장 2. 홀더클래스 이너클래스로 만들기 3. 각자 ~ 4. 생성자 만듬 5. 변수만듬
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{
    private ArrayList<Contact> datas;
    private Context context;

    public RecyclerAdapter(ArrayList<Contact> datas, Context context){
        this.datas = datas;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. 데이터를 행 단위로 꺼낸다.
        final Contact contact = datas.get(position);

        // 2. 홀더에 데이터를 세팅한다
        holder.getTxtNo().setText(contact.getId() + "");
        holder.getTxtName().setText(contact.getName());
        holder.getTxtTel().setText(contact.getTelOne());

        holder.spinnerRun(contact.getTel());

        /*
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, );

            }
        });*/

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.cardView.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<Contact> getDatas(){
        return datas;
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView txtNo, txtName, txtTel;
        private Spinner spinTel;
        private ImageButton btnCall;
        private CardView cardView;

        private ArrayAdapter<String> spinAdapt;

        public Holder(View view) {
            super(view);

            this.txtNo = (TextView) view.findViewById(R.id.txtNo);
            this.txtName = (TextView) view.findViewById(R.id.txtName);
            this.txtTel = (TextView) view.findViewById(R.id.txtTel);
            this.spinTel = (Spinner) view.findViewById(R.id.spinTel);

            this.btnCall = (ImageButton) view.findViewById(R.id.btnCall);

            this.btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + txtTel.getText().toString()));
                            context.startActivity(intent);
                        }
                    } else{
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + txtTel.getText().toString()));
                        context.startActivity(intent);
                    }
                }
            });

            this.cardView = (CardView) view.findViewById(R.id.cardView);

        }

        public TextView getTxtNo() {
            return txtNo;
        }

        public TextView getTxtName() {
            return txtName;
        }

        public TextView getTxtTel() {
            return txtTel;
        }

        public ImageButton getBtnCall() {
            return btnCall;
        }

        public CardView getCardView() {
            return cardView;
        }

        // 3.pre 스피너에 들어갈 데이터를 정의
        public void spinnerRun(final ArrayList<String> units){
            // 3.1 스피너 데이터 등록
            spinAdapt = new ArrayAdapter<String>(
                    context, android.R.layout.simple_spinner_dropdown_item, units
            );

            // 3.2 스피너에 아답터 등록
            spinTel.setAdapter(spinAdapt);

            // 3.3 스피너 리스너에 등록
            spinTel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id){
                    spinTel.setSelection(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView){
                }
            });
        }
    }
}
