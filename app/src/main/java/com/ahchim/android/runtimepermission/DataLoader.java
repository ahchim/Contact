package com.ahchim.android.runtimepermission;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Ahchim on 2017-02-01.
 */

public class DataLoader {
    private ArrayList<Contact> datas = new ArrayList<>();
    private Context context;

    public DataLoader(Context context){
        this.context = context;
    }


    public void load(){
        // 1. 주소록에 접근하기 위해 ContentResolver를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 2. 주소록에서 가져올 데이터 컬럼명을 정의한다.
        String projections[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,    // 데이터 아이디(번호)
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,  // 이름
                ContactsContract.CommonDataKinds.Phone.NUMBER         // 전화번호
        };
        // 커서 : 데이터가 처리되는 단위 (안드로이드에서)
        // 3. Content Resolver로 질의(쿼리)한 데이터를 커서에 담는다.
        // 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 주소록 URI : ContactsContrast.Contacts.CONTENT_URI
        //              HAS_PHONE_NUMBER : 전화번호가 있는지 확인하는 상수
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI // 데이터의 주소
                        , projections       // 가져올 데이터 컬럼명 배열
                        , null              // 조건절
                        , null              // 지정된 컬럼명과 매핑되는 실제 조건 값
                        , null              // 정렬
        );

        if(cursor!=null){
            // 4. 커서에 넘어온 데이터가 있다면 반복문을 돌면서 datas에 담아준다.
            while(cursor.moveToNext()){
                Contact contact  = new Contact();

                // 코드가 길어지면 인덱스 헷갈리기 쉽다. 그래서 이렇게 하는데, 0, 1, 2로 cursor.get~() 넣어도 된다.
                // 5. 커서의 컬럼 인덱스를 가져온 후
                int idx = cursor.getColumnIndex(projections[0]);
                // 5.1 컬럼인덱스에 해당하는 타입에 맞춰 값을 꺼내서 세팅한다.
                contact.setId(cursor.getInt(idx));

                idx = cursor.getColumnIndex(projections[1]);
                contact.setName(cursor.getString(idx));

                idx = cursor.getColumnIndex(projections[2]);
                contact.addTel(cursor.getString(idx));

                datas.add(contact);
            }

            // ******중요 : cursor 사용 후 close를 호출하지 않으면 메모리 누수가 발생할 수 있다.
            cursor.close();
        }
    }

    public ArrayList<Contact> get() {
        return datas;
    }
}
