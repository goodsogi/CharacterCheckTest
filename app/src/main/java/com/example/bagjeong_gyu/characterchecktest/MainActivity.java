package com.example.bagjeong_gyu.characterchecktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
/* **********************************************
	 * 자음 모음 분리
	 * 설연수 -> ㅅㅓㄹㅇㅕㄴㅅㅜ,    바보 -> ㅂㅏㅂㅗ
	 * **********************************************/
    /** 초성 - 가(ㄱ), 날(ㄴ) 닭(ㄷ) */
    public static char[] arrChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
            0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
            0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
    /** 중성 - 가(ㅏ), 야(ㅑ), 뺨(ㅑ)*/
    public static char[] arrJungSung = { 0x314f, 0x3150, 0x3151, 0x3152,
            0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
            0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162,
            0x3163 };
    /** 종성 - 가(없음), 갈(ㄹ) 천(ㄴ) */
    public static char[] arrJongSung = { 0x0000, 0x3131, 0x3132, 0x3133,
            0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
            0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145,
            0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCheckButton();
    }

    private void initCheckButton() {
        Button checkButton = (Button) findViewById(R.id.checkText);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkText();
                checkTextWithPattern();
            }
        });
    }

    private void checkTextWithPattern() {
        EditText textInput = (EditText) findViewById(R.id.textInput);
        String word = textInput.getText().toString();
        Pattern p = Pattern.compile("(!,@,#,$,%,^,&,*,?,_,~)");
        Matcher m = p.matcher(word);
        if (m.find()){ showResult("특수문자만으로 이뤄짐"); } else {
            showResult("특수문자만으로 이뤄지지않음");
        }
    }

    private void checkText() {
        EditText textInput = (EditText) findViewById(R.id.textInput);
        String word = textInput.getText().toString();
        String result		= "";

        for (int i = 0; i < word.length(); i++) {

			/*  한글자씩 읽어들인다. */
            char chars = (char) (word.charAt(i) - 0xAC00);

            if (chars >= 0 && chars <= 11172) {
				/* A. 자음과 모음이 합쳐진 글자인경우 */

				/* A-1. 초/중/종성 분리 */
                int chosung 	= chars / (21 * 28);
                int jungsung 	= chars % (21 * 28) / 28;
                int jongsung 	= chars % (21 * 28) % 28;


				/* A-2. result에 담기 */
                result = result + arrChoSung[chosung] + arrJungSung[jungsung];


				/* 자음분리 */
                if (jongsung != 0x0000) {
					/* A-3. 종성이 존재할경우 result에 담는다 */
                    result =  result + arrJongSung[jongsung];
                }



            } else {
				/* B. 한글이 아니거나 자음만 있을경우 */

				/* 자음분리 */
                result = result + ((char)(chars + 0xAC00));


            }//if

        }//for




        showResult(result);

    }

    private void showResult(String result) {
        TextView resultView = (TextView) findViewById(R.id.result);
        resultView.setText(result);
    }


}
